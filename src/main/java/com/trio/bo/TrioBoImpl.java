package com.trio.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trio.dao.TrioDao;
import com.trio.dto.Agent;
import com.trio.dto.Clinic;
import com.trio.dto.Dropbox;
import com.trio.dto.Employee;
import com.trio.dto.GLGMem;
import com.trio.dto.Member;
import com.trio.dto.User;
import com.trio.model.ClinicDetails;
import com.trio.model.CountryDetail;
import com.trio.model.IndustryDetail;
import com.trio.model.UserLogin;
import com.trio.util.Custom;
import com.trio.util.Email;
//import com.trio.util.PushEmail;
import com.trio.util.TrioException;


@Service("bo")
public class TrioBoImpl implements TrioBo{
	
	public static final Logger logger = LoggerFactory.getLogger(TrioBoImpl.class);
	
	@Autowired
	TrioDao dao;
	
	// ---------------- TRIO Number Validate ------------------------------
	public String gglNumberCheck(String gglNumber)
	{
		return dao.gglNumberCheck(gglNumber);
		//return "";
	}
		
	// ---------------- Update Payment ------------------------------
	public boolean UpdatePayment(String gglNumber)
	{
		boolean paymentUpdateStatus = false;
		try {
			Member member = new Member();
			member.setMemberNumber(gglNumber);
			member =  dao.UpdatePayment(member);
			paymentUpdateStatus=true;
			Email.paymentConfirmation(member); 
			
			return paymentUpdateStatus;
		} catch(Exception e) {
			paymentUpdateStatus=false;
			return paymentUpdateStatus;
		}
	}
	
	public User userLogin(User user){
		logger.info("Inside userLogin method-------------");
		List<UserLogin> result=null;
		try {
			user.setId(1);
			result = dao.userLogin(user,result);
			if(result.size() > 0) {
				result=null;
				user.setId(2);
				result = dao.userLogin(user,result);
				if(result.size() > 0) {
					logger.info("userLogin Status ------------------->"+result.get(0).getUserRole());
					logger.info("Primary Key ------------------->"+result.get(0).getUser_Login_ID());
					logger.info("User role ------------------>"+result.get(0).getUserRole());
					//user.setStatus("success");
					user.setUserRole(result.get(0).getUserRole());
					user.setId(result.get(0).getUser_Login_ID());
					user.setUserloginPrimaryKeyString(String.valueOf(result.get(0).getUser_Login_ID()));
				
					if(result.get(0).getStatus().equalsIgnoreCase("Approved")){
						//user.setStatus("success");
						if(result.get(0).getUserRole().equalsIgnoreCase("admin")) {
							logger.info("Admin part");

							user.setStatus("admindashboard");
						}
						
						if(result.get(0).getUserRole().equalsIgnoreCase("ITSUPPORT")) {
							logger.info("itsupportdashboard part");

							user.setStatus("itsupportdashboard");
						}
						
						if(result.get(0).getUserRole().equalsIgnoreCase("FINANCE")) {
							logger.info("financedashboard part");

							user.setStatus("financedashboard");
						}
						
						if(result.get(0).getUserRole().equalsIgnoreCase("member")) {
							logger.info("Member  part");

							user.setStatus("memberdashboard");
						}
						if(result.get(0).getUserRole().equalsIgnoreCase("agent")) {
							logger.info("Agent  part");
							user.setStatus("agentdashboard");
						}
						if(result.get(0).getUserRole().equalsIgnoreCase("clinicmanageremployee")){ 
							logger.info("Clinic Manager Employee  part");
							user.setStatus("employeedashboard");
						}
						if(result.get(0).getUserRole().equalsIgnoreCase("invmanageremployee")){ 
							logger.info("Investment Manager Employee  part");
							user.setStatus("invmanagerempdashboard");
						}
						if(result.get(0).getUserRole().equalsIgnoreCase("clinicnon-manageremployee")){ 
							logger.info("Clinic Non-Manager Employee  part");
							user.setStatus("clinicnonmanagerempdashboard");
						}
						if(result.get(0).getUserRole().equalsIgnoreCase("invnon-manageremployee")){ 
							logger.info("Investment Non-Manager  Employee  part");
							user.setStatus("invnonmanagerempdashboard");
						}
						
					}
					if(result.get(0).getStatus().equalsIgnoreCase("Waiting For Approval")){
						user.setStatus("Please wait for Admin approval and It is still under process");

					}
				}
				else {
					user.setStatus("Invalid Pass word Please try again");
				}
			}else {
			user.setStatus("Invalid User Name Please try again");
			//logger.info("No data found.");
		}
		
		}catch(Exception e){
			logger.info("BO Exception -->"+e.getMessage());
			user.setStatus("Network Error Please try again");

		}
		finally{
			result=null;
		}
		return user;
	}
	

	
	// -------------------Create a Member -------------------------------------------	
	public Member createMember(Member member) throws TrioException{
		logger.info("Inside createMember method-------------");
		int newTempCode=0;
		String newCodeFinal=null;
		try {
			member = dao.userExistingCheck("UserCheck",member);
			if(member.getStatus().equalsIgnoreCase("0")) {
				// -------------- Username is already registred Please try again. -----------
				member.setStatus("userexits");				
			}if(member.getStatus().equalsIgnoreCase("1")){
				// -------------- Username is successfully created. -----------
				member.setStatus("success");
			}
			if(member.getStatus().equalsIgnoreCase("2")){
				// -------------- Network issue Please try again. -----------
				member.setStatus("otherError");
			}
	
			// ----------- Only for success user name creation ------------
			if(member.getStatus().equalsIgnoreCase("success")) 
			{
				logger.info("Email ID -->"+member.getEmailID()); // 2
				logger.info("Country -->"+member.getCountry()); // 3
				logger.info("Phone Number -->"+member.getPhoneNumber()); //4
				logger.info("First Name -->"+member.getFirstName()); // 5
				logger.info("Last Name -->"+member.getLastName()); // 6
				logger.info("User Name -->"+member.getUsername()); // 7
				logger.info("Password Name -->"+member.getPassword()); // 8
				logger.info("Bank Name -->"+member.getBankName()); // 9
				logger.info("Account number -->"+member.getBankAcctNumber()); // 10
				logger.info("Member Type -->"+member.getActType()); // 11
				logger.info("No.of Clinics ---->"+member.getNoofclinics()); // 12		
				logger.info("Before Calling DAO");
				newTempCode=dao.getRandamCode(newTempCode,"CurrentGLGCode");
				logger.info("Successfully Called DAO");	
				logger.info("New Generated Randam Number ---------------->"+newTempCode);			
				/*if(member.getActType().equalsIgnoreCase("Silver"))  {
					newCodeFinal = "TRIOS"+newTempCode;
					logger.info("Silver New Generated Randam Number ---------------->"+newTempCode);
				}			
				if(member.getActType().equalsIgnoreCase("Gold"))  {
					newCodeFinal = "TRIOG"+newTempCode;
					logger.info("GOLD New Generated Randam Number ---------------->"+newTempCode);
				}
				if(member.getActType().equalsIgnoreCase("Platinum"))  {
					newCodeFinal = "TRIOP"+newTempCode;		
					logger.info("Platinum New Generated Randam Number ---------------->"+newTempCode);
				}*/		
				newCodeFinal = "TRIO000"+newTempCode;
				member.setMemberNumber(newCodeFinal);// New Member ID 
				logger.info("Generated New Code ---------------------->"+newCodeFinal);
			    // Calling for Save data
				logger.info("Before Calling DAO");
				member= dao.createMember(member);
				logger.info("Successfully Called DAO");
				// Email pushing calling 
				//Email.tipsMail(member);
				logger.info("Befor Calling the Member Email Send :::::::::::::::::::::::::::");
				boolean regStatus = Email.RegisterMail(member,newCodeFinal);
				logger.info("Successfully Called the Member Email Send :::::::::::::::::::::::::::");	
				logger.info("Befor Calling the Admin Email Send :::::::::::::::::::::::::::");
				if(regStatus==true) {
					Email.adminalertEmail(member,newCodeFinal);
				}
				else {
					logger.info("Exception occure while storing the Member registration email.....");
				}
				logger.info("Successfully Called the Admin Email Send :::::::::::::::::::::::::::");		
				logger.info("Successfully called Dao"+member.getStatus());
			}
		}catch(Exception e) {
			// -------------- Network issue Please try again. -----------
			member.setStatus("otherError");
			logger.info("BO Exception -->"+e.getMessage());
		}finally {
			
		}
		return member;
	}

	// ----------------- get Country list -----------------
	public HashMap<String,String> getCountry(HashMap<String,String> statemap)
	{
	ArrayList<CountryDetail> Clist=null;
	try {
			logger.info("Before Calling DAO");
			Clist = dao.getCountry();
			logger.info("Successfully Called DAO");
			for(CountryDetail country:Clist){
			statemap.put(country.getCountryName(), country.getState_name());
		}
	}catch(Exception e) {
		logger.info("BO - Exception -->"+e.getMessage());
	}
		return statemap;
	}
	
	
	// ----------------- get All state and category ----------------	
	public Map<String,Dropbox> getState(String country,HashMap<String,Dropbox> map){
		
		ArrayList<IndustryDetail> industrydetail=null;
		Dropbox box =null;
		try {
			box = new Dropbox();
			industrydetail= dao.getState(country,industrydetail);
			for(IndustryDetail industry:industrydetail){
				box.setCategory(industry.getIndustryName());
				box.setHname(industry.getCompanyName());			
		}
		//map.put(industry.getStateName(), box);
		}catch(Exception e) {
			logger.info("BO - Exception -->"+e.getMessage());
		}
		return map;
	}

	// -------------------------- Load ALL TRIO member list ---------------------
	public ArrayList<GLGMem> getAllMemberList(String requestType,ArrayList<GLGMem> myMemList){
	logger.info("Inside getAllMemberList method-------------");
	myMemList= dao.getAllMemberList(requestType,myMemList);
	logger.info("Inside getAllMemberList method-------------");
	return myMemList;
	}
		
	// ----------------- Approval for member register ---------------
	public User getApproved(User user,int userLoginPrimaryKey,String requestType){
		logger.info("Inside getApproved method-------------");
		logger.info("Before Calling DAO");
		user=  dao.getApproved(user,userLoginPrimaryKey,requestType);
		logger.info("Successfully Called DAO");
		if(user.getStatus().equalsIgnoreCase("success")){
			Email.PaymentApproveMail(user);
		}else{
			Email.PaymentRejectMail(user);
		}
		logger.info("Successfully Called DAO for Member Approval");
		return user;
	}
		
	public Member getMyProfile(Member member)
	{
		logger.info("Inside getMyProfile method-------------");
		logger.info("Before Calling DAO getMyProfile");
		member=dao.getMyProfile(member);
		logger.info("Successfully Called DAO getMyProfile");
		return member;
	}

	// ---------------- forget Password use check ------------------------------
	public User Checkuser(User user,int temp){
		logger.info("Inside Checkuser method-------------");
		if(temp ==1 )
		{
			logger.info("Before Calling DAO Checkuser");
			user = dao.Checkuser(user);
			logger.info("Successfully Called DAO Checkuser");
			logger.info("[Checkuser 1] User status ------>"+user.getStatus());
		if(user.getStatus().equalsIgnoreCase("success")){
			logger.info("Before Calling Email optMailsend");
			Email.optMailsend(user);
			logger.info("Successfully Called Email optMailsend");

		}
		else 
		{
			logger.info("------------ Inside else for non success");
		}
		}
		if(temp == 2) {
			logger.info("Before Calling DAO OtpCheck");
			user = dao.OtpCheck(user);	
			logger.info("Successfully Called DAO OtpCheck");
			logger.info("[Checkuser 2] User status ------>"+user.getStatus());
		}
			
		if(temp == 3) {
			logger.info("Before Calling DAO resetPassword");
			user = dao.resetPassword(user);	 
			logger.info("Successfully Called DAO resetPassword");
			logger.info("[Checkuser 3] User status ------>"+user.getStatus());
		}
			
			return user;
		}
		
	//--------- My profile update BO ---------
	public Member updateMyProfile(Member member){
		logger.info("Inside updateMyProfile method-------------");
		logger.info("Before Calling DAO updateMyProfile");
		member=dao.updateMyProfile(member);
		logger.info("Successfully Called DAO updateMyProfile");
		return member;
	}	
				
	// ---------- submit withdraw amount ---------------
	public Member submitWith(Member member){
		logger.info("Inside submitWith method-------------");
		logger.info("Before Calling DAO submitWith");
		dao.submitWith(member);
		logger.info("Successfully Called DAO submitWith");
		return member;
	}
	
	public Member addClinic(Member member){
		logger.info("Inside addClinic method-------------");
		try {
				logger.info("[BO] No.of Clinics  ---->"+member.getNoofclinics());
				logger.info("[BO] Clinic Name ---->"+member.getClinicName());
				logger.info("[BO] Clinic Name2 ---->"+member.getClinicName2());
				logger.info("[BO] Clinic Name3 ---->"+member.getClinicName3());
				logger.info("[BO] Clinic Name4 ---->"+member.getClinicName4());
				logger.info("[BO] Clinic Name5 ---->"+member.getClinicName5());
				logger.info("[BO] Clinic Name6 ---->"+member.getClinicName6());
				logger.info("[BO] Clinic Name7 ---->"+member.getClinicName7());
				logger.info("[BO] Clinic Name8 ---->"+member.getClinicName8());
				logger.info("[BO] Clinic Name9 ---->"+member.getClinicName9());
				logger.info("[BO] Clinic Name10 ---->"+member.getClinicName10());
				logger.info("[BO] Member ID ---->"+member.getMemberID());
				logger.info("Final Clinc name -->"+member.getClinicName()); 
				logger.info("Before Calling DAO addClinic");
				member= dao.addClinic(member);
				logger.info("Successfully Called DAO addClinic");				
				if(member.getStatus().equalsIgnoreCase("success")){
					logger.info("Before Calling Email AddClinicMail");
					Email.AddClinicMail(member);
					logger.info("Successfully Called Email AddClinicMail");				

				}else{
					logger.info("--------------- Inside else for non success----------");				

				}
				logger.info("Successfully called Dao"+member.getStatus());		
		}catch(Exception e) {
				// -------------- Network issue Please try again. -----------
			member.setStatus("failure");
			logger.info("BO Exception -->"+e.getMessage());
		}finally {
			
		}
			return member;
		}
	// openWithdraw
	public Member openWithdraw(Member member) {
		logger.info("Inside openWithdraw method-------------");
		try {
			logger.info("Member ID for Withdraw ------------>"+member.getMemberID());
			logger.info("Date ------------>"+member.getWithdrawDate());
			logger.info("Before Calling DAO");
			member=dao.openWithdraw(member);
			logger.info("Successfully Called DAO");
			if(member.getStatus().equalsIgnoreCase("Requested For Withdraw")){
				logger.info("Before Calling Email WithdrawrequestMail");
				Email.WithdrawrequestMail(member);
				logger.info("Successfully Called Email WithdrawrequestMail");				
				logger.info("Before Calling Email WithdrawalertMail");
				Email.WithdrawalertMail(member);
				logger.info("Successfully Called Email WithdrawalertMail");
				
			}else{
				logger.info("------------ Inside else for non-Requested For Withdraw------------");
			}
				logger.info("Successfully called Dao"+member.getStatus());		
		}catch(Exception e) {
				// -------------- Network issue Please try again. -----------
			member.setStatus("failure");
			logger.info("BO Exception -->"+e.getMessage());
		}finally {
			
		}
			return member;
		}
	
	// -------------------------- Load ALL TRIO member and Clinic list ---------------------
	public ArrayList<GLGMem> getAllClinicList(String requestType,ArrayList<GLGMem> glglist){		
		logger.info("Inside getAllClinicList method-------------");
		logger.info("Before Calling DAO getAllClinicList");
		glglist = dao.getAllClinicList(requestType,glglist);
		logger.info("Successfully Called DAO getAllClinicList");
		return glglist;

	}
		
	// -------------------------- Load all clinic  list for menu ---------------------
	public ArrayList<Clinic> getAllClinicList2(ArrayList<Clinic> list,String requestType){
		logger.info("Inside getAllClinicList2 method-------------");
		ArrayList<ClinicDetails> cliniclist=null;
		Clinic clinic;
		 try {
			 logger.info("Before Calling DAO getAllClinicList2");
			 cliniclist = dao.getAllClinicList2(cliniclist,requestType);
			 logger.info("Successfully Called DAO getAllClinicList2");
				if(cliniclist.size()>0) {
					int i=1;
					for(ClinicDetails c : cliniclist ) {
						clinic = new Clinic();
						logger.info("Clinic Name ------>"+c.getClinicName());
						clinic.setsNo(i);
						clinic.setPrimaryKey(c.getClinic_Details_ID());
						clinic.setClinicName(c.getClinicName());
						clinic.setClinicCode(c.getClinicCode());
						clinic.setPrimaryKey(c.getClinic_Details_ID());						
						clinic.setAddress(c.getClinicAddress());
						clinic.setPhoneNumber(c.getClinicPhoneNumber());
						clinic.setEmailID(c.getClinicEmailID());
						clinic.setName(c.getAgent_details().getAgentName()); 
						list.add(clinic);
						i++;
					}
				}
			}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
			}
			finally {
				
			}
			return list;
		}
			
		
	//Ledger Details
	public ArrayList<Member> getLedgerInfo(Member member,ArrayList<Member> mlist){
		ArrayList<Member> finalList=null;
		logger.info("Inside getLedgerInfo method-------------");
		try {
			finalList = new ArrayList<Member>();
			if(member.getRequestType()==1) {
				logger.info("----------Inside Request Type 1--------------");
				logger.info("Before Calling DAO getLedgerInfo");
				mlist = dao.getLedgerInfo(member,mlist);
				logger.info("Successfully Called DAO getLedgerInfo");
				finalList = mlist;
			}
			// Withdraw view
			if(member.getRequestType()==2) {
				logger.info("----------Inside Request Type 2--------------");
				logger.info("Before Calling DAO getLedgerInfo");
				mlist = dao.getLedgerInfo(member,mlist);
				logger.info("Successfully Called DAO getLedgerInfo");				
				ArrayList<Member> secList= new ArrayList<Member>();
				for(Member m:mlist) {
					Member memberNew = new Member();
					memberNew.setsNo(m.getsNo());
					memberNew.setInvestmentDate(m.getInvestmentDate());
					memberNew.setEnrollmentDate(m.getEnrollmentDate());
					memberNew.setWithdrawDate(m.getWithdrawDate());
					memberNew.setWithdrawAmount(m.getWithdrawAmount());
					memberNew.setMemberID(m.getMemberID());
					if(m.getStatus().equalsIgnoreCase("Requested For Withdraw")) {
						memberNew.setStatus("Withdraw Pending For Approval");
						memberNew.setWstatus("Requested for Withdraw");
					}
					else if(m.getStatus().equalsIgnoreCase("Approved For Withdraw")) {
						memberNew.setStatus("Amount Taken Already");
						memberNew.setWstatus("Can't Withdraw");
					}					
					else if(m.getStatus().equalsIgnoreCase("Rejected For Withdraw")) {
						memberNew.setStatus("Amount Rejected");
						memberNew.setWstatus("Can't Withdraw");
					}					
					else if(m.getStatus().equalsIgnoreCase("Not Available") || m.getStatus().equalsIgnoreCase("Available") ) {
						memberNew.setStatus(m.getStatus());
					}				
					logger.info("#####################################");
					logger.info("Database withdraw Date -->"+m.getWithdrawDate());
					logger.info("Current date -->"+Custom.getCurrentStringDate());	
					logger.info("#####################################");
					String v1 = m.getWithdrawDate().replace("-","");
					String v2 = Custom.getCurrentStringDate().replace("-","");					
					int withdrawdate=Integer.valueOf(v1);
					int currentdate=Integer.valueOf(v2);
					logger.info("**************************************");
					logger.info("After replace withdraw date -->"+withdrawdate);
					logger.info("After replace current date -->"+currentdate);
					logger.info("**************************************");					
					if(withdrawdate == currentdate && !m.getStatus().equalsIgnoreCase("Requested For Withdraw") 
							&& !m.getStatus().equalsIgnoreCase("Approved For Withdraw") 
							&& !m.getStatus().equalsIgnoreCase("Rejected For Withdraw")) {
						memberNew.setStatus("Available");
						memberNew.setWstatus("Withdraw");
					}					
					else if(withdrawdate > currentdate) {
			            logger.info("Date1 is after Date2");
						memberNew.setStatus("Not Available");
						memberNew.setWstatus("Can't Withdraw");
					}					
					else if(withdrawdate < currentdate && !m.getStatus().equalsIgnoreCase("Requested For Withdraw") 
							&& !m.getStatus().equalsIgnoreCase("Approved For Withdraw") 
							&& !m.getStatus().equalsIgnoreCase("Rejected For Withdraw") ) {
			            logger.info("Date1 is Before Date2");
						logger.info("Available");
						memberNew.setStatus("Available");
						memberNew.setWstatus("Withdraw");
					}		
					secList.add(memberNew);
			}
				finalList=secList; 
			}
			if(member.getRequestType()==3) {
				logger.info("----------Inside Request Type 3--------------");
				logger.info("Before Calling DAO getLedgerInfo");
				mlist = dao.getLedgerInfo(member,mlist);
				logger.info("Successfully Called DAO getLedgerInfo");
				finalList = mlist;
			}						
		return finalList;			
		} catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
			return mlist; 
		}
	}
	
	// Withdraw approval
	public User getApprovewithdraw(User user) {
		logger.info("Inside getApprovewithdraw method-------------");
		logger.info("Before Calling DAO getApprovewithdraw");
		user = dao.getApprovewithdraw(user);
		logger.info("Successfully Called DAO getApprovewithdraw");		
		if(user.getStatus().equalsIgnoreCase("success")){
			logger.info("Before Calling Email WithdrawapproveMail");
			Email.WithdrawapproveMail(user);
			logger.info("Successfully Called Email WithdrawapproveMail");		
		}else if(user.getStatus().equalsIgnoreCase("rejectSuccess")){
			logger.info("Before Calling Email WithdrawrejectMail");
			Email.WithdrawrejectMail(user);
			logger.info("Successfully Called Email WithdrawrejectMail");		
		}
		return user;
	}
		
	// edit Clinic information
	public Member editClinic(Member member){
		logger.info("Inside editClinic() method Bo");
		try {
			logger.info("[BO] No.of Clinics ---->"+member.getNoofclinics());
			logger.info("[BO] Member ID ---->"+member.getMemberID());
			logger.info("Successfully called Dao"+member.getStatus());		
		}catch(Exception e) {
				// -------------- Network issue Please try again. -----------
			member.setStatus("failure");
			logger.info("BO Exception -->"+e.getMessage());
		}finally {
			
		}
			return member;
		}
	
	// Load Agent data
	public ArrayList<Agent> getAgentInfo(ArrayList<Agent> agentList){
		logger.info("Inside getAgentInfo method-------------");
		logger.info("Before Calling DAO getAgentInfo");
		agentList = dao.getAgentInfo(agentList);
		logger.info("Successfully Called DAO getAgentInfo");
		return agentList;
	} 
	// Save Agent 
	public String savePerson(Agent agent) 	{
		String response=null;
		logger.info("Inside savePerson method-------------");
		logger.info("Agent Name --->"+agent.getName());
		logger.info("Agent EmailID --->"+agent.getEmailID());
		logger.info("Agent Phone Number --->"+agent.getPhoneNumber());
		if(agent.getAgentType().equalsIgnoreCase("Investment Agent")) {
			int temp = dao.getUniqueCode(1,"Investment Agent");
			agent.setAgentCode("AGINV" + temp);
		}
		if(agent.getAgentType().equalsIgnoreCase("Clinic Agent")) {
			int temp = dao.getUniqueCode(1,"Clinic Agent");
			agent.setAgentCode("AGCLI" + temp);
		}
		Member member = new Member();
		member.setUsername(agent.getEmailID());
		member = dao.userExistingCheck("UserCheck",member);
		if(member.getStatus().equalsIgnoreCase("0")) {
			// -------------- Username is already registred Please try again. -----------
			//member.setStatus("userexits");
			response= "userexits";
		}if(member.getStatus().equalsIgnoreCase("1")){
			response = dao.savePerson(agent);
			//member.setStatus("success");
		}
		
		
		if(response.equalsIgnoreCase("success")){
			Email.RegisterAgentMail(agent);
		}
		if(response.equalsIgnoreCase("userexits")){
			response="userexits";
		}
		else{
			logger.info("----- Exception occure while storing the Registration Agent -----------");
		}
		return response;
	}
	
	// Save Agent and Employee
	public String savePerson(Employee emp) {
		logger.info("Save Employee BO Calling-----------");
		int temp = dao.getUniqueCode(1,"employeeCode");
		emp.setEmployeeCode("EMP" + temp);
		String response = dao.savePerson(emp);
		if(response.equalsIgnoreCase("success")){
			Email.RegisterEmployeeMail(emp);
		}else{
			logger.info("----- Exception occure while storing the Registration Employee -----------");
		}
		return response;
	}

	// Agent Name List 
	public ArrayList<Agent> getAgentName(ArrayList<Agent> agentList){
		agentList = dao.getAgentName(agentList);
		return agentList;
	}
	// Employee Name list
	public ArrayList<Employee> getEmpName(ArrayList<Employee> empList, String empRole){
		empList = dao.getEmpName(empList,empRole);
		return empList;
	}
	
	// Update Agent 
	public String setAgentUpdate(Agent agent) {
		String response = dao.setAgentUpdate(agent);
		return response;
	}
	// Remove Agent
	public String setAgentRemove(int agPK){
		String response = dao.setAgentRemove(agPK);
		return response;
	}
	
	// Load Employee data
	public ArrayList<Employee> getEmployeeInfo(ArrayList<Employee> employeeList){
		employeeList = dao.getEmployeeInfo(employeeList);
		return employeeList;
	}
	
	// Update Employee 
	public String setEmployeeUpdate(Employee emp) {
		String response = dao.setEmployeeUpdate(emp);
		return response;
	}
	// Remove Employee
	public String setEmployeeRemove(int empPK){
		String response = dao.setEmployeeRemove(empPK);
		return response;
	}
		
	// Save Clinic data
	public String saveClinic(Clinic clinic) {
		String response="failure";
		try {
			int temp = dao.getUniqueCode(1,"clinicCode");
			clinic.setClinicCode("CLIC" + temp);
			if(clinic.getPhoneNumber()==null || clinic.getPhoneNumber()=="") {
				logger.info("Phone Number Null");
				clinic.setPhoneNumber("none");
				}
			if(clinic.getEmailID()==null || clinic.getEmailID()=="") {
				logger.info("Email ID Null");
				clinic.setEmailID("none");
				}
			if(clinic.getAddress()==null || clinic.getAddress()=="") {
				logger.info("Address Null");
				clinic.setAddress("none");
				}
			//clinic.setCl("none");

			response = dao.saveClinic(clinic);
			return response;
		}catch(Exception e) {
			logger.error("Exception  -->"+e.getMessage());
			return response;
		}

	}
		
	// Clinic Name Exist Check
	public String clinicExistCheck(String clinicName) {
		String response="failure";
		try {
			response = dao.clinicExistCheck(clinicName);
			return response;
		}catch(Exception e) {
			logger.error("Exception ------>"+e.getMessage());
			return response;
		}

	}
	
	// Agent Code Exist Check
	public String agentCodeExistCheck(String agentCode) {
		String response="failure";
		try {
			response = dao.agentCodeExistCheck(agentCode);
			return response;
		}catch(Exception e) {
			logger.error("Exception  ----------->"+e.getMessage());
			return response;
		}

	}
		
	// ----------- Clinic Name List  --------------
	public ArrayList<Clinic> getClinicName(ArrayList<Clinic> cliList){
		logger.info("[BO Clinic ]Before Clinic Name List ------------->"); 
		cliList = dao.getClinicName(cliList);
		logger.info("[BO Clinic ]After Clinic Name List ------------->"); 
		return cliList;
	}	
		
	//Update Clinic		
	public String setClinicUpdate(Clinic clinic) {
		String response = dao.setClinicUpdate(clinic);
		return response;
	}
	// Remove Agent
	public String setClinicRemove(int clinicPK){
		String response = dao.setClinicRemove(clinicPK);
		return response;
	}
	
	//---------- getAgentProfile -----
	public Agent getAgentProfile(Agent agent){
		return dao.getAgentProfile(agent);
	}
	
	//---------- getClinicAgentReport -----
	public ArrayList<Clinic> getClinicAgentReport(String agentCode, ArrayList<Clinic> clinicReportList){
		return dao.getClinicAgentReport(agentCode,clinicReportList);
	}
	
	//---------- getInvAgentReport -----
	public ArrayList<Member> getInvAgentReport(String agentCode, ArrayList<Member> invreportlist){
		return dao.getInvAgentReport(agentCode,invreportlist);
	}
	
	//---------- getAllClinicReportList -----
	public ArrayList<Clinic> getAllClinicReportList(Clinic clinic,ArrayList<Clinic> allClinicReportList){
			return dao.getAllClinicReportList(clinic,allClinicReportList);
	}
	
	// Invest Agent Name List 
	public ArrayList<Agent> getinvAgentName(ArrayList<Agent> invagentList){
		invagentList = dao.getinvAgentName(invagentList);
		return invagentList;
	}
	
	//--------- getAllInvAgentReport view for Admin Login ---
	public ArrayList<Member> getAllInvAgentReport(Member member,ArrayList<Member> invReportList){
		return dao.getAllInvAgentReport(member,invReportList);
	}
			
	//---------- getMemberClinic Report -----
	public ArrayList<Clinic> getAllMemberClinicReportList(Clinic clinic, ArrayList<Clinic> memberclinicReportList){
		return dao.getAllMemberClinicReportList(clinic,memberclinicReportList);
	}
	//---------- getMemberReport -----
	public ArrayList<Member> getAllMemberReportList(Member member, ArrayList<Member> memberReportList,String requestType,String status){
		if(requestType.equalsIgnoreCase("Member Report")){ 
			memberReportList= dao.getAllMemberReportList(member,memberReportList,status);
		}else if(requestType.equalsIgnoreCase("Payment Report")){
			memberReportList= dao.getAllPaymentReportList(member,memberReportList);
		}
		return memberReportList;
	}
	//------------ Employee Report ----------
	public ArrayList<Employee> getAllEmpReportList(Employee emp,ArrayList<Employee> empReportList){
		return dao.getAllEmpReportList(emp,empReportList);
	}
	//------------ Update Agent Profile ------------
	public Agent updateAgentProfile(Agent agent){
		return dao.updateAgentProfile(agent);
	}

	@Override
	public Employee getEmployeeProfile(Employee emp) {
		return dao.getEmployeeProfile(emp);
	}

	@Override
	public Employee updateEmpProfile(Employee emp) {
		return dao.updateEmpProfile(emp);
	}

	@Override
	public ArrayList<Employee> getEmpReport(String employeeCode,ArrayList<Employee> empreportlist) {
		return dao.getEmpReport(employeeCode,empreportlist);
	}
	
	public static String getAgentQuery(Agent agent) {
		logger.info("Agent Country Code --->"+agent.getSelectedCountry());
		logger.info("Agent Code --->"+agent.getAgentCode());
		logger.info("Agent Type--->"+agent.getAgentType());
		logger.info("Agent Name--->"+agent.getName());
		logger.info("Agent Bank Name--->"+agent.getBankName());
		String query="from AgentDetails where ";		
		// 1 condition
		if(agent.getSelectedCountry() == null && agent.getAgentCode() == null && 
				agent.getAgentType() == null && agent.getName() == null && agent.getBankName() == null ){
					query="from AgentDetails";
		}
		
		// 2
		if(agent.getSelectedCountry() != null){
			query = query + "country='"+agent.getSelectedCountry()+"'";
		}		
	
		// 3
		if(agent.getAgentCode() != null){
			
			// 3.1
			if(agent.getSelectedCountry() != null){
				query = query + " and agentCode='"+agent.getAgentCode()+"'";
			}			
			
			else {
				query = query + " agentCode='"+agent.getAgentCode()+"'";

			}
		}
		// 4
		if(agent.getAgentType() != null){		
		
			// 4.1
			if(agent.getSelectedCountry() != null ){
				// this will work even if 's' is NULL
				query = query + " and agentType='"+agent.getAgentType()+"'";
			}
			else {
				// 4.2
				if(agent.getAgentCode() != null){
					// this will work even if 's' is NULL
					query = query + " and agentType='"+agent.getAgentType()+"'";
				}
				else {
					query = query + " agentType='"+agent.getAgentType()+"'";
				}

			}
		}
	
		// 5
		if(agent.getName() != null){
			// 5.1
			if(agent.getAgentType() != null ){
				query = query + " and agentName='"+agent.getName()+"'";
			}
			else {
				// 5.2
				if(agent.getSelectedCountry() != null ){
					query = query + " and agentName='"+agent.getName()+"'";
				}
				else {
					// 5.3
					if(agent.getAgentCode() != null){
						query = query + " and agentName='"+agent.getName()+"'";
					}
					else {
						query = query + " agentName='"+agent.getName()+"'";
					}
				}
			}
			}
			
			// 6
			if(agent.getBankName() != null){
				// 6.1
				if(agent.getAgentType() != null ){
					query = query + " and agentBankName='"+agent.getBankName()+"'";
				}
				else {
					// 6.2
					if(agent.getSelectedCountry() != null ){
						query = query + " and agentBankName='"+agent.getBankName()+"'";
					}
					else {
						// 6.3
						if(agent.getAgentCode() != null){
							query = query + " and agentBankName='"+agent.getBankName()+"'";
						}
						else {

							// 6.4
							if(agent.getName() != null){
								query = query + " and agentBankName='"+agent.getBankName()+"'";
							}
							else {
								query = query + " agentBankName='"+agent.getBankName()+"'";
							}
						
						}
					}
				}
			}
		return query;
	
	}
	
	@Override
	public ArrayList<Agent> searchAgent(ArrayList<Agent> searchagList,Agent agent) {
		
		if(agent.getSelectedCountry().equalsIgnoreCase("All")) {
			agent.setSelectedCountry(null);
		}
		if(agent.getAgentCode().equalsIgnoreCase("All")) {
			agent.setAgentCode(null);//.setEmployeeCode(null);
		}
		if(agent.getName().equalsIgnoreCase("All")) {
			agent.setName(null);
		}
		if(agent.getAgentType().equalsIgnoreCase("All")) {
			agent.setAgentType(null);
		}		
		if(agent.getBankName().equalsIgnoreCase("All")) {
			agent.setBankName(null);//setAgentCode(null);
		}
		//String query="from AgentDetails where ";	
		String query = getAgentQuery(agent);		
		return dao.searchAgent(searchagList,agent,query);
	}
	public static String getEmployeeQuery(Employee emp) {
		logger.info("Employee Code --->"+emp.getEmployeeCode());
		logger.info("Employee Country --->"+emp.getSelectedCountry());
		logger.info("Employee Name --->"+emp.getName());
		logger.info("Employee Type --->"+emp.getEmpType());
		String query="from EmployeeDetails where ";		
		
		// 1 
		if(emp.getEmployeeCode() == null && emp.getSelectedCountry() == null && 
				emp.getName() == null && emp.getEmpType() == null){
					query="from EmployeeDetails";
		}
		
		// 2
		if(emp.getEmployeeCode() != null && !emp.getEmployeeCode().isEmpty()){
			query = query + "empCode='"+emp.getEmployeeCode()+"'";
		}		
		
		// 3 
		if(emp.getSelectedCountry() != null && !emp.getSelectedCountry().isEmpty()){
			// 3.1
			if(emp.getEmployeeCode() != null && !emp.getEmployeeCode().isEmpty()){
				query = query + " and country='"+emp.getSelectedCountry()+"'";
			}			
			else {
				query = query + " country='"+emp.getSelectedCountry()+"'";

			}
		}
		
		// 4
		if(emp.getName() != null && !emp.getName().isEmpty()){		
			// 4.1
			if(emp.getSelectedCountry() != null && !emp.getSelectedCountry().isEmpty()){
				// this will work even if 's' is NULL
				query = query + " and empName='"+emp.getName()+"'";
			}
			else {
				// 4.2
				if(emp.getEmployeeCode() != null && !emp.getEmployeeCode().isEmpty()){
					// this will work even if 's' is NULL
					query = query + " and empName='"+emp.getName()+"'";
				}
				else {
					query = query + " empName='"+emp.getName()+"'";
				}

			}
		}
	
		// 5
		if(emp.getEmpType() != null && !emp.getEmpType().isEmpty()){
			// 5.1
			if(emp.getName() != null && !emp.getName().isEmpty()){
				query = query + " and empType='"+emp.getEmpType()+"'";
			}
			else {
				// 5.2
				if(emp.getEmployeeCode() != null && !emp.getEmployeeCode().isEmpty()){
					query = query + " and empType='"+emp.getEmpType()+"'";
				}
				else {
					// 5.3
					if(emp.getSelectedCountry() != null && !emp.getSelectedCountry().isEmpty()){
						query = query + " and empType='"+emp.getEmpType()+"'";
					}
					else {
						query = query + " empType='"+emp.getEmpType()+"'";
					}
				}
			}
	}
		
		return query;
	}
	
	@Override
	public ArrayList<Employee> searchEmployee(ArrayList<Employee> searchempList, Employee emp){
		
		if(emp.getSelectedCountry().equalsIgnoreCase("All")) {
			emp.setSelectedCountry(null);
		}
		if(emp.getEmployeeCode().equalsIgnoreCase("All")) {
			emp.setEmployeeCode(null);
		}
		if(emp.getName().equalsIgnoreCase("All")) {
			emp.setName(null);
		}
		if(emp.getEmpType().equalsIgnoreCase("All")) {
			emp.setEmpType(null);
		}
		
		String query = getEmployeeQuery(emp);
		logger.info("BO Custom Query ----------->"+query);
		return dao.searchEmployee(searchempList,emp,query);
	}
	
	public ArrayList<Clinic> searchClinic(ArrayList<Clinic> searchclinicList,Clinic clinic){
		if(clinic.getName().equalsIgnoreCase("All")){
			System.out.println("---- [BO] Agent Name All --------");
			ArrayList<ClinicDetails> cliniclist=null;
			String requestType="All";
			cliniclist = dao.getAllClinicList2(cliniclist,requestType);
				if(cliniclist.size()>0) {
					int i=1;
					for(ClinicDetails c : cliniclist ) {
						clinic = new Clinic();
						logger.info("Clinic Name ------>"+c.getClinicName());
						clinic.setsNo(i);
						clinic.setPrimaryKey(c.getClinic_Details_ID());
						clinic.setClinicName(c.getClinicName());
						clinic.setClinicCode(c.getClinicCode());
						clinic.setAddress(c.getClinicAddress());
						clinic.setPhoneNumber(c.getClinicPhoneNumber());
						clinic.setEmailID(c.getClinicEmailID());
						clinic.setName(c.getAgent_details().getAgentName()); 
						searchclinicList.add(clinic);
						i++;
					}
				}
			return searchclinicList;
		}else{
			System.out.println("---- [BO] Choosed Agent Name  --------");
			Map<String,Integer> agentMap = new HashMap<String,Integer>();
			ArrayList<Agent> agentList = new ArrayList<Agent>();
			ArrayList<String> agList = new ArrayList<String>();
			agentList = dao.getAgentName(agentList);
		    for(Agent a: agentList){
		    	agentMap.put(a.getAgentCode(),a.getPrimaryKey());
		    	agList.add(a.getName() + "-" +a.getAgentCode());

		    }
			String agentCode[] = clinic.getName().split("-");
			int value1 = agentMap.get(agentCode[1]);
			clinic.setAgentPK(value1);
			return dao.searchClinic(searchclinicList,clinic);
		}		
	}
	
	//-------------- Search Partner Clinic ------------
	public ArrayList<GLGMem> searchPartnerClinic(ArrayList<GLGMem> searchPartnerClinic, Clinic clinic){	
		searchPartnerClinic = dao.searchPartnerClinic(searchPartnerClinic,clinic);
		return searchPartnerClinic;
	}
	
	//--------- Get Sales Report for Clinic --------
	public ArrayList<Agent> getSalesClincReport(ArrayList<Agent> searchMonthlyClinicReport,String selectedMonth,String reportName,String name){
		searchMonthlyClinicReport = dao.getSalesClincReport(searchMonthlyClinicReport,selectedMonth,reportName,name);
		return searchMonthlyClinicReport;
	}
	
	//--------- Get Sales Report for Partner --------
	public ArrayList<Member> getSalesPartnerReport(ArrayList<Member> searchMonthlyPartnerReport, String selectedMonth,String memberName){
		searchMonthlyPartnerReport = dao.getSalesPartnerReport(searchMonthlyPartnerReport, selectedMonth, memberName);
		return searchMonthlyPartnerReport;
	}
	
	//--------- Get Sales Report for Employee --------
	public ArrayList<Employee> getSalesEmployeeReport(ArrayList<Employee> searchMonthlyEmployeeReport,String selectedMonth,String reportName,String empName){
		searchMonthlyEmployeeReport=dao.getSalesEmployeeReport(searchMonthlyEmployeeReport, selectedMonth, reportName,empName);
		return searchMonthlyEmployeeReport;
	}
	
	//-------- Get My Clinic Details For ClinicAgent in Sales
	public ArrayList<Clinic> getSalesMyClinicReport(ArrayList<Clinic> myClinicReport, Clinic clinic){
		myClinicReport = dao.getSalesMyClinicReport(myClinicReport, clinic);
		return myClinicReport;
	}
	
	//---------- Get MyEmployee Details for Sales ClinicAgent
	public ArrayList<Employee> getSalesMyEmployeeView(ArrayList<Employee> myEmpReport, String refEmploy){
		myEmpReport=dao.getSalesMyEmployeeView(myEmpReport, refEmploy);
		return myEmpReport;
	}
	
	//------------ Get ClinicName in Partner SalesReport ---------
	public Member MyClinicNameView(Member member){
		return dao.MyClinicNameView(member);
	}
	
	//--------- get EmpClinicDetails in Employee Report
	public ArrayList<Clinic> getSalesMyEmpClinicReport(ArrayList<Clinic> myClinicReport, Clinic clinic){
		myClinicReport = dao.getSalesMyEmpClinicReport(myClinicReport, clinic);
		return myClinicReport;
	}
	
	//---------------- Get EmpAgentDetails in Sales Report -------------
	public ArrayList<Agent> getSalesMyEmpAgentReport(ArrayList<Agent> myAgentReport, Agent agent){
		myAgentReport = dao.getSalesMyEmpAgentReport(myAgentReport, agent);
		return myAgentReport;
	}
	
	//------------- Get EditClinicList Info -------------
	public ArrayList<Clinic> getEditClinicName(ArrayList<Clinic> clinicList,String trioNumber){
		clinicList = dao.getEditClinicName(clinicList, trioNumber);
		return clinicList;
	}
	
	//-----------------  ClinicUpdate on Partner  -----------
	public Member setPartnerClinicUpdate(Member member){
		return dao.setPartnerClinicUpdate(member);
	}
	
	//-------- get PartnerName in a list -------
	public ArrayList<Member> getPartnerName(ArrayList<Member> parList){
		return dao.getPartnerName(parList);
	}
	
	//--------- Get All Partner Filter Data -----------
	public static String getAllPartnerQuery(GLGMem member) {
		logger.info("Partner Code --->"+member.getMemberID());
		logger.info("Partner Country --->"+member.getSelectedCountry());
		logger.info("Partner Status --->"+member.getStatus());
		logger.info("Partner AgentCode --->"+member.getAgentCode());
		String query="from UserDetail where NOT memberID='TRIO681111' and NOT memberID='TRIO581111' and NOT memberID='TRIO481111' and ";		
		// 1 
		if(member.getMemberID() == null && member.getSelectedCountry() == null && 
				member.getStatus() == null && member.getAgentCode() == null){
					query="from UserDetail where NOT memberID='TRIO681111' and NOT memberID='TRIO581111' and NOT memberID='TRIO481111'";
		}
		
		// 2
		if(member.getMemberID() != null && !member.getMemberID().isEmpty()){
			query = query + "memberID='"+member.getMemberID()+"'";
		}		
		
		// 3 
		if(member.getSelectedCountry() != null && !member.getSelectedCountry().isEmpty()){
			// 3.1
			if(member.getMemberID() != null && !member.getMemberID().isEmpty()){
				query = query + " and country='"+member.getSelectedCountry()+"'";
			}			
			else {
				query = query + " country='"+member.getSelectedCountry()+"'";
			}
		}
		
		// 4
		if(member.getStatus() != null && !member.getStatus().isEmpty()){		
			// 4.1
			if(member.getSelectedCountry() != null && !member.getSelectedCountry().isEmpty()){
				// this will work even if 's' is NULL
				query = query + " and payStatus='"+member.getStatus()+"'";
			}
			else {
				// 4.2
				if(member.getMemberID() != null && !member.getMemberID().isEmpty()){
					// this will work even if 's' is NULL
					query = query + " and payStatus='"+member.getStatus()+"'";
				}
				else {
					query = query + " payStatus='"+member.getStatus()+"'";
				}

			}
		}
	
		// 5
		if(member.getAgentCode() != null && !member.getAgentCode().isEmpty()){
			// 5.1
			if(member.getStatus() != null && !member.getStatus().isEmpty()){
				query = query + " and agent_code='"+member.getAgentCode()+"'";
			}
			else {
				// 5.2
				if(member.getMemberID() != null && !member.getMemberID().isEmpty()){
					query = query + " and agent_code='"+member.getAgentCode()+"'";
				}
				else {
					// 5.3
					if(member.getSelectedCountry() != null && !member.getSelectedCountry().isEmpty()){
						query = query + " and agent_code='"+member.getAgentCode()+"'";
					}
					else {
						query = query + " agent_code='"+member.getAgentCode()+"'";

					}
				}
			}
	}
		
		return query;
	}
	
	@Override
	public ArrayList<GLGMem> searchAllPartner(ArrayList<GLGMem> searchpartnerList, GLGMem member){
		
		if(member.getSelectedCountry().equalsIgnoreCase("All")) {
			member.setSelectedCountry(null);
		}
		if(member.getMemberID().equalsIgnoreCase("All")) {
			member.setMemberID(null);
		}
		if(member.getStatus().equalsIgnoreCase("All")) {
			member.setStatus(null);
		}
		if(member.getAgentCode().equalsIgnoreCase("All")) {
			member.setAgentCode(null);
		}
		
		String query = getAllPartnerQuery(member);
		logger.info("BO Custom Query ----------->"+query);
		return dao.searchAllPartner(searchpartnerList,member,query);
	}
	
	//-------------- Search Not Registered Clinic ------------
	public ArrayList<GLGMem> searchPartnerNonClinic(ArrayList<GLGMem> searchPartnerNonClinic, GLGMem glgmember){
		searchPartnerNonClinic = dao.searchPartnerNonClinic(searchPartnerNonClinic,glgmember);
		return searchPartnerNonClinic;
	}
	
}

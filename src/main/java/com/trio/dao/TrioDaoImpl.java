package com.trio.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.CrossOrigin;


import com.trio.dto.Agent;
import com.trio.dto.Clinic;
import com.trio.dto.Dropbox;
import com.trio.dto.Employee;
import com.trio.dto.GLGMem;
import com.trio.dto.Member;
import com.trio.dto.User;
import com.trio.model.AgentDetails;
import com.trio.model.ClinicDetails;
import com.trio.model.CommOverrDetail;
import com.trio.model.CountryDetail;
import com.trio.model.EmployeeDetails;
import com.trio.model.IndustryDetail;
//import com.trio.model.MemberId;
import com.trio.model.RandamNumber;
import com.trio.model.UserDetail;
import com.trio.model.UserLogin;
import com.trio.util.Custom;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Singleton
public class TrioDaoImpl implements TrioDao {

	public static final Logger logger = LoggerFactory.getLogger(TrioDaoImpl.class);


	@PersistenceContext(unitName="trio-pu")
	private EntityManager entityManager;
	
	//@Value("${emp.details.table}") 
    //private String empDetails;	
	

	// File Upload Validate TRIO Number --------------------
	@Transactional(value="transactionManager")
	public String gglNumberCheck(String gglNumber) {
		Query query;
		String returnValue="failure";
		try {
			query=entityManager.createQuery("from UserDetail where memberID=?");
			query.setParameter(1, gglNumber);
			UserDetail userDetail=(UserDetail)query.getSingleResult();	
			if(userDetail.getMemberID().isEmpty()) {
				returnValue="failure";
			}
			else{
				returnValue = "success";
			}
			return returnValue;
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());

			return returnValue;
		}
		finally {
			
		}
		//return returnValue;
	}

	// ---------------- forget Password use check ------------------------------
	@Transactional(value="transactionManager")
	public User Checkuser(User user){
		Query query;
		String tempEmail=null;
		try {	//user = dao.Checkuser(user);
		query=entityManager.createQuery("from UserLogin where username=?");
		query.setParameter(1, user.getUsername());
		UserLogin userloing=(UserLogin)query.getSingleResult();	
		logger.info("DB User Name -->"+userloing.getUsername());
		logger.info("DB User Email ID -->"+userloing.getUserDetails().get(0).getEmail1());
		tempEmail=userloing.getUserDetails().get(0).getEmail1();
		if(userloing.getUsername().equalsIgnoreCase(user.getUsername())){
			// generate otp and send to mail.
			String tempDate=Custom.getCurrentDateandTime().replaceAll(":", "");
			logger.info("Current date ---->"+tempDate);
			String otp = user.getUsername().substring(0, 3) + tempDate;// String.valueOf(Custom.getCurrentDate());
			userloing.setUserOtp(otp);
			entityManager.merge(userloing);			
			user.setStatus("success");
			user.setEmail_ID(tempEmail);
			user.setOtp(otp);
			user.setUsername(userloing.getUsername());
		}
		else {
			user.setStatus("failure");

		}
			return user;
		}catch(Exception e) {
			user.setStatus("failure");
			return user;
		}finally {
			
		}
	}
			
			
// ---------------- forget Password use check ------------------------------
	@Transactional(value="transactionManager")
	public User OtpCheck(User user){
		Query query;
		try {
		logger.info("OTP Number -->"+user.getMemberID());	
		//user = dao.Checkuser(user);
		query=entityManager.createQuery("from UserLogin where userOtp=?");
		query.setParameter(1, user.getMemberID());
		UserLogin userloing=(UserLogin)query.getSingleResult();	
		//logger.info("DB User Name -->"+userloing.getUsername());
		if(userloing.getUserOtp().isEmpty()) {
			user.setStatus("failure");
		}
		else{
			user.setStatus("success");
		}
		
			return user;
		}catch(Exception e) {
			user.setStatus("failure");
			return user;
		}finally {
			
		}
	}
				
	// ---------------- reSet Password ------------------------------
	@Transactional(value="transactionManager")
	public User resetPassword(User user){
		Query query;
		try {
		logger.info("New Password -->"+user.getPassword());	
		logger.info("User Name -->"+user.getUsername());	
		query=entityManager.createQuery("from UserLogin where username=?");
		query.setParameter(1, user.getUsername());
		UserLogin userlogin=(UserLogin)query.getSingleResult();	
		//UserLogin userlogin = entityManager.find(UserLogin.class, userloing.getUser_Login_ID());
		userlogin.setPassword(user.getPassword());
		entityManager.merge(userlogin);
		user.setStatus("success");
		return user;
		}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
			user.setStatus("failure");
			return user;
		}finally {
			
		}
	}
	
	// -------------------Login check -------------------------------------------

	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public List<UserLogin> userLogin(User user,List<UserLogin> result){
		Query query=null;
		//List<UserLogin> resultDB;
		logger.info("Inside userLogin() DAO");
		try {
			if(user.getId()==1) {
				logger.info("Inside userLogin() DAO UserName Validate Check ....");
				query=entityManager.createQuery("from UserLogin where username=?");
				query.setParameter(1, user.getUsername());
				result=(List<UserLogin>)query.getResultList();
				logger.info("Inside userLogin() DAO UserName Validate end ....");

			}
			if(user.getId()==2){
				logger.info("Inside userLogin() DAO UserPassword Validate start ....");
				query=entityManager.createQuery("from UserLogin where password=? and username=?");
				query.setParameter(1, user.getPassword());
				query.setParameter(2, user.getUsername());
				result=(List<UserLogin>)query.getResultList();	
				logger.info("User Details Primary Key --->"+result.get(0).getUserDetails().get(0).getUser_Details_ID());
			     UserDetail userdetails = entityManager.find(UserDetail.class, result.get(0).getUserDetails().get(0).getUser_Details_ID());
			     logger.info("[DAO] Member ID ------------------>"+userdetails.getMemberID());
			     user.setMemberNumber(userdetails.getMemberID());
				 logger.info("Inside userLogin() DAO UserPassword Validate end ....");
 
			}
		} 
		catch(IndexOutOfBoundsException ie) {
			logger.error("IndexOutOfBoundsException -->"+ie.getMessage());
			user.setStatus("Network Error Please try again");
		}
		catch(Exception e) {
			logger.error("Exception -->"+e.getMessage());
			user.setStatus("Network Error Please try again");
		}
		finally {
			query=null;
		}

		return result;
	}
	
	// -------------------------------- User Name Check For Member Registration 
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public Member userExistingCheck(String requestType,Member member){
	    Query query=null;
	    List<UserLogin> result;
		try {
			if(requestType.equalsIgnoreCase("UserCheck")){
				query=entityManager.createQuery("from UserLogin where username=?");
				query.setParameter(1, member.getUsername());
				result=(List<UserLogin>)query.getResultList();
				if(result.size()>0) {
					member.setStatus("0"); // UI purpose	 already exit		
					}
				else {
					member.setStatus("1"); // UI purpose	 non exit		

				}
				//return member;
			}
			
// ------------------------- Member ID check ---------------
			
			if(requestType.equalsIgnoreCase("memberIDNotValid")){
				query=entityManager.createQuery("from MemberId where member_Number=?");
				query.setParameter(1,member.getMemberID());
				result=(List<UserLogin>)query.getResultList();
				if(result.size()>0) {
					member.setStatus("Exist"); // UI purpose			
					}
				else {
					member.setStatus("NotExist"); // UI purpose			

				}
			}
		return member;
 
		}catch(Exception e) {
			member.setStatus("2"); // UI purpose
						logger.info("Exception -->"+e.getMessage());
			return member;
		}
	   
	}
	
	// -------------------Create a Member -------------------------------------------
	
	@Transactional(value="transactionManager")
	public Member createMember(Member member){
		logger.info("Inside createMember() DAO");
		UserLogin parent = null;
	    List<UserDetail> childlist = null;
		try{
				
				// Save in login table 
				parent = new UserLogin();
				parent.setUsername(member.getUsername());
				parent.setPassword(member.getPassword());
				parent.setStatus("Waiting For Approval");
				parent.setUserRole("member");
				
				// Save in Userdetails table 
				UserDetail child=new UserDetail();
				child.setMemberID(member.getMemberNumber()); 
				child.setFirstname(member.getFirstName());
				child.setLastname(member.getLastName());
				child.setEmail1(member.getEmailID());
				child.setPhonenumber1(member.getPhoneNumber());
				child.setCountry(member.getCountry());
				child.setBankName(member.getBankName());
				child.setBankAcctNumber(member.getBankAcctNumber());
				child.setAdminFees(member.getAdminFees());
				child.setTotalFees(member.getTotalFees());
				child.setNoofclinics(member.getNoofclinics()); 
				child.setAgent_code(member.getAgentCode()); 
				child.setTotalFees(20000000*member.getNoofclinics()); 
				child.setNpwp_Number(member.getNpwpNumber()); 
				logger.info("Total Fees ------>"+child.getTotalFees()); 
				//child.setClinicName("Not Registered"); 
				child.setClinic_status("Not Registered");
				child.setUserLogin(parent);
				
				child.setAcctType(member.getActType());
				child.setPayAmt(member.getPayAmt());
				child.setPayStatus("Waiting");
				child.setAcctCreated_date(Custom.getCurrentDate());
				childlist = new ArrayList<UserDetail>();
				childlist.add(child);
				parent.setUserDetails(childlist);
			    entityManager.persist(parent);
				//member.setStatus("1"); // UI purpose
				logger.info("Successfully saved data");


		}catch(Exception e){
			//member.setStatus("2"); // UI purpose
			logger.info("Exception -->"+e.getMessage());
		}finally{
			 parent = null;
		     childlist = null;
		     //result=null;
		     //query=null;
		}
		return member;
	}

// ---------------- get country and state ---------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<IndustryDetail> getState (String country,ArrayList<IndustryDetail> industrylist){
		Query query=null;
		try {
			query=entityManager.createQuery("from IndustryDetail where countryName=? ");
			query.setParameter(1, country);
			industrylist=(ArrayList<IndustryDetail>)query.getResultList();
			
		}catch(Exception e){
			
		}finally
		{
			
		}
		return industrylist;
	}
	
	
// -------------------- get Country list ---------------------
	
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<CountryDetail> getCountry(){
		ArrayList<CountryDetail> result=null;
		Query query=null;
		try {
			query=entityManager.createQuery("from CountryDetail");
			result=(ArrayList<CountryDetail>)query.getResultList();
		}catch(Exception e) {
			logger.info("Exception --------------->"+e.getMessage());
		}finally{
			
		}		
		return result;
	}
	
	
// ----------------------------- Unique Code for TRIO -------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public int getUniqueCode(int newCode,String requestType) {
		Query query=null;
		ArrayList<RandamNumber> result=null;

		try {
			query=entityManager.createQuery("from RandamNumber");
			result=(ArrayList<RandamNumber>)query.getResultList();
			// TRIO Investment Agent
			if(requestType.equalsIgnoreCase("Investment Agent"))
			{
				int temp = result.get(0).getAgent_Randam_Number();
				logger.info("InvestmentAgent Existing code ---------->"+temp);
				newCode = result.get(0).getAgent_Randam_Number() + 1;
				logger.info("InvestmentAgent Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set agent_Randam_Number="+newCode);
			    query.executeUpdate();
				logger.info("New InvestmentAgent Code Updated Successfully");
				}
			
			// TRIO Clinic Agent 
			if(requestType.equalsIgnoreCase("Clinic Agent"))
			{
				int temp = result.get(0).getAgent_clinic_Randam_Number();
				logger.info("Clinic Agent Existing code ---------->"+temp);
				newCode = result.get(0).getAgent_clinic_Randam_Number() + 1;
				logger.info("Clinic Agent Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set agent_clinic_Randam_Number="+newCode);
			    query.executeUpdate();
				logger.info("New ClinicAgent Code Updated Successfully");
			}
			
			
			// TRIO Employee Code 
			if(requestType.equalsIgnoreCase("employeeCode"))
			{
				int temp = result.get(0).getEmp_Randam_Number();
				logger.info("Employee Existing code ---------->"+temp);
				newCode = result.get(0).getEmp_Randam_Number() + 1;
				logger.info("Employee Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set emp_Randam_Number="+newCode);
			    query.executeUpdate();
				logger.info("New Employee Code Updated Successfully");
			}
				
		
			// TRIO Clinic Code 
			if(requestType.equalsIgnoreCase("clinicCode"))
			{
				int temp = result.get(0).getClinic_Randam_Number();
				logger.info("Clinic Existing code ---------->"+temp);
				newCode = result.get(0).getClinic_Randam_Number() + 1;
				logger.info("Clinic Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set clinic_Randam_Number="+newCode);
			    query.executeUpdate();
				logger.info("New Clinic Code Updated Successfully");
			}
			
						
			
		}catch(Exception e) {
			logger.info("Exception ------------>"+e.getMessage());
		}finally
		{
			
		}
			
		return newCode;

	}
	
	
	// ---------------------------- get Random member Code --------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public int getRandamCode(int newCode,String requestType) {
		Query query=null;
		ArrayList<RandamNumber> result=null;

		try {
			query=entityManager.createQuery("from RandamNumber");
			result=(ArrayList<RandamNumber>)query.getResultList();	
			// TRIO Investment Agent
			if(requestType.equalsIgnoreCase("InvestmentAgent"))
			{
				int temp = result.get(0).getAgent_Randam_Number();
				logger.info("InvestmentAgent Existing code ---------->"+temp);
				newCode = result.get(0).getAgent_Randam_Number() + 1;
				logger.info("InvestmentAgent Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set current_Member_Number="+newCode);
			    query.executeUpdate();
				logger.info("New InvestmentAgent Code Updated Successfully");
				}
			
			// TRIO Clinic Agent 
			if(requestType.equalsIgnoreCase("ClinicAgent"))
			{
				int temp = result.get(0).getClinic_Randam_Number();
				logger.info("ClinicAgent Existing code ---------->"+temp);
				newCode = result.get(0).getClinic_Randam_Number() + 1;
				logger.info("ClinicAgent Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set current_Member_Number="+newCode);
			    query.executeUpdate();
				logger.info("New ClinicAgent Code Updated Successfully");
			}
			
			
			if(requestType.equalsIgnoreCase("CurrentGLGCode"))
			{
				int temp = result.get(0).getCurrent_Member_Number();
				logger.info("Existing code ---------->"+temp);
				newCode = result.get(0).getCurrent_Member_Number() + 1;
				logger.info("Generated New code ------------>"+newCode);			
				query=entityManager.createQuery("update RandamNumber set current_Member_Number="+newCode);
			    query.executeUpdate();
				logger.info("New Member Code Updated Successfully");
				}
			if(requestType.equalsIgnoreCase("CurrentGroupCode")){
				int currentGroupCode = result.get(0).getCurrent_Group_Number();
				newCode = currentGroupCode + 1;
				logger.info("Generated New Group Code  ------------>"+newCode);		
				query=entityManager.createQuery("update RandamNumber set Current_Group_Number="+newCode);
			    query.executeUpdate();
				logger.info("New Group Code Updated Successfully");

			}
			
			if(requestType.equalsIgnoreCase("treeNumber")){
				int currentTreeNumber = result.get(0).getTreeRandamNumber();
				newCode = currentTreeNumber + 1;
				logger.info("Generated New Group Code  ------------>"+newCode);		
				query=entityManager.createQuery("update RandamNumber set treeRandamNumber="+newCode);
			    query.executeUpdate();
				logger.info("New Group Code Updated Successfully");

			}
			
			if(requestType.equalsIgnoreCase("bookingInvoiceNumber")){
				int currentTreeNumber = result.get(0).getBooking_Randam_Number();
				newCode = currentTreeNumber + 1;
				logger.info("Generated New Booking Invoice Number  ------------>"+newCode);		
				query=entityManager.createQuery("update RandamNumber set booking_Randam_Number="+newCode);
			    query.executeUpdate();
				logger.info("New Booking Code Updated Successfully");

			}
			// update New code 


			return newCode;
		
		
		
		}catch(Exception e) {
			logger.info("Exception ------------>"+e.getMessage());
		}finally
		{
			
		}
				return newCode;	
		}
		
	
		
	// ---------------------- load ALL TRIO  Member Data -------------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public ArrayList<GLGMem> getAllMemberList(String requestType,ArrayList<GLGMem> myMemList){
		Query query=null;
		GLGMem glgmember;
		ArrayList<UserLogin> resultList;
		int a=0;
		//logger.info("Member Number ---------------->"+memberNumber);
		try {
			//query=entityManager.createQuery("from UserLogin where status=?");
			//query.setParameter(1, "Waiting For Approval");
			
			if(requestType.equalsIgnoreCase("WaitingForApproval")) {
				query=entityManager.createQuery("from UserLogin where status=?  and userRole=? order by user_Login_ID desc");
				query.setParameter(1, "Waiting For Approval");
				query.setParameter(2, "member");				
				resultList=(ArrayList<UserLogin>)query.getResultList();	
				logger.info("Total Member Size ------------>"+resultList.size());
					//int a=0;
					for(int i=0;i<resultList.size();i++){
						logger.info("User Login Primary Key --------------->"+resultList.get(i).getUser_Login_ID());
						
						glgmember = new GLGMem();					
						glgmember.setMemberStatus(resultList.get(i).getStatus());
						glgmember.setUserLoginPrimaryKey(resultList.get(i).getUser_Login_ID());
						
						// Testing
						logger.info("Member ID ----------->"+resultList.get(i).getUserDetails().get(a).getMemberID());
						logger.info("Member Name  ----------->"+resultList.get(i).getUserDetails().get(a).getFirstname());
						logger.info("Member Email  ----------->"+resultList.get(i).getUserDetails().get(a).getEmail1());
						logger.info("Member Phone  ----------->"+resultList.get(i).getUserDetails().get(a).getPhonenumber1());
						logger.info("Member Type ----------->"+resultList.get(i).getUserDetails().get(a).getAcctType());
						logger.info("Member Total Amount  ----------->"+resultList.get(i).getUserDetails().get(a).getTotalFees());
						
						//UserDetail userdetails = entityManager.find(UserDetail.class, resultList.get(i).getUser_Login_ID());	

						glgmember.setMemberID(resultList.get(i).getUserDetails().get(a).getMemberID());
						glgmember.setMemberName(resultList.get(i).getUserDetails().get(a).getFirstname()+"  "+resultList.get(i).getUserDetails().get(a).getLastname());
						glgmember.setMemberEmail(resultList.get(i).getUserDetails().get(a).getEmail1());
						glgmember.setMemberPhone(resultList.get(i).getUserDetails().get(a).getPhonenumber1());
						glgmember.setMemberType(resultList.get(i).getUserDetails().get(a).getAcctType());
						glgmember.setTotalAmount(resultList.get(i).getUserDetails().get(a).getTotalFees());
						glgmember.setNoofclinics(resultList.get(i).getUserDetails().get(a).getNoofclinics()); 
						glgmember.setPaymentStatus(resultList.get(i).getUserDetails().get(a).getPayStatus());
						//glgmember.setClinicName(resultList.get(i).getUserDetails().get(a).getClinicName()); 
						glgmember.setStatus(resultList.get(i).getUserDetails().get(a).getClinic_status()); 

						myMemList.add(glgmember);
					//}
					logger.info("All Member Size --------------->"+myMemList.size());
				}
					
			}
			if(requestType.equalsIgnoreCase("All")) {
				query=entityManager.createQuery("from UserLogin where userRole=? order by user_Login_ID desc");
				query.setParameter(1, "member");
				resultList=(ArrayList<UserLogin>)query.getResultList();	
				logger.info("Total Member Size ------------>"+resultList.size());
				int j=0;
				for(int i=0;i<resultList.size();i++){
					logger.info("User Login Primary Key --------------->"+resultList.get(i).getUser_Login_ID());
					
					logger.info("Member ID ----------->"+resultList.get(i).getUserDetails().get(j).getMemberID());
					logger.info("Member Name  ----------->"+resultList.get(i).getUserDetails().get(j).getFirstname());
					logger.info("Member Email  ----------->"+resultList.get(i).getUserDetails().get(j).getEmail1());
					logger.info("Member Phone  ----------->"+resultList.get(i).getUserDetails().get(j).getPhonenumber1());
					logger.info("Member Type ----------->"+resultList.get(i).getUserDetails().get(j).getAcctType());
					logger.info("Member Total Amount  ----------->"+resultList.get(i).getUserDetails().get(j).getTotalFees());
					
					
					//UserDetail userdetails = entityManager.find(UserDetail.class, resultList.get(i).getUser_Login_ID());	
					glgmember = new GLGMem();
					//logger.info("Member ID ---------------->"+userdetails.getMemberID());
					glgmember.setMemberStatus(resultList.get(i).getStatus());
					glgmember.setUserLoginPrimaryKey(resultList.get(i).getUser_Login_ID());					
					glgmember.setMemberID(resultList.get(i).getUserDetails().get(j).getMemberID());
					glgmember.setMemberName(resultList.get(i).getUserDetails().get(j).getFirstname()+"  "+resultList.get(i).getUserDetails().get(j).getLastname());
					glgmember.setMemberEmail(resultList.get(i).getUserDetails().get(j).getEmail1());
					glgmember.setMemberPhone(resultList.get(i).getUserDetails().get(j).getPhonenumber1());
					glgmember.setMemberType(resultList.get(i).getUserDetails().get(j).getAcctType());
					glgmember.setTotalAmount(resultList.get(i).getUserDetails().get(j).getTotalFees());
					glgmember.setNoofclinics(resultList.get(i).getUserDetails().get(j).getNoofclinics()); 
					glgmember.setPaymentStatus(resultList.get(i).getUserDetails().get(j).getPayStatus());
					//glgmember.setClinicName(resultList.get(i).getUserDetails().get(j).getClinicName()); 
					glgmember.setStatus(resultList.get(i).getUserDetails().get(j).getClinic_status()); 
					myMemList.add(glgmember);
					logger.info("User memberID --------------->"+glgmember.getMemberID());
				}
			}
			if(requestType.equalsIgnoreCase("Approved")) {
				query=entityManager.createQuery("from UserLogin where status=? and  userRole=? order by user_Login_ID desc");
				query.setParameter(1, "Approved");
				query.setParameter(2, "member");

				resultList=(ArrayList<UserLogin>)query.getResultList();	
				logger.info("Total Member Size ------------->"+resultList.size());
				for(int i=0;i<resultList.size();i++){
					logger.info("First Name ------>"+resultList.get(i).getUserDetails().get(a).getFirstname());
					if(resultList.get(i).getUserDetails().get(a).getClinic_status().equalsIgnoreCase("Not Registered") || resultList.get(i).getUserDetails().get(a).getClinic_status()==null) {
						glgmember = new GLGMem();
						logger.info("Clinic Status ------>"+resultList.get(i).getUserDetails().get(a).getClinic_status());
						glgmember.setMemberStatus(resultList.get(i).getStatus());
						glgmember.setUserLoginPrimaryKey(resultList.get(i).getUser_Login_ID());
						glgmember.setMemberID(resultList.get(i).getUserDetails().get(a).getMemberID());
						glgmember.setMemberName(resultList.get(i).getUserDetails().get(a).getFirstname()+"  "+resultList.get(i).getUserDetails().get(a).getLastname());
						glgmember.setMemberEmail(resultList.get(i).getUserDetails().get(a).getEmail1());
						glgmember.setMemberPhone(resultList.get(i).getUserDetails().get(a).getPhonenumber1());
						glgmember.setMemberType(resultList.get(i).getUserDetails().get(a).getAcctType());
						glgmember.setTotalAmount(resultList.get(i).getUserDetails().get(a).getTotalFees());
						glgmember.setNoofclinics(resultList.get(i).getUserDetails().get(a).getNoofclinics()); 
						glgmember.setPaymentStatus(resultList.get(i).getUserDetails().get(a).getPayStatus());
						glgmember.setStatus(resultList.get(i).getUserDetails().get(a).getClinic_status()); 						
						myMemList.add(glgmember);
					}
				}
			}	
		}catch(Exception e) {
			//logger.info("Exception ----------->"+e.getMessage());
			logger.error("Exception -->"+e.getMessage());
		}finally{
			
		}		
		return myMemList;
	
	}		

//	------------------ Approval -------------------------
	@Transactional(value="transactionManager")
	//@SuppressWarnings (value="unchecked")
	public User getApproved(User user,int userLoginPrimaryKey,String requestType){
		logger.info("Inside DAO getApprove Method ----------");
		logger.info("Primary Key ----------------->"+userLoginPrimaryKey);
		try{
			UserLogin userlogin = entityManager.find(UserLogin.class, userLoginPrimaryKey);
			
			if(requestType.equalsIgnoreCase("Approve")) {		
				UserDetail userdetails = userlogin.getUserDetails().get(0);
				String memberTemp = userdetails.getMemberID();
				logger.info("Member ID ---------->"+userdetails.getMemberID());				
				userlogin.setStatus("Approved");
				entityManager.merge(userlogin);
				entityManager.flush();
				entityManager.clear();				
				userdetails.setAcct_Approved_date(Custom.getCurrentDate()); 				
				entityManager.merge(userdetails);
				entityManager.flush();
				entityManager.clear();				
				int temp=0;
				int finalAmount= 0;
				temp = (int)(userdetails.getTotalFees()*(20.0f/100.0f));
				finalAmount = temp / 4;	
				/*if(userdetails.getAcctType().equalsIgnoreCase("silver")) {
					// 25% 
					//userdetails.getAcctType();
					userdetails.getTotalFees();
					temp = (int)(userdetails.getTotalFees()*(25.0f/100.0f));
					finalAmount = temp / 4;
					
				}
				if(userdetails.getAcctType().equalsIgnoreCase("gold")) {
					temp = (int)(userdetails.getTotalFees()*(30.0f/100.0f));
					finalAmount = temp / 4;		
				}
				if(userdetails.getAcctType().equalsIgnoreCase("platinum")) {
					temp = (int)(userdetails.getTotalFees()*(35.0f/100.0f));					
					finalAmount = temp / 4;
				}*/
				for(int i=0;i<=3;i++) {				
					CommOverrDetail comm = new CommOverrDetail();
					comm.setMember_Number(memberTemp);
					comm.setInvestment_date(Custom.getCurrentDate());
					comm.setStatus("Not Available");
					
					if(i==3) {
						comm.setWithdraw_amt(finalAmount + userdetails.getTotalFees());
		
					}else {
						comm.setWithdraw_amt(finalAmount);

					}
					entityManager.persist(comm);
					entityManager.flush();
					entityManager.clear();
				}
				
				// Response Code 				
				user.setStatus("success");
				user.setEmail_ID(userdetails.getEmail1());
				user.setUsername(userdetails.getFirstname() + " " + userdetails.getLastname());
				user.setMemberID(userdetails.getMemberID());
				user.setAccoutType(userdetails.getAcctType());
				user.setNoofclinics(userdetails.getNoofclinics());
				user.setAcct_Approved_date(userdetails.getAcct_Approved_date()); 
			}
			if(requestType.equalsIgnoreCase("Reject")) { 
				userlogin.setStatus("Rejected");
				entityManager.merge(userlogin);
				//UserDetail userdetails = entityManager.find(UserDetail.class, userLoginPrimaryKey);
				UserDetail userdetails = userlogin.getUserDetails().get(0);
				user.setEmail_ID(userdetails.getEmail1());
				user.setMemberID(userdetails.getMemberID()); 
				user.setAcct_Approved_date(Custom.getCurrentDate()); 
				user.setStatus("rejectSuccess");
			}			
			logger.info("Member data is successfully saved.");
		}catch(Exception e) {
			user.setStatus("failure");
						logger.info("Exception -->"+e.getMessage());
		}finally{
			
		}
		return user;
	}
	
// ------------------ view my profile ---------------------
	@Transactional(value="transactionManager")
	public Member getMyProfile(Member member){
		//Query query;
		try {
			UserLogin userlogin = entityManager.find(UserLogin.class, Integer.valueOf(member.getUserloginPrimaryKeyString()));
			member.setUsername(userlogin.getUsername());
		 	member.setActType(userlogin.getUserDetails().get(0).getAcctType());
		 	member.setBankName(userlogin.getUserDetails().get(0).getBankName());
		 	member.setBankAcctNumber(userlogin.getUserDetails().get(0).getBankAcctNumber());
		 	member.setCountry(userlogin.getUserDetails().get(0).getCountry());
		 	member.setEmailID(userlogin.getUserDetails().get(0).getEmail1());
		 	member.setPhoneNumber(userlogin.getUserDetails().get(0).getPhonenumber1());
		 	member.setFirstName(userlogin.getUserDetails().get(0).getFirstname());
		 	member.setLastName(userlogin.getUserDetails().get(0).getLastname());
		 	member.setNoofclinics(userlogin.getUserDetails().get(0).getNoofclinics()); 
		 	member.setTotalAmount(userlogin.getUserDetails().get(0).getTotalFees()); 
		 	member.setMemberID(userlogin.getUserDetails().get(0).getMemberID());
		 	member.setMember1_primaryKey(userlogin.getUserDetails().get(0).getUser_Details_ID()); 
		 	member.setNpwpNumber(userlogin.getUserDetails().get(0).getNpwp_Number()); 
		 	logger.info("Primary Key ------------------>"+member.getMember1_primaryKey()); 
		}catch(Exception e){
			logger.info("Exception -->"+e.getMessage());
		} finally {
			
		}
		return member;
	}
	
			// ------------------- load Organization list --------------
			@Transactional(value="transactionManager")
			@SuppressWarnings (value="unchecked")
			public ArrayList<String> getName(ArrayList<String> namelist, Member member) {
				Query query=null;
			try{
			 	//query=entityManager.createQuery("select org.name from OrganizationList as org where org.countryName=? and org.stateName=? and org.category=?");
				query=entityManager.createNativeQuery("select org.name from organization_list as org where org.country_name=? and org.state_name=? and org.category=?");
				query.setParameter(1, member.getSelectedCountry());
				query.setParameter(2, member.getSelectedState());
				query.setParameter(3, member.getCategoryname());		
				namelist=(ArrayList<String>)query.getResultList();
				logger.info("Name list size ---------------->"+namelist.size());
				if(namelist.size() > 0) {
					logger.info("Name list ------------->"+namelist.get(0));
				}
				return namelist;	
			}catch(Exception e) {
					logger.info("Exception --->"+e.getMessage());
				}finally {
					
				}
			return namelist;	
			}
				
		// ---------------- Update Payment ------------------------------
		@Transactional(value="transactionManager")
		public Member UpdatePayment(Member member)
		{
			Query query=null;
			UserDetail result;
			try {
				query=entityManager.createQuery("UPDATE UserDetail  set payStatus = 'Payment Uploaded' WHERE memberID='" +member.getMemberNumber()+"'");
				//String x= "Alex Ubalton '" + alex + "' Final round ";
			    query.executeUpdate();
				logger.info("Payment Status is updated Successfully");	
				query=null;
				query=entityManager.createQuery("from UserDetail WHERE memberID='" +member.getMemberNumber()+"'");
				result = (UserDetail) query.getSingleResult();
				logger.info("Email ID -->"+result.getEmail1());
				member.setEmailID(result.getEmail1());
				return member;
			}catch(Exception e ) {
				//status=false;
			}
			finally {
				query=null;
				
			}
			return member;
		
		}
		
		//---------- Update My Profile ---------------
		@Transactional(value="transactionManager")
		public Member updateMyProfile(Member member){
		logger.info("--- Inside updateMyProfile Method ---");
		UserDetail userdetails = null;
			try{
				// int tempPrimary = Integer.valueOf(member.getUserloginPrimaryKeyString());
				 userdetails = entityManager.find(UserDetail.class, member.getMember1_primaryKey());
				 logger.info("User Details Primary id---------->"+member.getMember1_primaryKey()); 
				 userdetails.setFirstname(member.getFirstName());
				 userdetails.setLastname(member.getLastName());
				 userdetails.setEmail1(member.getEmailID());
				 userdetails.setPhonenumber1(member.getPhoneNumber());
				 userdetails.setCountry(member.getCountry());
				 userdetails.setBankName(member.getBankName());
				 userdetails.setBankAcctNumber(member.getBankAcctNumber());
				 userdetails.setNoofclinics(member.getNoofclinics()); 
				 userdetails.setTotalFees(20000000*member.getNoofclinics()); 
				 userdetails.setNpwp_Number(member.getNpwpNumber()); 
				 logger.info("Total Amount --------->"+userdetails.getTotalFees()); 
				 entityManager.merge(userdetails);	
				 logger.info("-------- After Merge details --------->"); 
				 member.setStatus("success");
			}catch(Exception e){   
				 member.setStatus("failure");
				logger.info("Exception -->"+e.getMessage());
			}
			finally{
				 entityManager.flush();
				 entityManager.clear();
			}
		
			return member;
		}
		
		//---------- submit withdraw amount ---------------
		
		@Transactional(value="transactionManager")
		public Member submitWith(Member member){
		logger.info("--- Inside submitWith Method ---");
			try{
				
			}catch(Exception e){
				logger.info("Exception -->"+e.getMessage());
				//			logger.info("Exception -->"+e.getMessage());
			}
			finally{
				
			}
		
			return member;
		}
		
		// -------------Open Withdraw -------------- 
		@Transactional(value="transactionManager")
		public Member openWithdraw(Member member){
			Query query = null;
			logger.info("Dao Member ID for Withdraw ------------>"+member.getMemberID());
			logger.info("Dao Date ------------>"+member.getWithdrawDate());
			logger.info("----------- Inside openWithdraw() DAO ----------");
			try{
				query=entityManager.createQuery("from CommOverrDetail where Member_Number=? and withdraw_date=?");					
				query.setParameter(1, member.getMemberID());
				query.setParameter(2, member.getWithdrawDate());
				CommOverrDetail comm = (CommOverrDetail)query.getSingleResult();
				logger.info("[DAO] CommOverrDetail MemberID ---------->"+comm.getMember_Number());
				logger.info("[DAO] CommOverrDetail Withdraw Date ---------->"+comm.getWithdraw_date());
				comm = entityManager.find(CommOverrDetail.class,comm.getCom_Overr_ID());
				logger.info("[DAO] Status ---------->"+comm.getStatus());
				comm.setStatus("Requested For Withdraw"); 
				logger.info("[DAO] Status ---------->"+comm.getStatus());
			    entityManager.merge(comm);	
			    UserDetail userdetails=new UserDetail();
			    query=entityManager.createQuery("from UserDetail where memberID=?");
				query.setParameter(1, member.getMemberID());
				userdetails = (UserDetail)query.getSingleResult();
				member.setEmailID(userdetails.getEmail1());
				member.setWithdrawAmount(comm.getWithdraw_amt()); 
			    member.setStatus("Requested For Withdraw");

			}catch(Exception e) {
				member.setStatus("failure");
							logger.info("Exception -->"+e.getMessage());
			}
			return member;
		}
		// ------------- Add Clinic -------------- 
		@Transactional(value="transactionManager")
		@SuppressWarnings("unchecked")
		public Member addClinic(Member member){
			Query query = null;
			
			logger.info("----------- Inside AddClinic() DAO ----------");
			try{
				
				    // Alex Ublaton
				    
				    UserDetail userdetails=new UserDetail();
					logger.info("[DAO] MemberID ---------->"+member.getMemberID());
					query=entityManager.createQuery("from UserDetail where memberID=?");					
					query.setParameter(1, member.getMemberID());					
					UserDetail user = (UserDetail)query.getSingleResult();
					userdetails = entityManager.find(UserDetail.class,user.getUser_Details_ID());
					//logger.info("[DAO] MemberID ---------->"+userdetails.getMemberID());	
					logger.info("[DAO] Clinic Status ---------->"+userdetails.getClinic_status());
					logger.info("[DAO] Clinic Created Date ---------->"+userdetails.getAdd_clinic_date());
					ClinicDetails child=null;
					//userdetails.setClinicName(member.getClinicName()); 
					userdetails.setAdd_clinic_date(Custom.getCurrentDate());
					userdetails.setClinic_status("Registered");
					
					for(int i =0;i<member.getNoofclinics();i++) {
						logger.info("[DAO] addClinic Clinic PK ------------->"+member.clinicPKs[i]);
						child = new ClinicDetails();
						child = entityManager.find(ClinicDetails.class,member.clinicPKs[i]);
						child.setClinicStatus("not available");
						child.setUserdetails(userdetails);
					    entityManager.persist(child);	
					    entityManager.flush();
					    entityManager.clear();
					}
		    // end 
				    
				    //query=null;
					query=entityManager.createQuery("from CommOverrDetail WHERE Member_Number='" +member.getMemberID()+"'");
					List<CommOverrDetail> result = (ArrayList<CommOverrDetail>)query.getResultList();//.getSingleResult();
					logger.info("Commission and Overriding Size ------------>"+result.size());
				//	Dropbox box = Custom.getFutureDate(Custom.getCurrentDate());
					Dropbox box = Custom.getFutureDate();
					logger.info("Date 1 -------->"+box.getDate1());
					logger.info("Date 2 -------->"+box.getDate2());
					logger.info("Date 3 -------->"+box.getDate3());
					logger.info("Date 4 -------->"+box.getDate4());

					int j =1;
					for(CommOverrDetail comm : result) {
						logger.info("Current Date for Enrollment --------->"+Custom.getCurrentDate());
						comm.setEnrollment_date(Custom.getCurrentDate());
						if(j==1) {
							comm.setWithdraw_date(box.getDate1());

						}
						if(j==2) {
							comm.setWithdraw_date(box.getDate2());
					
								 }
						if(j==3) {
							comm.setWithdraw_date(box.getDate3());

						         }
						if(j==4) {
							comm.setWithdraw_date(box.getDate4());

				         }
						entityManager.merge(comm);	
					    entityManager.flush();
					    entityManager.clear();
					    j++;
					}
					member.setBookingdate(userdetails.getAdd_clinic_date());
					member.setEmailID(userdetails.getEmail1()); 
					member.setStatus("success");
				    logger.info("Successfully saved data");		
			}catch(Exception e){
					member.setStatus("failure");
					logger.info("Exception -->"+e.getMessage());
			}finally{
				
			}
			return member;
		}
		
		// ---------------------- load All Clinic and Member Information -------------------------
		@Transactional(value="transactionManager")
		@SuppressWarnings (value="unchecked")
		public ArrayList<GLGMem> getAllClinicList(String requestType,ArrayList<GLGMem> glglist){
			logger.info("--------------- [DAO] Inside getAllClinicList --------");
			Query query=null;
			GLGMem glgmember;
			ArrayList<UserLogin> resultList;
	
			int a=0;
			try {			
				
				if(requestType.equalsIgnoreCase("Approved")) {
					query=entityManager.createQuery("from UserLogin where status=? and userRole=? order by user_Login_ID desc");
					query.setParameter(1, "Approved");
					query.setParameter(2, "member");
					resultList=(ArrayList<UserLogin>)query.getResultList();	
					logger.info("Total Member Size ----------->"+resultList.size());
					for(int i=0;i<resultList.size();i++){
						//UserDetail userdetails = entityManager.find(UserDetail.class, resultList.get(i).getUser_Login_ID());	
						UserDetail userdetails = resultList.get(i).getUserDetails().get(a);
						
						logger.info("First Name ------>"+userdetails.getFirstname()+ " " +userdetails.getLastname());
						
						if(resultList.get(i).getUserDetails().get(a).getClinic_status().equalsIgnoreCase("Registered")){
							glgmember = new GLGMem();
							glgmember.setMemberStatus(resultList.get(i).getStatus());
							glgmember.setUserLoginPrimaryKey(resultList.get(i).getUser_Login_ID());
							logger.info("Member PrimaryKey ------>"+resultList.get(i).getUser_Login_ID());
							glgmember.setMemberID(userdetails.getMemberID());
							glgmember.setMemberName(userdetails.getFirstname()+"  "+userdetails.getLastname());
							glgmember.setMemberEmail(userdetails.getEmail1());
							glgmember.setMemberPhone(userdetails.getPhonenumber1());
							glgmember.setMemberType(userdetails.getAcctType());
							glgmember.setTotalAmount(userdetails.getTotalFees());
							glgmember.setNoofclinics(userdetails.getNoofclinics());
							glgmember.setPaymentStatus(userdetails.getPayStatus());
							glgmember.setStatus(userdetails.getClinic_status());
							
							glglist.add(glgmember);
						}

					}
					logger.info("---------- After add a memberName to glglist ------>");
				}	
			}catch(Exception e) {
				//e.printStackTrace();
				logger.error("Exception ----------->"+e.getMessage());
			}finally{
				
			}		
			return glglist;
		
		}	
		
	    // List the All Clinics view Menu
		// ---------------------- load All Clinic and Member Information -------------------------
		@Transactional(value="transactionManager")
		@SuppressWarnings (value="unchecked")
		public ArrayList<ClinicDetails> getAllClinicList2(ArrayList<ClinicDetails> clinicresultList,String requestType){
			logger.info("--------------- [DAO] Inside getAllClinicList2 --------");
			Query query=null;
			try {			
				logger.info("------------ [DAO] Inside try getAllClinicList2 -------------");
				if(requestType.equalsIgnoreCase("All")) {
	        				logger.info("-------------------- All Clinic data  -----------------");
					query=entityManager.createQuery("from ClinicDetails");
					clinicresultList=(ArrayList<ClinicDetails>)query.getResultList();
					logger.info("------- List Size ---------"+clinicresultList.size());
					logger.info("-------------------- After Call the Query for All Clinic data  -----------------");
				}
				else {
					logger.info("-------------------- No Clinic data found -----------------");
				}

				
			}catch(Exception e) {
				logger.info("Exception ----------->"+e.getMessage());
			}finally{
				
			}		
			return clinicresultList;				
		}	
				
				
		// ------------------ Get Ledger Info ---------------------
		@Transactional(value="transactionManager")
		@SuppressWarnings("unchecked")
		public ArrayList<Member> getLedgerInfo(Member member,ArrayList<Member> list){
			//ArrayList<Member> listValue=null;
			Query query;
			List<CommOverrDetail> result=null;
			UserLogin userlogin=null;
			UserDetail userdetails;
			try {
				int Sno=1;
				if(member.getRequestType()==3) {
					query=entityManager.createQuery("from CommOverrDetail where status=?");
					query.setParameter(1, "Requested For Withdraw");
					result = (ArrayList<CommOverrDetail>)query.getResultList();//.getSingleResult();
					logger.info("Condition 1 Database Table Size ---------->"+result.size());
					logger.info("Condition 1 Database Table Size is not equal to 0 ---------->"+result.size());
						for(CommOverrDetail c : result) {
							member = new Member();	
							member.setsNo(Sno);							
							member.setInvestmentDate(c.getInvestment_date());
							member.setEnrollmentDate(c.getEnrollment_date());
						//	String[] out = c.getWithdraw_date().split("-");
							//member.setWithdrawDate(out[2] +"-"+out[0] +"-"+ out[1]);							
							logger.info("DAO Withdraw date for request type 3 -->"+c.getWithdraw_date());
							member.setWithdrawDate(c.getWithdraw_date());					
							
							member.setWithdrawAmount(c.getWithdraw_amt());
							member.setStatus(c.getStatus());
							query=entityManager.createQuery("from UserDetail where memberID=?");
							query.setParameter(1, c.getMember_Number());
							userdetails = (UserDetail)query.getSingleResult();//.getSingleResult();				
							member.setMemberID(userdetails.getMemberID());
							member.setActType(userdetails.getAcctType());
							member.setTotalAmount(userdetails.getTotalFees());
							Sno++;
							list.add(member);
						}
					//}
					logger.info("Condition 1 Database Table Size ----------->"+list.size()); 
				}
				if(member.getRequestType()==2) {

					userlogin = entityManager.find(UserLogin.class, Integer.valueOf(member.getUserloginPrimaryKeyString()));
					logger.info("Member Number -------->"+userlogin.getUserDetails().get(0).getMemberID());
					query=entityManager.createQuery("from CommOverrDetail where Member_Number=?");
					query.setParameter(1, userlogin.getUserDetails().get(0).getMemberID());
					result = (ArrayList<CommOverrDetail>)query.getResultList();//.getSingleResult();				
					for(CommOverrDetail comm : result) {
						member = new Member();					
						member.setsNo(Sno);
						
						member.setInvestmentDate(comm.getInvestment_date());
						member.setEnrollmentDate(comm.getEnrollment_date());
						//String[] out = comm.getWithdraw_date().split("-");
						//member.setWithdrawDate(out[2] +"-"+out[0] +"-"+ out[1]);
						logger.info("DAO Withdraw date for request type 2 -->"+comm.getWithdraw_date());
						member.setWithdrawDate(comm.getWithdraw_date());
						member.setWithdrawAmount(comm.getWithdraw_amt());
						member.setStatus(comm.getStatus());
						member.setPercentage("20%");
						/*if(userlogin.getUserDetails().get(0).getAcctType().equalsIgnoreCase("silver")) {
							member.setPercentage("25%");
						}
						if(userlogin.getUserDetails().get(0).getAcctType().equalsIgnoreCase("gold")) {
							member.setPercentage("30%");
						}
						if(userlogin.getUserDetails().get(0).getAcctType().equalsIgnoreCase("platinum")) {
							member.setPercentage("35%");
						}*/
						member.setMemberID(userlogin.getUserDetails().get(0).getMemberID());
						member.setActType(userlogin.getUserDetails().get(0).getAcctType());
						member.setTotalAmount(userlogin.getUserDetails().get(0).getTotalFees());
						
						
						Sno++;
						list.add(member);
					}
					logger.info("Database Table Size ---------->"+result.size());

				
				}
				
				else {
					userlogin = entityManager.find(UserLogin.class, Integer.valueOf(member.getUserloginPrimaryKeyString()));
					logger.info("Member Number -------->"+userlogin.getUserDetails().get(0).getMemberID());
					query=entityManager.createQuery("from CommOverrDetail where Member_Number=?");
					query.setParameter(1, userlogin.getUserDetails().get(0).getMemberID());
					result = (ArrayList<CommOverrDetail>)query.getResultList();//.getSingleResult();				
					for(CommOverrDetail comm : result) {
						member = new Member();					
						member.setsNo(Sno);
						
						member.setInvestmentDate(comm.getInvestment_date());
						member.setEnrollmentDate(comm.getEnrollment_date());
						//String[] out = comm.getWithdraw_date().split("-");
						//member.setWithdrawDate(out[2] +"-"+out[0] +"-"+ out[1]);
						logger.info("DAO Withdraw date else -->"+comm.getWithdraw_date());
						member.setWithdrawDate(comm.getWithdraw_date());
						member.setWithdrawAmount(comm.getWithdraw_amt());
						member.setStatus(comm.getStatus());
						member.setPercentage("20%");
						/*if(userlogin.getUserDetails().get(0).getAcctType().equalsIgnoreCase("silver")) {
							member.setPercentage("25%");
						}
						if(userlogin.getUserDetails().get(0).getAcctType().equalsIgnoreCase("gold")) {
							member.setPercentage("30%");
						}
						if(userlogin.getUserDetails().get(0).getAcctType().equalsIgnoreCase("platinum")) {
							member.setPercentage("35%");
						}*/
						member.setMemberID(userlogin.getUserDetails().get(0).getMemberID());
						member.setActType(userlogin.getUserDetails().get(0).getAcctType());
						member.setTotalAmount(userlogin.getUserDetails().get(0).getTotalFees());
						
						
						Sno++;
						list.add(member);
					}
					logger.info("Database Table Size ---------->"+result.size());

				}
			return list;
			}catch(Exception e){
				logger.info("Exception -->"+e.getMessage());
			} finally {
				
			}
			return list;
		}

// Approve withdraw 
	@Transactional(value="transactionManager")
	public User getApprovewithdraw(User user) {
		Query query;
		try {
			query=entityManager.createQuery("from CommOverrDetail where Member_Number=? and status=? and withdraw_date=?");
			query.setParameter(1, user.getMemberID());
			query.setParameter(2, "Requested For Withdraw");
			query.setParameter(3, user.getWithdrawDate());
			CommOverrDetail commResult = (CommOverrDetail)query.getSingleResult();//.getSingleResult();	
			
			UserDetail userdetails=new UserDetail();
		    query=entityManager.createQuery("from UserDetail where memberID=?");
			query.setParameter(1, user.getMemberID());
			userdetails = (UserDetail)query.getSingleResult();
			user.setEmail_ID(userdetails.getEmail1());
		    user.setAcct_Approved_date(Custom.getCurrentDate());
			
			if(user.getRequestTypeStr().equalsIgnoreCase("Approvewithdraw")) {
				commResult.setStatus("Approved For Withdraw");
				entityManager.merge(commResult);	
			    entityManager.flush();
			    entityManager.clear();
			    user.setWithdrawAmount(commResult.getWithdraw_amt());
			    user.setStatus("success");
			}
			else if(user.getRequestTypeStr().equalsIgnoreCase("Rejectwithdraw")) {
				commResult.setStatus("Rejected For Withdraw");
				entityManager.merge(commResult);	
			    entityManager.flush();
			    entityManager.clear();
			    user.setStatus("rejectSuccess");
			}
			
		}catch(Exception e) {
			user.setStatus("failure");
						logger.info("Exception -->"+e.getMessage());
		}finally {
			
		}
		return user;
	}
	
// -------------------------- Load Agent data ----------------------
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public ArrayList<Agent> getAgentInfo(ArrayList<Agent> agentList){
	Query query;
	Agent agent;	
	try {
		query = entityManager.createQuery("from AgentDetails");
		//query.setParameter(1, "clinicagent");
	   List<AgentDetails> agdblist = (ArrayList<AgentDetails>)query.getResultList();
	   int i=1;
	   if(agdblist.size() > 0) {
		   for(AgentDetails ad : agdblist) {
			   	agent = new Agent();
			   	agent.setsNo(i);
			   	agent.setPrimaryKey(ad.getAgent_Details_ID());
		    	agent.setAgentCode(ad.getAgentCode());
		    	agent.setName(ad.getAgentName());
		    	agent.setPhoneNumber(ad.getAgentPhoneNumber());
		    	agent.setAddress(ad.getAgentAddress());
		    	agent.setEmailID(ad.getAgentEmailID());
		    	agent.setBankName(ad.getAgentBankName());
		    	agent.setAccountNumber(ad.getAgentAccountNumber());
		    	agent.setBankBranchName(ad.getAgentBankBranchName());
		    	agent.setAgentType(ad.getAgentType());
		    	agent.setRefEmploy(ad.getRefEmpCode()); 
		    	agent.setSelectedCountry(ad.getCountry()); 
		    	agent.setCreatedDate(ad.getAcctCreated_date());
		    	agentList.add(agent);
		    	i++;
		   }
		   
	   }else {
		   
	   }
		 return agentList;
	 }catch(Exception e) {
		 			logger.info("Exception -->"+e.getMessage());
	 }finally {
		 
	 }
	 return agentList; 
	}

// ------------------------- Save Agent --------------------
	@Transactional(value="transactionManager")
	public String savePerson(Agent agent) {
			String response="";
			logger.info("Dao Agent save Called.");
			UserLogin userlogin;
			try {
				// Save in userlogin table 
				userlogin = new UserLogin();
				userlogin.setUsername(agent.getEmailID());
				userlogin.setPassword("test");
				userlogin.setStatus("Approved");
				userlogin.setUserRole("agent");				
				// Save in AgentDetails table 
				AgentDetails child=new AgentDetails();
				child.setAgentCode(agent.getAgentCode()); // this need to be auto generated.
				child.setAgentName(agent.getName());
				logger.info("Agent Name ---->"+child.getAgentName());
				child.setAgentAddress(agent.getAddress());
				child.setAgentAccountNumber(agent.getAccountNumber());//.setMemberID(member.getMemberNumber()); 
				child.setAgentBankBranchName(agent.getBankBranchName());
				child.setAgentBankName(agent.getBankName());
				child.setAgentEmailID(agent.getEmailID()); 
				logger.info("Bank Name ---->"+child.getAgentBankName());
				child.setAgentPhoneNumber(agent.getPhoneNumber());
				logger.info("Phone Number ---->"+child.getAgentPhoneNumber());
				child.setAgentType(agent.getAgentType()); 
				child.setCountry(agent.getSelectedCountry());
				logger.info("Agent Type ---->"+child.getAgentType());
				String referenceEmp = agent.getRefEmploy();
				String refEmpCode = referenceEmp.substring(referenceEmp.lastIndexOf("-")+1);
				logger.info("Reference Employee Code --------->"+refEmpCode); 
				child.setRefEmpCode(refEmpCode); 
				child.setAcctCreated_date(Custom.getCurrentDate()); 
				child.setUserLogin(userlogin);
				List<AgentDetails> childlist = new ArrayList<AgentDetails>();
				childlist.add(child);
				userlogin.setAgentDetails(childlist);
			    entityManager.persist(userlogin);
				logger.info("Agent Data is Stored successfully....");
				response="success";
				return response;
			}catch(Exception e) {
							logger.info("Exception -->"+e.getMessage());
				response="failure";
				return response;
			}
			finally {
				
			}
			
		}
// --------------------------- Save Employee 
	@Transactional(value="transactionManager")
	public String savePerson(Employee emp) {
		String response="";
		logger.info("Dao Employee save Called.");
		//logger.info("Employee Details P ----------->"+empDetails);
		UserLogin userlogin;
		try {
			// Save in userlogin table 
			userlogin = new UserLogin(); 
			userlogin.setUsername(emp.getEmailID());
			userlogin.setPassword("test");
			userlogin.setStatus("Approved");
			if(emp.getEmpRole().equalsIgnoreCase("Clinic Manager")){
				userlogin.setUserRole("clinicmanageremployee");	
			}else if(emp.getEmpRole().equalsIgnoreCase("Investment Manager")){
				userlogin.setUserRole("invmanageremployee");	
			}else if(emp.getEmpRole().equalsIgnoreCase("Clinic Non-Manager")){
				userlogin.setUserRole("clinicnon-manageremployee");	
			}else if(emp.getEmpRole().equalsIgnoreCase("Investment Non-Manager")){
				userlogin.setUserRole("invnon-manageremployee");	
			}
						
			// Save in AgentDetails table 
			EmployeeDetails child=new EmployeeDetails();
			child.setEmployeeCode(emp.getEmployeeCode());
			child.setEmployeeName(emp.getName());
			child.setEmployeeEmailID(emp.getEmailID());
			child.setEmployeeAddress(emp.getAddress());
			child.setEmployeePhoneNumber(emp.getPhoneNumber());
			child.setEmpSalary(emp.getSalary()); 
			child.setEmployeeType(emp.getEmpType());
			child.setEmpRole(emp.getEmpRole());	
			child.setCountry(emp.getSelectedCountry()); 
			child.setAcctCreated_date(Custom.getCurrentDate());
			//String referenceCode = emp.getRefEmploy();
			String refCode = emp.getRefEmploy().substring(emp.getRefEmploy().lastIndexOf("-")+1);
			logger.info("Reference Employee Code --------->"+refCode); 
			child.setRefCode(refCode);
			child.setJoin_date(emp.getJoin_date()); 
			child.setUserLogin(userlogin);
			List<EmployeeDetails> childlist = new ArrayList<EmployeeDetails>();
			childlist.add(child);
			userlogin.setEmployeeDetails(childlist);
		    entityManager.persist(userlogin);
			logger.info("Employee data is Stored successfully....");
			response="success";
			return response;
		}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
			response="failure";
			return response;
		}
		finally {
			
		}
		
	}
		
// ----------------------------------- Agent Name List 
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public ArrayList<Agent> getAgentName(ArrayList<Agent> agentList){
		Query query;
		String Agent_NAME_QUERY = "SELECT ag.agent_Details_ID, ag.agent_Name,ag.agent_Code FROM agent_details ag where ag.agent_Type=?";
	   // List<Agent> agentdbList=null;
	    Agent agent;
		try {
			//agentList = new   ArrayList<Agent>();
			query= entityManager.createNativeQuery(Agent_NAME_QUERY);
			query.setParameter(1, "Clinic Agent");
			//query.setParameter(1, "clinicagent");
			List<Object[]> list = query.getResultList();
			if(list.size() >0 ) {
				for (Object[] a : list) {
				    logger.info("ID ----  "
				            + a[0]
				            + " Name --- >"
				            + a[1]);
					agent = new Agent();
					agent.setPrimaryKey((Integer)a[0]);
					agent.setName((String) a[1]);
					agent.setAgentCode((String) a[2]);
					agentList.add(agent);

				}
				agent = new Agent();
				agent.setPrimaryKey(1);
				agent.setName("Others");
				agent.setAgentCode("000");
				agentList.add(agent);

			}else {
				agent = new Agent();
				agent.setPrimaryKey(0);
				agent.setName("No Agent");
				agentList.add(agent);
			}
			
		}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
			agent = new Agent();
			agent.setName("No Employee");
		}finally {
			
		}
			return agentList;
		}
// -------------------------- Employee Name list
	//transactionManager
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public ArrayList<Employee> getEmpName(ArrayList<Employee> empList, String empRole){
			Query query;
			//String EMP_NAME_QUERY = "SELECT emp1.employee_Details_ID, emp1.employee_Name,emp1.employee_Code FROM employee_details emp1 where (emp1.employee_Type='permanentemployee' and emp1.employee_Type='temporaryemployee')";
			String EMP_NAME_QUERY = "SELECT emp1.employee_Details_ID, emp1.employee_Name,emp1.employee_Code FROM employee_details emp1 where (emp1.employee_Type='Temporary Employee' or emp1.employee_Type='Permanent Employee') ";
			String REF_EMP_NAME_QUERY = "SELECT emp1.employee_Details_ID, emp1.employee_Name,emp1.employee_Code FROM employee_details emp1 where (emp1.employee_Type='Temporary Employee' or emp1.employee_Type='Permanent Employee') and "
					+ "	(emp1.employee_Role='Clinic Manager' or emp1.employee_Role='Investment Manager') ";
			String NON_EMP_NAME_QUERY = "SELECT emp1.employee_Details_ID, emp1.employee_Name,emp1.employee_Code FROM employee_details emp1 where (emp1.employee_Type='Temporary Employee' or emp1.employee_Type='Permanent Employee') and "
					+ "	(emp1.employee_Role='Clinic Non-Manager' or emp1.employee_Role='Investment Non-Manager') ";
		    Employee employee;
		    	if(empRole.equalsIgnoreCase("ALL")){
		    		try {
		    			query= entityManager.createNativeQuery(EMP_NAME_QUERY);
						List<Object[]> list = query.getResultList();
						logger.info(" List size -------->"+list.size());
						logger.info("Employee Role ======>"+empRole);		
						if(list.size() > 0) {
							for (Object[] a : list) {
							    logger.info("ID ----  " 
							            + a[0]
							            + " Name --- >"
							            + a[1]);
							    employee = new Employee();
							    employee.setPrimaryKey((Integer)a[0]);
							    employee.setName((String) a[1]);
							    employee.setEmployeeCode((String) a[2]);
			
							    empList.add(employee);
			
							}
							 employee = new Employee();
						     employee.setPrimaryKey(1);
						     employee.setName("Others");
							 employee.setEmployeeCode("000");
			
						     empList.add(employee);
						}
						else {
						    employee = new Employee();
						    employee.setPrimaryKey(0);
						    employee.setName("No Employee");
						    empList.add(employee);
						}
					}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
						employee = new Employee();
						employee.setName("No Employee");
					}finally {
						
					}
						
		    	}else if(empRole.equalsIgnoreCase("Manager")){
		    		try {
						query= entityManager.createNativeQuery(REF_EMP_NAME_QUERY);
						List<Object[]> list = query.getResultList();
						logger.info(" List size -------->"+list.size());
						logger.info("Employee Role ======>"+empRole);	
						if(list.size() > 0) {
							for (Object[] a : list) {
							    logger.info("ID ----  " 
							            + a[0]
							            + " Name --- >"
							            + a[1]);
							    employee = new Employee();
							    employee.setPrimaryKey((Integer)a[0]);
							    employee.setName((String) a[1]);
							    employee.setEmployeeCode((String) a[2]);
			
							    empList.add(employee);
			
							}
						}
						else {
							logger.info("No Managers are there..");
						}
						
			    	}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
					}finally {
						
					}
				}else if(empRole.equalsIgnoreCase("Non-Manager")){
		    		try {
						query= entityManager.createNativeQuery(NON_EMP_NAME_QUERY);
						List<Object[]> list = query.getResultList();
						logger.info(" List size -------->"+list.size());
						logger.info("Employee Role ======>"+empRole);	
						if(list.size() > 0) {
							for (Object[] a : list) {
							    logger.info("ID ----  " 
							            + a[0]
							            + " Name --- >"
							            + a[1]);
							    employee = new Employee();
							    employee.setPrimaryKey((Integer)a[0]);
							    employee.setName((String) a[1]);
							    employee.setEmployeeCode((String) a[2]);
			
							    empList.add(employee);
			
							}
						}
						else {
							employee = new Employee();
							employee.setName("No Non-Manager");
							logger.info("No Non-Managers are there..");
						}
						
			    	}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
					}finally {
						
					}
				}
				
				return empList;
			}

	/*@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public ArrayList<Employee> getEmpName(ArrayList<Employee> empList, String empRole){
		Query query;
		//String EMP_NAME_QUERY = "SELECT emp1.employee_Details_ID, emp1.employee_Name,emp1.employee_Code FROM employee_details emp1 where (emp1.employee_Type='permanentemployee' and emp1.employee_Type='temporaryemployee')";
		String EMP_NAME_QUERY = "SELECT emp1.employee_Details_ID, emp1.employee_Name,emp1.employee_Code FROM employee_details emp1 where (emp1.employee_Type='Temporary Employee' or emp1.employee_Type='Permanent Employee') ";
		String REF_EMP_NAME_QUERY = "SELECT emp1.employee_Details_ID, emp1.employee_Name,emp1.employee_Code FROM employee_details emp1 where (emp1.employee_Type='Temporary Employee' or emp1.employee_Type='Permanent Employee') and emp1.employee_Role='Manager' ";
	    Employee employee;
	    	if(empRole.equalsIgnoreCase("ALL")){
	    		try {
	    			query= entityManager.createNativeQuery(EMP_NAME_QUERY);
					List<Object[]> list = query.getResultList();
					logger.info(" List size -------->"+list.size());
					logger.info("Employee Role ======>"+empRole);		
					if(list.size() > 0) {
						for (Object[] a : list) {
						    logger.info("ID ----  " 
						            + a[0]
						            + " Name --- >"
						            + a[1]);
						    employee = new Employee();
						    employee.setPrimaryKey((Integer)a[0]);
						    employee.setName((String) a[1]);
						    employee.setEmployeeCode((String) a[2]);
		
						    empList.add(employee);
		
						}
						 employee = new Employee();
					     employee.setPrimaryKey(1);
					     employee.setName("Others");
						 employee.setEmployeeCode("000");
		
					     empList.add(employee);
					}
					else {
					    employee = new Employee();
					    employee.setPrimaryKey(0);
					    employee.setName("No Employee");
					    empList.add(employee);
					}
				}catch(Exception e) {
					logger.info("Exception -->"+e.getMessage());
					employee = new Employee();
					employee.setName("No Employee");
				}finally {
					
				}
					
	    	}else if(empRole.equalsIgnoreCase("Manager")){
	    		try {
					query= entityManager.createNativeQuery(REF_EMP_NAME_QUERY);
					List<Object[]> list = query.getResultList();
					logger.info(" List size -------->"+list.size());
					logger.info("Employee Role ======>"+empRole);	
					if(list.size() > 0) {
						for (Object[] a : list) {
						    logger.info("ID ----  " 
						            + a[0]
						            + " Name --- >"
						            + a[1]);
						    employee = new Employee();
						    employee.setPrimaryKey((Integer)a[0]);
						    employee.setName((String) a[1]);
						    employee.setEmployeeCode((String) a[2]);
		
						    empList.add(employee);
		
						}
					}
					else {
						logger.info("No Managers are there..");
					}
					
		    	}catch(Exception e) {
					logger.info("Exception -->"+e.getMessage());
				}finally {
					
				}
			}
			
			return empList;
		}*/
	// Update Agent data
	@Transactional(value="transactionManager")
	public String setAgentUpdate(Agent agent) {
		String response;
		try {

			AgentDetails child = entityManager.find(AgentDetails.class,agent.getPrimaryKey());
			UserLogin userlogin = child.getUserLogin();
			userlogin.setUserRole("agent");				
			child.setAgentName(agent.getName());
			child.setAgentPhoneNumber(agent.getPhoneNumber());
			child.setAgentEmailID(agent.getEmailID());
			child.setAgentAddress(agent.getAddress());
			child.setAgentBankName(agent.getBankName());
			child.setAgentBankBranchName(agent.getBankBranchName());
			child.setAgentAccountNumber(agent.getAccountNumber());
			child.setAgentType(agent.getAgentType()); 
			logger.info("[DAO] Country ------->"+agent.getSelectedCountry()); 
			child.setCountry(agent.getSelectedCountry()); 
			logger.info("Ref Employ Code ------->"+agent.getRefEmploy()); 
			String refEmpCode = agent.getRefEmploy().substring(agent.getRefEmploy().lastIndexOf("-")+1);
			child.setRefEmpCode(refEmpCode);
			child.setUserLogin(userlogin);
			List<AgentDetails> childlist = new ArrayList<AgentDetails>();
			childlist.add(child);
			userlogin.setAgentDetails(childlist);
		    entityManager.merge(userlogin);
			logger.info("Agent data is Updated successfully....");
			
			response="success";
			return response;
		}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
			response="failure";
			return response;
		}finally {
			
		}
	}
	
	
	// Update Employee data
		@Transactional(value="transactionManager")
		public String setEmployeeUpdate(Employee emp) {
			String response;
			try {

				EmployeeDetails child = entityManager.find(EmployeeDetails.class,emp.getPrimaryKey());
				UserLogin userlogin = child.getUserLogin();
				if(emp.getEmpRole().equalsIgnoreCase("Clinic Manager")){
					userlogin.setUserRole("clinicmanageremployee");	
				}else if(emp.getEmpRole().equalsIgnoreCase("Investment Manager")){
					userlogin.setUserRole("invmanageremployee");	
				}else if(emp.getEmpRole().equalsIgnoreCase("Clinic Non-Manager")){
					userlogin.setUserRole("clinicnon-manageremployee");	
				}else if(emp.getEmpRole().equalsIgnoreCase("Investment Non-Manager")){
					userlogin.setUserRole("invnon-manageremployee");	
				}
				//userlogin.setUserRole("employee");
				child.setEmployeeCode(emp.getEmployeeCode());
				logger.info("Employee Code -->"+emp.getEmployeeCode());
				child.setEmployeeName(emp.getName());
				logger.info("Employee Name -->"+emp.getName());
				logger.info("Employee Phone Number -->"+emp.getPhoneNumber());
				logger.info("Employee Address -->"+emp.getAddress());
				logger.info("Employee Salary -->"+emp.getSalary());
				logger.info("Employee EmailID -->"+emp.getEmailID());
				logger.info("Employee Type -->"+emp.getEmpType());
				child.setEmployeeEmailID(emp.getEmailID());
				child.setEmployeeAddress(emp.getAddress());
				child.setEmployeePhoneNumber(emp.getPhoneNumber());
				child.setEmployeeType(emp.getEmpType());
				child.setEmpSalary(emp.getSalary()); 
				child.setEmpRole(emp.getEmpRole()); 
				child.setJoin_date(emp.getJoin_date()); 
				if(emp.getEmpRole().equalsIgnoreCase("Clinic Manager")){
					child.setRefCode("");
				}else if(emp.getEmpRole().equalsIgnoreCase("Investment Manager")){
					child.setRefCode("");
				}else if(emp.getEmpRole().equalsIgnoreCase("Clinic Non-Manager")){
					String refCode = emp.getRefEmploy().substring(emp.getRefEmploy().lastIndexOf("-")+1);
					child.setRefCode(refCode);
				}else if(emp.getEmpRole().equalsIgnoreCase("Investment Non-Manager")){
					String refCode = emp.getRefEmploy().substring(emp.getRefEmploy().lastIndexOf("-")+1);
					child.setRefCode(refCode);
				}
				child.setAcctCreated_date(Custom.getCurrentDate());
				child.setCountry(emp.getSelectedCountry());
				child.setUserLogin(userlogin);
				List<EmployeeDetails> childlist = new ArrayList<EmployeeDetails>();
				childlist.add(child);
				userlogin.setEmployeeDetails(childlist);
			    entityManager.merge(userlogin);
				logger.info("Employee data is Updated successfully....");
				
				response="success";
				return response;
			}catch(Exception e) {
							logger.info("Exception -->"+e.getMessage());
				response="failure";
				return response;
			}finally {
				
			}
		}
		
	
		// Remove Agent data
		@Transactional(value="transactionManager")
		public String setAgentRemove(int agPK) {
			String response = "failure";
			try {
				AgentDetails child = entityManager.find(AgentDetails.class,agPK);
				UserLogin userlogin = child.getUserLogin();
				child.setUserLogin(userlogin);
			    entityManager.remove(child);
			    logger.info("Agent is removed Successfully....");
			    response = "success";
			
			}catch(Exception e) {

							logger.info("Exception -->"+e.getMessage());
				response="failure";
				return response;
			}finally {
				
			}
			return response;
		}
		
		// Load Employee data
		@Transactional(value="transactionManager")
		@SuppressWarnings("unchecked")
		public ArrayList<Employee> getEmployeeInfo(ArrayList<Employee> employeeList){
		Query query;
		Employee emp;	
		try {
			query = entityManager.createQuery("from EmployeeDetails");
		   List<EmployeeDetails> empList = (ArrayList<EmployeeDetails>)query.getResultList();
		   logger.info("emp List --->"+empList.size());
		   if(empList.size() > 0) {
			   int i=1;
			   for(EmployeeDetails empdet : empList) {
				   emp = new Employee();
				   emp.setsNo(i);
				   emp.setPrimaryKey(empdet.getEmployee_Details_ID());
				   emp.setEmployeeCode(empdet.getEmployeeCode()); 
				   emp.setName(empdet.getEmployeeName());
				   emp.setPhoneNumber(empdet.getEmployeePhoneNumber());
				   emp.setEmailID(empdet.getEmployeeEmailID());
				   emp.setEmpType(empdet.getEmployeeType());
				   emp.setSalary(empdet.getEmpSalary()); 
				   emp.setAddress(empdet.getEmployeeAddress()); 
				   emp.setEmpRole(empdet.getEmpRole()); 
				   emp.setRefEmploy(empdet.getRefCode()); 
				   emp.setSelectedCountry(empdet.getCountry());
				   emp.setJoin_date(empdet.getJoin_date()); 
				   employeeList.add(emp);
				   i++;
			   }
			   
		   }else {
			   
		   }	
	 
			 return employeeList;
		 }catch(Exception e) {
			 			logger.info("Exception -->"+e.getMessage());
		 }finally {
			 
		 }
		 return employeeList; 
		}
		
	// Remove Employee data
	@Transactional(value="transactionManager")
	public String setEmployeeRemove(int empPK) {
		String response = "failure";
		EmployeeDetails empdetails;
		try {
			logger.info("Employee Primary Key ----->"+empPK);
			empdetails = new EmployeeDetails();
			empdetails = entityManager.find(EmployeeDetails.class, empPK);
			logger.info("Employee Name -------------->"+empdetails.getEmployeeName());
			entityManager.remove(empdetails);
			logger.info("Employee Data is removed successfully..");
		    response = "success";
		
		}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
			response="failure";
			return response;
		}finally {
			
		}
		return response;
			}
	// Save Clinic data
	@Transactional(value="transactionManager")
	public String saveClinic(Clinic clinic) {
		 logger.info("-------------------------- Inside saveClinic DAO method ------------");
		String response;	
		try {
			AgentDetails agentdetails = entityManager.find(AgentDetails.class, clinic.getAgentPK());
			EmployeeDetails employeedetails = entityManager.find(EmployeeDetails.class, clinic.getEmployeePK());
			ClinicDetails child = new ClinicDetails();
			child.setClinicName(clinic.getClinicName());
			child.setClinicAddress(clinic.getAddress());
			child.setClinicPhoneNumber(clinic.getPhoneNumber());
			child.setClinicCode(clinic.getClinicCode());
			child.setAcctCreated_date(Custom.getCurrentDate());
			child.setClinicStatus("available");
			child.setAgent_details(agentdetails);
			child.setEmployee_details(employeedetails);		
		    entityManager.persist(child);//(userlogin);	    
			response="success";
			logger.info("--------------------------Saved Clinic data successfully------------");
			return response;
		}catch(Exception e) {
			response ="failure";
			logger.info("Exception -->"+e.getMessage());

		}finally {
			
		}
		return response;
		}		
	
	// Clinic Name Exist Check
	@Transactional(value="transactionManager")
	public String clinicExistCheck(String clinicName) {
		String response;
		Query query;
		try {
			query=entityManager.createQuery("from ClinicDetails where clinicName=?");
			query.setParameter(1, clinicName); 
			ClinicDetails clinicdetails = (ClinicDetails)query.getSingleResult();
			 if(clinicdetails.getClinicName().isEmpty()) {
				 logger.info("-------------------------- There is No Dublicate Clinic Name found------------");
				 response="not exit";
				}
			else {
				 logger.info("-------------------------- There is Dublicate Clinic Name found------------");

				response="exit";
			}
			return response;
			
		}
		catch(NoResultException ex) {
			 logger.info("-------------------------- There is No Dublicate Clinic Name found------------");
			response="not exit";
		return response;	
			}
		
		catch(Exception e) {
			response="exit";
			logger.info("Exception -->"+e.getMessage());
			return response;
		}finally {
			
		}
	}
	
	// Agent Code Exist Check
	@Transactional(value="transactionManager")
	public String agentCodeExistCheck(String agentCode) {
		String response;
		Query query;
		try {
			query=entityManager.createQuery("from AgentDetails where agentCode=? and agentType=?");
			query.setParameter(1, agentCode); 
			query.setParameter(2, "Investment Agent"); 				
			AgentDetails agentdetails = (AgentDetails)query.getSingleResult();
			 if(agentdetails.getAgentCode().isEmpty()) {
				 logger.info("-------------------------- There is No  Agent Code found------------");
				 response="not exit";
				}
			else {
				 logger.info("-------------------------- There is  Agent Code found------------");

				response="exit";
			}
			return response;
			
		}
		catch(NoResultException ex) {
			 logger.info("-------------------------- There is No Agent Code found------------");
			response="not exit";
		return response;	
			}
		
		catch(Exception e) {
			response="exit";
			logger.info("Exception -->"+e.getMessage());
			return response;
		}finally {
			
		}
	}
		
	// ---------- Clinic Name List --------------
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public ArrayList<Clinic> getClinicName(ArrayList<Clinic> clinicList){
		Query query;
	    Clinic clinic;
		try {
			   logger.info("[DAO Clinic]Before Clinic Name List ------------->");
			   query=entityManager.createQuery("from ClinicDetails where clinicStatus=?");
			   query.setParameter(1, "available");
			   List<ClinicDetails> cliList = (ArrayList<ClinicDetails>)query.getResultList();
			   logger.info("clinic List --->"+cliList.size());
			   if(cliList.size() > 0) {
				   for(ClinicDetails clinicdet : cliList) {
					   clinic = new Clinic();
					   logger.info("Clinic Name --->"+clinicdet.getClinicName());
					   clinic.setClinicName(clinicdet.getClinicName());
					   clinic.setClinicCode(clinicdet.getClinicCode());
					   clinic.setPrimaryKey(clinicdet.getClinic_Details_ID());
					   clinicList.add(clinic);
				   }
				   
			   }else {
				   clinic = new Clinic();
				   clinic.setClinicName("No Clinic");
				   clinicList.add(clinic);
				   logger.info("----------- Empty Clinic List -----------");
			   }	
	 
			 return clinicList;
		}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
		}finally {
			
		}
			return clinicList;
		}
				
	// Update Clinic data
	@Transactional(value="transactionManager")
	public String setClinicUpdate(Clinic clinic) {
		String response;
		try {
			AgentDetails agentdetails = entityManager.find(AgentDetails.class, clinic.getAgentPK());
			EmployeeDetails employeedetails = entityManager.find(EmployeeDetails.class, clinic.getEmployeePK());
			logger.info("AgentpK ---------->"+clinic.getAgentPK());
			logger.info("EmployeePK ---------->"+clinic.getEmployeePK());
			logger.info("Clinic  PK ---------->"+clinic.getPrimaryKey());
			ClinicDetails child = new ClinicDetails();
			child.setClinicName(clinic.getClinicName());
			child.setClinicAddress(clinic.getAddress());
			child.setClinicPhoneNumber(clinic.getPhoneNumber());
			child.setClinicCode(clinic.getClinicCode());
			child.setClinicStatus("available");//(clinic.getClinicCode());
			child.setAcctCreated_date(Custom.getCurrentDate());
			child.setAgent_details(agentdetails);
			child.setEmployee_details(employeedetails);	
			child.setClinic_Details_ID(clinic.getPrimaryKey());
		    entityManager.merge(child);	    
			response="success";
			logger.info("----- After Clinic Update Calling -----");
			return response;
			
		}catch(Exception e) {
						logger.info("Exception -->"+e.getMessage());
			response="failure";
			return response;
		}finally {
			
		}
	}
				
			
	// Remove Clinic data
	@Transactional(value="transactionManager")
	public String setClinicRemove(int clinicPK) {
		String response = "failure";
		try {
			logger.info("Clinic PK ----->"+clinicPK);
			ClinicDetails clinic = entityManager.find(ClinicDetails.class,clinicPK);
		    entityManager.remove(clinic);
		    logger.info("Clinic is removed Successfully....");
		    response = "success";
		
		}catch(Exception e) {

						logger.info("Exception -->"+e.getMessage());
			response="failure";
			return response;
		}finally {
			
		}
		return response;
	}
	
	// ------------------ view Agent profile ---------------------
			@Transactional(value="transactionManager")
			public Agent getAgentProfile(Agent agent){
				try {
					UserLogin userlogin = entityManager.find(UserLogin.class, Integer.valueOf(agent.getUserLoginPrimaryKey()));
					agent.setUsername(userlogin.getUsername());						
					logger.info("[DAO AgentProfile]userLoginPrimaryKey --------->"+agent.getUserLoginPrimaryKey());
				 	logger.info("[DAO AgentProfile]Agent Code --------->"+userlogin.getAgentDetails().get(0).getAgentCode());
				 	logger.info("[DAO AgentProfile]Agent Name --------->"+userlogin.getAgentDetails().get(0).getAgentName());
				 	logger.info("[DAO AgentProfile]Agent PhoneNumber --------->"+userlogin.getAgentDetails().get(0).getAgentPhoneNumber());
				 	logger.info("[DAO AgentProfile]Agent EmailID --------->"+userlogin.getAgentDetails().get(0).getAgentEmailID());
				 	logger.info("[DAO AgentProfile]Agent Type --------->"+userlogin.getAgentDetails().get(0).getAgentType());
				 	agent.setAgentCode(userlogin.getAgentDetails().get(0).getAgentCode()); 
				 	agent.setName(userlogin.getAgentDetails().get(0).getAgentName());
				 	agent.setPhoneNumber(userlogin.getAgentDetails().get(0).getAgentPhoneNumber()); 
				 	agent.setEmailID(userlogin.getAgentDetails().get(0).getAgentEmailID());
				 	agent.setAddress(userlogin.getAgentDetails().get(0).getAgentAddress());
				 	agent.setBankName(userlogin.getAgentDetails().get(0).getAgentBankName());
				 	agent.setAccountNumber(userlogin.getAgentDetails().get(0).getAgentAccountNumber());
				 	agent.setBankBranchName(userlogin.getAgentDetails().get(0).getAgentBankBranchName()); 
				 	agent.setAgentType(userlogin.getAgentDetails().get(0).getAgentType()); 
				 	agent.setPrimaryKey(userlogin.getAgentDetails().get(0).getAgent_Details_ID()); 
				 	logger.info("Primary Key ------------------>"+agent.getPrimaryKey());
				}catch(Exception e){
					logger.info("AgentProfile Exception ---->"+e.getMessage());
				} finally {
					
				}
				return agent;
			}
			
					
			// ---------------------- load Only MY GLG Member Data -------------------------
			@Transactional(value="transactionManager")
			@SuppressWarnings (value="unchecked")
			public ArrayList<Clinic> getClinicAgentReport(String agentCode, ArrayList<Clinic> clinicReportList){
				Query query=null;
				Clinic clinic;
				ArrayList<AgentDetails> resultList;
				List<ClinicDetails> clinicdetails;
				logger.info("[DAO]Agent Code ---------------->"+agentCode);
				try {						
					query=entityManager.createQuery("from AgentDetails where agentCode=?");
					query.setParameter(1, agentCode);
					resultList=(ArrayList<AgentDetails>)query.getResultList();
					logger.info("Agent Details List size ------>"+resultList.size());
					if(resultList.size()>0){
						query = entityManager.createQuery("from ClinicDetails where agent_Details_ID=?");
						query.setParameter(1, resultList.get(0).getAgent_Details_ID());
						clinicdetails = (List<ClinicDetails>) query.getResultList();
						logger.info("Clinic Agent Details List size ------>"+clinicdetails.size());
						if (clinicdetails.size() > 0) {
							int i=1;
							for(ClinicDetails clinicdet :clinicdetails){
								clinic = new Clinic();
								logger.info("Agent Code ---------->"+agentCode);
								clinic.setsNo(i);
								clinic.setAgentCode(agentCode);
								clinic.setName(clinicdet.getAgent_details().getAgentName());
								clinic.setPhoneNumber(clinicdet.getAgent_details().getAgentPhoneNumber()); 
								clinic.setClinicName(clinicdet.getClinicName()); 
								clinic.setClinicCode(clinicdet.getClinicCode());
								clinic.setClinicPhoneNumber(clinicdet.getClinicPhoneNumber());
								clinic.setCreatedDate(clinicdet.getAcctCreated_date());
								clinic.setAddress(clinicdet.getClinicAddress());
								clinic.setEmpName(clinicdet.getEmployee_details().getEmployeeName());
								clinic.setMemberName(clinicdet.getUserdetails().getFirstname() + clinicdet.getUserdetails().getLastname());
								clinicReportList.add(clinic);
								i++;
							}
						}
					
					}
					logger.info("clinicReport List Size -------->"+clinicReportList.size()); 
				}catch(Exception e) {
					logger.info("Exception ----------->"+e.getMessage());
				}finally{
					
				}		
				return clinicReportList;
			
			}

				@Transactional(value="transactionManager")
				@SuppressWarnings (value="unchecked")
				public ArrayList<Member> getInvAgentReport(String agentCode, ArrayList<Member> invreportlist) {
					Query query=null;
					Member member;
					ArrayList<UserDetail> resultList;
					logger.info("[DAO]Agent Code ---------------->"+agentCode);
					try {						
						query=entityManager.createQuery("from UserDetail where agent_code=?");
						query.setParameter(1, agentCode);
						resultList=(ArrayList<UserDetail>)query.getResultList();
						int i=1;
						for(UserDetail userdet : resultList) {
							member = new Member();
							logger.info("Member Number ---------->"+userdet.getMemberID());					
							logger.info("Member Name ---------->"+userdet.getFirstname()	+" "+ userdet.getLastname());
							member.setsNo(i);
							member.setAgentCode(agentCode);
							member.setMemberNumber(userdet.getMemberID()); 
							member.setFirstName(userdet.getFirstname() +" "+ userdet.getLastname()); 
							member.setPhoneNumber(userdet.getPhonenumber1());	
							member.setNoofclinics(userdet.getNoofclinics());
							member.setEnrollmentDate(userdet.getAcct_Approved_date());
							member.setTotalAmount(userdet.getTotalFees());
							member.setStatus(userdet.getUserLogin().getStatus()); 
							member.setEmailID(userdet.getEmail1());
							member.setPaymentpath(userdet.getPayStatus());
							invreportlist.add(member);
							i++;
						}
						logger.info("invreport List Size -------->"+invreportlist.size()); 
					}catch(Exception e) {
						logger.info("Exception ----------->"+e.getMessage());
					}finally{
						
					}		
					return invreportlist;
				
				}
				
				// ---------------------- load All ClinicReport Data Based On Date -------------------------
				@Transactional(value="transactionManager")
				public ArrayList<Clinic> getAllClinicReportList(Clinic clinic, ArrayList<Clinic> clinicReportList){
					Date tmpFromdate;
					Date tmpTodate;
					try {	
						tmpFromdate=clinic.getFromDate();
						tmpTodate=clinic.getToDate();

						AgentDetails agentdetails = entityManager.find(AgentDetails.class, clinic.getAgentPK());
						logger.info("Agent Code ----->"+agentdetails.getAgentCode());
						logger.info("Agent Name ----->"+agentdetails.getAgentName());
						logger.info("Agent Phone ----->"+agentdetails.getAgentPhoneNumber());

						if(agentdetails.getClinicdetails().size()>0){
							logger.info("List Size ------------->"+agentdetails.getClinicdetails().size());
							int j=1;
							for(int i=0;i<agentdetails.getClinicdetails().size();i++){
								clinic = new Clinic();	

								logger.info("From Date ui ------------->"+tmpFromdate);
								logger.info("database Date ------------->"+agentdetails.getClinicdetails().get(i).getAcctCreated_date());
								logger.info("To Date ui ------------->"+tmpTodate);
								logger.info("------------ Test 11  ------------->");
								if (agentdetails.getClinicdetails().get(i).getAcctCreated_date().after(tmpFromdate) 
										&& agentdetails.getClinicdetails().get(i).getAcctCreated_date().before(tmpTodate) ) {
									logger.info("Clinic Code ----->"+agentdetails.getClinicdetails().get(i).getClinicCode());
									logger.info("Clinic Name ----->"+agentdetails.getClinicdetails().get(i).getClinicName());
									logger.info("Clinic Phone ----->"+agentdetails.getClinicdetails().get(i).getClinicPhoneNumber());
									clinic.setsNo(j);
									clinic.setAgentCode(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentCode());
									clinic.setName(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentName());
									clinic.setPhoneNumber(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentPhoneNumber()); 
									clinic.setEmailID(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentEmailID());
									clinic.setAddress(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentAddress());
									clinic.setBankName(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentBankName());
									clinic.setRefEmploy(agentdetails.getClinicdetails().get(i).getAgent_details().getRefEmpCode());
									clinic.setClinicName(agentdetails.getClinicdetails().get(i).getClinicName()); 
									clinic.setClinicCode(agentdetails.getClinicdetails().get(i).getClinicCode());
									clinic.setClinicPhoneNumber(agentdetails.getClinicdetails().get(i).getClinicPhoneNumber());
									clinic.setCreatedDate(agentdetails.getClinicdetails().get(i).getAcctCreated_date());
									clinicReportList.add(clinic);
									j++;
					            } else if(agentdetails.getClinicdetails().get(i).getAcctCreated_date().equals(tmpFromdate) ||
										agentdetails.getClinicdetails().get(i).getAcctCreated_date().equals(tmpTodate)){
					            	logger.info("Clinic Code ----->"+agentdetails.getClinicdetails().get(i).getClinicCode());
									logger.info("Clinic Name ----->"+agentdetails.getClinicdetails().get(i).getClinicName());
									logger.info("Clinic Phone ----->"+agentdetails.getClinicdetails().get(i).getClinicPhoneNumber());
									clinic.setsNo(j);
									clinic.setAgentCode(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentCode());
									clinic.setName(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentName());
									clinic.setPhoneNumber(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentPhoneNumber());
									clinic.setEmailID(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentEmailID());
									clinic.setAddress(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentAddress());
									clinic.setBankName(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentBankName());
									clinic.setRefEmploy(agentdetails.getClinicdetails().get(i).getAgent_details().getRefEmpCode());
									clinic.setClinicName(agentdetails.getClinicdetails().get(i).getClinicName()); 
									clinic.setClinicCode(agentdetails.getClinicdetails().get(i).getClinicCode());
									clinic.setClinicPhoneNumber(agentdetails.getClinicdetails().get(i).getClinicPhoneNumber());
									clinic.setCreatedDate(agentdetails.getClinicdetails().get(i).getAcctCreated_date());
									clinicReportList.add(clinic);
									j++;
					            }
								logger.info("------------ Test 22  ------------->");
							}
						}					
						logger.info("clinicReport List Size -------->"+clinicReportList.size()); 
					}catch(Exception e) {
						logger.info("Exception ----------->"+e.getMessage());
					}finally{
						
					}		
					return clinicReportList;
				
				}
				
				// Invest Agent Name List 
				@Transactional(value="transactionManager")
				@SuppressWarnings("unchecked")
				public ArrayList<Agent> getinvAgentName(ArrayList<Agent> invagentList){
					Query query;
					String Agent_NAME_QUERY = "SELECT ag.agent_Details_ID, ag.agent_Name,ag.agent_Code FROM agent_details ag where ag.agent_Type=?";
				    Agent agent;
					try {
						query= entityManager.createNativeQuery(Agent_NAME_QUERY);
						query.setParameter(1, "Investment Agent");
						//query.setParameter(1, "investmentAgent");
						List<Object[]> list = query.getResultList();
						if(list.size() >0 ) {
							for (Object[] a : list) {
							    logger.info("ID ----  "
							            + a[0]
							            + " Name --- >"
							            + a[1]);
								agent = new Agent();
								agent.setPrimaryKey((Integer)a[0]);
								agent.setName((String) a[1]);
								agent.setAgentCode((String) a[2]);
								invagentList.add(agent);

							}
							agent = new Agent();
							agent.setPrimaryKey(1);
							agent.setName("Others");
							agent.setAgentCode("000");
							invagentList.add(agent);

						}else {
							agent = new Agent();
							agent.setPrimaryKey(0);
							agent.setName("No Agent");
							invagentList.add(agent);
						}
						
					}catch(Exception e) {
									logger.info("Exception -->"+e.getMessage());
					}finally {
						
					}
						return invagentList;
					}
				
				// ---------------------- load All investReport Data Based On Date -------------------------
				@Transactional(value="transactionManager")
				@SuppressWarnings (value="unchecked")
				public ArrayList<Member> getAllInvAgentReport(Member member, ArrayList<Member> invReportList){
					Query query=null;
					List<UserDetail> resultList;
					try {			
						StringTokenizer st = new StringTokenizer(member.getAgentCode());
						String agentName = st.nextToken("-");
						String agentcode1 = member.getAgentCode();
						String agentCode = agentcode1.substring(agentcode1.lastIndexOf("-") + 1);
						logger.info("agent Name ------>"+agentName);
						logger.info("agent Code ------>"+agentCode);
						
						query=entityManager.createQuery("from UserDetail where agent_code=? and acct_Approved_date between ? and ?");
						query.setParameter(1, agentCode);
						query.setParameter(2, member.getBookingdate());
						query.setParameter(3, member.getInvestmentDate());
						resultList=(ArrayList<UserDetail>)query.getResultList();
						logger.info("Result List Size -------->"+invReportList.size()); 
						int i=1;
						for(UserDetail userdetails : resultList) {
							member = new Member();
							logger.info("Member Number ---------->"+userdetails.getMemberID());					
							logger.info("Member Name ---------->"+userdetails.getFirstname()	+" "+ userdetails.getLastname());
							member.setsNo(i);
							member.setAgentCode(agentCode);
							member.setCname(agentName);
							member.setMemberNumber(userdetails.getMemberID()); 
							member.setFirstName(userdetails.getFirstname() +" "+ userdetails.getLastname());
							member.setPhoneNumber(userdetails.getPhonenumber1());
							member.setEmailID(userdetails.getEmail1()); 
							member.setNoofclinics(userdetails.getNoofclinics());
							member.setTotalAmount(userdetails.getTotalFees());
							member.setBookingdate(userdetails.getAcct_Approved_date()); 
							member.setPaymentpath(userdetails.getPayStatus());
							invReportList.add(member);
							i++;
						}
										
					}catch(Exception e) {
						logger.info("Exception ----------->"+e.getMessage());
					}finally{
						
					}		
					return invReportList;
				
				}
								
				// ---------------------- load All Member ClinicReport Data Based On Date -------------------------
				@Transactional(value="transactionManager")
				@SuppressWarnings (value="unchecked")
				public ArrayList<Clinic> getAllMemberClinicReportList(Clinic clinic, ArrayList<Clinic> memberclinicReportList){
					Query query=null;
					List<ClinicDetails> resultList;
					try {	
						logger.info("From Date ui ------------->"+clinic.getFromDate());
						logger.info("To Date ui ------------->"+clinic.getToDate());
						
						if(clinic.getName().equalsIgnoreCase("All") && clinic.getEmpName().equalsIgnoreCase("All")){
							
							logger.info("-------- Inside Both Agent and Employee are Equal to All ----------");
							
							query=entityManager.createQuery("from ClinicDetails where acctCreated_date between ? and ?");
							query.setParameter(1, clinic.getFromDate());
							query.setParameter(2, clinic.getToDate());
							resultList=(ArrayList<ClinicDetails>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
							int i=1;
							for(ClinicDetails clinicdetails : resultList) {
									clinic = new Clinic();	
									logger.info("Clinic Code ----->"+clinicdetails.getClinicCode());
									logger.info("Clinic Name ----->"+clinicdetails.getClinicName());
									logger.info("Clinic Phone ----->"+clinicdetails.getClinicPhoneNumber());
									logger.info("Agent Code ----->"+clinicdetails.getAgent_details().getAgentName());
									logger.info("Employee Code ----->"+clinicdetails.getEmployee_details().getEmployeeName());
									clinic.setsNo(i);
									clinic.setClinicCode(clinicdetails.getClinicCode());
									clinic.setClinicName(clinicdetails.getClinicName());
									clinic.setClinicPhoneNumber(clinicdetails.getClinicPhoneNumber());
									clinic.setCreatedDate(clinicdetails.getAcctCreated_date());
									clinic.setAddress(clinicdetails.getClinicAddress());
									clinic.setName(clinicdetails.getAgent_details().getAgentName());
									clinic.setAgentCode(clinicdetails.getAgent_details().getAgentCode());
									clinic.setEmpName(clinicdetails.getEmployee_details().getEmployeeName());	
									clinic.setEmployeeCode(clinicdetails.getEmployee_details().getEmployeeCode());	
									if(clinicdetails.getClinicStatus().equalsIgnoreCase("not available")){
										clinic.setMemberNumber(clinicdetails.getUserdetails().getMemberID());
										clinic.setUsername(clinicdetails.getUserdetails().getFirstname() + " " +clinicdetails.getUserdetails().getLastname());
									}else if(clinicdetails.getClinicStatus().equalsIgnoreCase("available")){
										clinic.setMemberNumber(" ");
										clinic.setUsername(" ");
									}
									
									memberclinicReportList.add(clinic);
									i++;
							  }
							
						}else if(clinic.getName().equalsIgnoreCase("All")){

							logger.info("-------- Inside Both Agent only Equal to All ----------");
							
							query=entityManager.createQuery("from ClinicDetails where employee_Details_ID=? and acctCreated_date between ? and ?");
							query.setParameter(1, clinic.getEmployeePK());
							query.setParameter(2, clinic.getFromDate());
							query.setParameter(3, clinic.getToDate());
							resultList=(ArrayList<ClinicDetails>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
							int i=1;
							for(ClinicDetails clinicdetails : resultList) {
									clinic = new Clinic();	
					            	logger.info("Clinic Code ----->"+clinicdetails.getClinicCode());
									logger.info("Clinic Name ----->"+clinicdetails.getClinicName());
									logger.info("Clinic Phone ----->"+clinicdetails.getClinicPhoneNumber());
									logger.info("Agent Code ----->"+clinicdetails.getAgent_details().getAgentName());
									logger.info("Employee Code ----->"+clinicdetails.getEmployee_details().getEmployeeName());
									clinic.setsNo(i);
									clinic.setClinicCode(clinicdetails.getClinicCode());
									clinic.setClinicName(clinicdetails.getClinicName());
									clinic.setClinicPhoneNumber(clinicdetails.getClinicPhoneNumber());
									clinic.setCreatedDate(clinicdetails.getAcctCreated_date());
									clinic.setAddress(clinicdetails.getClinicAddress());
									clinic.setName(clinicdetails.getAgent_details().getAgentName());
									clinic.setAgentCode(clinicdetails.getAgent_details().getAgentCode());
									clinic.setEmpName(clinicdetails.getEmployee_details().getEmployeeName());	
									clinic.setEmployeeCode(clinicdetails.getEmployee_details().getEmployeeCode());	
									if(clinicdetails.getClinicStatus().equalsIgnoreCase("not available")){
										clinic.setMemberNumber(clinicdetails.getUserdetails().getMemberID());
										clinic.setUsername(clinicdetails.getUserdetails().getFirstname() + " " +clinicdetails.getUserdetails().getLastname());
									}else if(clinicdetails.getClinicStatus().equalsIgnoreCase("available")){
										clinic.setMemberNumber(" ");
										clinic.setUsername(" ");
									}
									
									memberclinicReportList.add(clinic);
									i++;
						      }
						}else if(clinic.getEmpName().equalsIgnoreCase("All")){

							logger.info("-------- Inside Both Employee only Equal to All ----------");
							
							query=entityManager.createQuery("from ClinicDetails where agent_Details_ID=? and acctCreated_date between ? and ?");
							query.setParameter(1, clinic.getAgentPK());
							query.setParameter(2, clinic.getFromDate());
							query.setParameter(3, clinic.getToDate());
							resultList=(ArrayList<ClinicDetails>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
							int i=1;
							for(ClinicDetails clinicdetails : resultList) {
									clinic = new Clinic();	
					            	logger.info("Clinic Code ----->"+clinicdetails.getClinicCode());
									logger.info("Clinic Name ----->"+clinicdetails.getClinicName());
									logger.info("Clinic Phone ----->"+clinicdetails.getClinicPhoneNumber());
									logger.info("Agent Code ----->"+clinicdetails.getAgent_details().getAgentName());
									logger.info("Employee Code ----->"+clinicdetails.getEmployee_details().getEmployeeName());
									clinic.setsNo(i);
									clinic.setClinicCode(clinicdetails.getClinicCode());
									clinic.setClinicName(clinicdetails.getClinicName());
									clinic.setClinicPhoneNumber(clinicdetails.getClinicPhoneNumber());
									clinic.setCreatedDate(clinicdetails.getAcctCreated_date());
									clinic.setAddress(clinicdetails.getClinicAddress());
									clinic.setName(clinicdetails.getAgent_details().getAgentName());
									clinic.setAgentCode(clinicdetails.getAgent_details().getAgentCode());
									clinic.setEmpName(clinicdetails.getEmployee_details().getEmployeeName());	
									clinic.setEmployeeCode(clinicdetails.getEmployee_details().getEmployeeCode());	
									if(clinicdetails.getClinicStatus().equalsIgnoreCase("not available")){
										clinic.setMemberNumber(clinicdetails.getUserdetails().getMemberID());
										clinic.setUsername(clinicdetails.getUserdetails().getFirstname() + " " +clinicdetails.getUserdetails().getLastname());
									}else if(clinicdetails.getClinicStatus().equalsIgnoreCase("available")){
										clinic.setMemberNumber(" ");
										clinic.setUsername(" ");
									}
									
									memberclinicReportList.add(clinic);
									i++;
						      }
							
						}else{

							logger.info("-------- Inside Both Agent and Employee are Equal to Not All ----------");
							
							query=entityManager.createQuery("from ClinicDetails where agent_Details_ID=? and employee_Details_ID=? and acctCreated_date between ? and ?");
							query.setParameter(1, clinic.getAgentPK()); 
							query.setParameter(2, clinic.getEmployeePK());
							query.setParameter(3, clinic.getFromDate());
							query.setParameter(4, clinic.getToDate());
							resultList=(ArrayList<ClinicDetails>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
								int i=1;
								for(ClinicDetails clinicdetails : resultList) {
										clinic = new Clinic();	
						            	logger.info("Clinic Code ----->"+clinicdetails.getClinicCode());
										logger.info("Clinic Name ----->"+clinicdetails.getClinicName());
										logger.info("Clinic Phone ----->"+clinicdetails.getClinicPhoneNumber());
										logger.info("Agent Code ----->"+clinicdetails.getAgent_details().getAgentName());
										logger.info("Employee Code ----->"+clinicdetails.getEmployee_details().getEmployeeName());
										clinic.setsNo(i);
										clinic.setClinicCode(clinicdetails.getClinicCode());
										clinic.setClinicName(clinicdetails.getClinicName());
										clinic.setClinicPhoneNumber(clinicdetails.getClinicPhoneNumber());
										clinic.setCreatedDate(clinicdetails.getAcctCreated_date());
										clinic.setAddress(clinicdetails.getClinicAddress());
										clinic.setName(clinicdetails.getAgent_details().getAgentName());
										clinic.setAgentCode(clinicdetails.getAgent_details().getAgentCode());
										clinic.setEmpName(clinicdetails.getEmployee_details().getEmployeeName());	
										clinic.setEmployeeCode(clinicdetails.getEmployee_details().getEmployeeCode());	
										if(clinicdetails.getClinicStatus().equalsIgnoreCase("not available")){
											clinic.setMemberNumber(clinicdetails.getUserdetails().getMemberID());
											clinic.setUsername(clinicdetails.getUserdetails().getFirstname() + " " +clinicdetails.getUserdetails().getLastname());
										}else if(clinicdetails.getClinicStatus().equalsIgnoreCase("available")){
											clinic.setMemberNumber(" ");
											clinic.setUsername(" ");
										}
										
										memberclinicReportList.add(clinic);
										i++;
							      }
								
						}
						
						logger.info("clinicReport List Size -------->"+memberclinicReportList.size()); 
					}catch(Exception e) {
						logger.info("Exception ----------->"+e.getMessage());
					}finally{
						
					}		
					return memberclinicReportList;
				
				}
		
				// ---------------------- load All Member Report Data Based On Date -------------------------
				@Transactional(value="transactionManager")
				@SuppressWarnings (value="unchecked")
				public ArrayList<Member> getAllMemberReportList(Member member, ArrayList<Member> memberReportList,String status){
					Query query=null;
					List<UserDetail> list;
					List<UserLogin> resultList;
					int i=1;
					int a=0;
					try {	
						logger.info("From Date ui ------------->"+member.getBookingdate());
						logger.info("To Date ui ------------->"+member.getEnrollmentDate());
						if(status.equalsIgnoreCase("All")){						
							query=null;
							query=entityManager.createQuery("from UserDetail where acctCreated_date between ? and ?");
							query.setParameter(1, member.getBookingdate());
							query.setParameter(2, member.getEnrollmentDate()); 
							list=(ArrayList<UserDetail>)query.getResultList();
							logger.info("Result List ---------------->"+list.size()); 
							for(UserDetail userdetails : list) {
									member = new Member();	
					            	logger.info("Member Code ----->"+userdetails.getMemberID());
									member.setsNo(i);
									member.setFirstName(userdetails.getFirstname() + " " + userdetails.getLastname());
									member.setMemberID(userdetails.getMemberID());
									member.setBookingStatus(userdetails.getClinic_status());
									member.setPhoneNumber(userdetails.getPhonenumber1());
									member.setEmailID(userdetails.getEmail1());
									member.setInvestmentDate(userdetails.getAcctCreated_date());
									member.setActType(userdetails.getAcctType());
									member.setTotalAmount(userdetails.getTotalFees());
									member.setStatus(userdetails.getUserLogin().getStatus());
									member.setAgentCode(userdetails.getAgent_code());
									member.setPaymentpath(userdetails.getPayStatus());
									member.setUserLoginPrimaryKey(userdetails.getUserLogin().getUser_Login_ID()); 
									logger.info("ID for userLogin ------>"+member.getUserLoginPrimaryKey());
									member.setWstatus(userdetails.getUserLogin().getStatus()); 
									logger.info("Status for userLogin ------>"+member.getWstatus());
									memberReportList.add(member);
									i++;
								}						
							logger.info("memberReport List Size -------->"+memberReportList.size()); 
							
						}else{
							
							java.util.Date fromDate = member.getBookingdate();
							java.util.Date ToDate = member.getEnrollmentDate();
							query=entityManager.createQuery("from UserLogin where status=? and userRole=?");
							query.setParameter(1, status);  
							query.setParameter(2, "member");
							resultList=(ArrayList<UserLogin>)query.getResultList();	
							logger.info("Total UserLogin Partner Size --------------->"+resultList.size());
							if(resultList.size() >0){ 
								for(int j=0; j<resultList.size(); j++){
									 logger.info("User ID =======>"+resultList.get(j).getUserDetails().get(a).getUser_Details_ID());
									 query=null;
									 query=entityManager.createQuery("from UserDetail where user_Details_ID=? and acctCreated_date between ? and ?");
									 query.setParameter(1, resultList.get(j).getUserDetails().get(a).getUser_Details_ID()); 
									 query.setParameter(2, fromDate);
									 query.setParameter(3, ToDate);
									 list=(ArrayList<UserDetail>)query.getResultList(); 
									 logger.info("Total UserDetails Partner Size --------------->"+list.size());
									 for(UserDetail userdetails : list) {
											member = new Member();	
							            	logger.info("Member Code ----->"+userdetails.getMemberID());
											member.setsNo(i);
											member.setFirstName(userdetails.getFirstname() + " " + userdetails.getLastname());
											member.setMemberID(userdetails.getMemberID());
											member.setBookingStatus(userdetails.getClinic_status());
											member.setPhoneNumber(userdetails.getPhonenumber1());
											member.setEmailID(userdetails.getEmail1());
											member.setInvestmentDate(userdetails.getAcctCreated_date());
											member.setActType(userdetails.getAcctType());
											member.setTotalAmount(userdetails.getTotalFees());
											member.setStatus(userdetails.getUserLogin().getStatus());
											member.setAgentCode(userdetails.getAgent_code());
											member.setPaymentpath(userdetails.getPayStatus());
											memberReportList.add(member);
											i++;
										}
								 }
							}	
						}
											
					}catch(Exception e){
						logger.info("PartnerReport Exception --------->"+e.getMessage()); 
					}
					return memberReportList;
				}
				
				
				@Transactional(value="transactionManager")
				@SuppressWarnings (value="unchecked")
				public ArrayList<Member> getAllPaymentReportList(Member member, ArrayList<Member> memberReportList){
					Query query=null;
					List<UserDetail> resultList;
					try {	
						logger.info("From Date ui ------------->"+member.getBookingdate());
						logger.info("To Date ui ------------->"+member.getEnrollmentDate());
						int i=1;
						if(member.getWstatus().equalsIgnoreCase("All")){
							query=entityManager.createQuery("from UserDetail where acctCreated_date between ? and ?");
							query.setParameter(1, member.getBookingdate());
							query.setParameter(2, member.getEnrollmentDate());
							resultList=(ArrayList<UserDetail>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
							for(UserDetail userdetails : resultList) {
									member = new Member();	
					            	logger.info("Member Code ----->"+userdetails.getMemberID());
									member.setsNo(i);
									member.setFirstName(userdetails.getFirstname() + " " + userdetails.getLastname());
									member.setMemberID(userdetails.getMemberID());
									member.setBookingStatus(userdetails.getPayStatus());
									member.setPhoneNumber(userdetails.getPhonenumber1());
									member.setInvestmentDate(userdetails.getAcctCreated_date());
									member.setActType(userdetails.getAcctType());
									member.setTotalAmount(userdetails.getTotalFees());
									member.setStatus(userdetails.getUserLogin().getStatus());
									member.setBankName(userdetails.getBankName());
									member.setEmailID(userdetails.getEmail1());
									member.setBankAcctNumber(userdetails.getBankAcctNumber());
									member.setSelectedCountry(userdetails.getCountry());
									memberReportList.add(member);
									i++;
						      }
						}else if(member.getWstatus().equalsIgnoreCase("Paid")){
							query=entityManager.createQuery("from UserDetail where payStatus=? and acctCreated_date between ? and ?");
							query.setParameter(1, "Payment Uploaded");
							query.setParameter(2, member.getBookingdate());
							query.setParameter(3, member.getEnrollmentDate());
							resultList=(ArrayList<UserDetail>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
							for(UserDetail userdetails : resultList) {
									member = new Member();	
					            	logger.info("Member Code ----->"+userdetails.getMemberID());
									member.setsNo(i);
									member.setFirstName(userdetails.getFirstname() + " " + userdetails.getLastname());
									member.setMemberID(userdetails.getMemberID());
									member.setBookingStatus(userdetails.getPayStatus());
									member.setPhoneNumber(userdetails.getPhonenumber1());
									member.setInvestmentDate(userdetails.getAcctCreated_date());
									member.setActType(userdetails.getAcctType());
									member.setTotalAmount(userdetails.getTotalFees());
									member.setStatus(userdetails.getUserLogin().getStatus());
									member.setBankName(userdetails.getBankName());
									member.setEmailID(userdetails.getEmail1());
									member.setBankAcctNumber(userdetails.getBankAcctNumber());
									member.setSelectedCountry(userdetails.getCountry());
									memberReportList.add(member);
									i++;
						      }
						}else if(member.getWstatus().equalsIgnoreCase("Not-Paid")){
							query=entityManager.createQuery("from UserDetail where payStatus=? and acctCreated_date between ? and ?");
							query.setParameter(1, "Waiting");
							query.setParameter(2, member.getBookingdate());
							query.setParameter(3, member.getEnrollmentDate());
							resultList=(ArrayList<UserDetail>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
							for(UserDetail userdetails : resultList) {
									member = new Member();	
					            	logger.info("Member Code ----->"+userdetails.getMemberID());
									member.setsNo(i);
									member.setFirstName(userdetails.getFirstname() + " " + userdetails.getLastname());
									member.setMemberID(userdetails.getMemberID());
									member.setBookingStatus(userdetails.getPayStatus());
									member.setPhoneNumber(userdetails.getPhonenumber1());
									member.setInvestmentDate(userdetails.getAcctCreated_date());
									member.setActType(userdetails.getAcctType());
									member.setTotalAmount(userdetails.getTotalFees());
									member.setStatus(userdetails.getUserLogin().getStatus());
									member.setBankName(userdetails.getBankName());
									member.setEmailID(userdetails.getEmail1());
									member.setBankAcctNumber(userdetails.getBankAcctNumber());
									member.setSelectedCountry(userdetails.getCountry());
									memberReportList.add(member);
									i++;
						      }
						
						}
						
						logger.info("memberReport List Size -------->"+memberReportList.size()); 
					}catch(Exception e) {
						logger.info("Exception ----------->"+e.getMessage());
					}finally{
						
					}		
					return memberReportList;
				}
				
				// ---------------------- load All Member Report Data Based On Date -------------------------
				@Transactional(value="transactionManager")
				@SuppressWarnings (value="unchecked")
				public ArrayList<Employee> getAllEmpReportList(Employee emp, ArrayList<Employee> empReportList){
					Query query=null;
					List<EmployeeDetails> resultList;
					try {	
						logger.info("From Date ui ------------->"+emp.getFromDate());
						logger.info("To Date ui ------------->"+emp.getToDate());
						if(emp.getEmpRole().equalsIgnoreCase("All")){
							query=entityManager.createQuery("from EmployeeDetails where acctCreated_date between ? and ?");
							query.setParameter(1, emp.getFromDate());
							query.setParameter(2, emp.getToDate());
							resultList=(ArrayList<EmployeeDetails>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
							int i=1;
							for(EmployeeDetails empdetails : resultList) {
									emp = new Employee();
									logger.info("Employee Code ----->"+empdetails.getEmployeeCode());
									emp.setsNo(i);
									emp.setName(empdetails.getEmployeeName()); 
									emp.setEmployeeCode(empdetails.getEmployeeCode());
									emp.setPhoneNumber(empdetails.getEmployeePhoneNumber());
									emp.setAddress(empdetails.getEmployeeAddress()); 
									emp.setCreatedDate(empdetails.getAcctCreated_date());
									emp.setEmpRole(empdetails.getEmpRole()); 
									emp.setRefEmploy(empdetails.getRefCode());
									emp.setSalary(empdetails.getEmpSalary());
									emp.setJoin_date(empdetails.getJoin_date());
									emp.setEmpType(empdetails.getEmployeeType());
									empReportList.add(emp);
									i++;
						      }
						}else{
							query=entityManager.createQuery("from EmployeeDetails where empRole=? and acctCreated_date between ? and ?");
							query.setParameter(1, emp.getEmpRole()); 
							query.setParameter(2, emp.getFromDate());
							query.setParameter(3, emp.getToDate());
							resultList=(ArrayList<EmployeeDetails>)query.getResultList();
							logger.info("Result List ---------------->"+resultList.size()); 
							int i=1;
							for(EmployeeDetails empdetails : resultList) {
									emp = new Employee();
									logger.info("Employee Code ----->"+empdetails.getEmployeeCode());
									emp.setsNo(i);
									emp.setName(empdetails.getEmployeeName()); 
									emp.setEmployeeCode(empdetails.getEmployeeCode());
									emp.setPhoneNumber(empdetails.getEmployeePhoneNumber());
									emp.setAddress(empdetails.getEmployeeAddress()); 
									emp.setCreatedDate(empdetails.getAcctCreated_date());
									emp.setEmpRole(empdetails.getEmpRole()); 
									emp.setRefEmploy(empdetails.getRefCode());
									emp.setSalary(empdetails.getEmpSalary());
									emp.setJoin_date(empdetails.getJoin_date());
									emp.setEmpType(empdetails.getEmployeeType());
									empReportList.add(emp);
									i++;
						      }
						}
						logger.info("memberReport List Size -------->"+empReportList.size()); 
					}catch(Exception e) {
						logger.info("Exception ----------->"+e.getMessage());
					}finally{
						
					}		
					return empReportList;
				}
				
			//----------- Update Agent Profile -----------
				@Transactional(value="transactionManager")
				public Agent updateAgentProfile(Agent agent){
					logger.info("[DAO] updateAgentProfile method -------------");
					AgentDetails agdetails = null;
					try{
						 agdetails = entityManager.find(AgentDetails.class, agent.getPrimaryKey());
						 logger.info("Agent Details Primary id---------->"+agent.getPrimaryKey()); 
						 agdetails.setAgentCode(agent.getAgentCode());
						 agdetails.setAgentName(agent.getName());
						 agdetails.setAgentEmailID(agent.getEmailID());
						 agdetails.setAgentPhoneNumber(agent.getPhoneNumber());
						 agdetails.setAgentAddress(agent.getAddress());
						 agdetails.setAgentBankName(agent.getBankName());
						 agdetails.setAgentAccountNumber(agent.getAccountNumber());
						 agdetails.setAgentBankBranchName(agent.getBankBranchName());
						 agdetails.setAgentType(agent.getAgentType());
						 entityManager.merge(agdetails);
						 logger.info("-------- After Merge details --------->"); 
						 agent.setStatus("success");
					}catch(Exception e){
						agent.setStatus("failure");
						logger.info("Exception ------------>"+e.getMessage());
					}
					finally{
						entityManager.flush();
						entityManager.clear();
					}
					return agent;	
				}
				
				@Transactional(value="transactionManager")
				public Employee getEmployeeProfile(Employee emp){
					try{
						UserLogin userlogin = entityManager.find(UserLogin.class, Integer.valueOf(emp.getUserLoginPrimaryKey()));
						emp.setUsername(userlogin.getUsername());
						logger.info("[DAO getEmployeeProfile]userLoginPrimaryKey --------->"+emp.getUserLoginPrimaryKey());
						logger.info("[DAO getEmployeeProfile]Employee Code --------->"+userlogin.getEmployeeDetails().get(0).getEmployeeCode());
						logger.info("[DAO getEmployeeProfile]Employee Name --------->"+userlogin.getEmployeeDetails().get(0).getEmployeeName());
						logger.info("[DAO getEmployeeProfile]Employee Role --------->"+userlogin.getEmployeeDetails().get(0).getEmpRole());
						logger.info("[DAO getEmployeeProfile]Ref Employee --------->"+userlogin.getEmployeeDetails().get(0).getRefCode());
						emp.setPrimaryKey(userlogin.getEmployeeDetails().get(0).getEmployee_Details_ID());
						emp.setAddress(userlogin.getEmployeeDetails().get(0).getEmployeeAddress());
						emp.setName(userlogin.getEmployeeDetails().get(0).getEmployeeName());
						emp.setEmployeeCode(userlogin.getEmployeeDetails().get(0).getEmployeeCode());
						emp.setEmailID(userlogin.getEmployeeDetails().get(0).getEmployeeEmailID());
						emp.setPhoneNumber(userlogin.getEmployeeDetails().get(0).getEmployeePhoneNumber());
						emp.setSalary(userlogin.getEmployeeDetails().get(0).getEmpSalary());
						emp.setEmpType(userlogin.getEmployeeDetails().get(0).getEmployeeType());
						emp.setEmpRole(userlogin.getEmployeeDetails().get(0).getEmpRole());
						emp.setRefEmploy(userlogin.getEmployeeDetails().get(0).getRefCode());
						logger.info("Primary Key ------------------>"+emp.getPrimaryKey());
					}catch(Exception e){
						logger.info("EmployeeProfile Exception ---------->"+e.getMessage());
					}finally{
						
					}
					return emp;
				}
				
				@Transactional(value="transactionManager")
				public Employee updateEmpProfile(Employee emp){
					logger.info("[DAO] updateEmpProfile method -------------");
					EmployeeDetails empdetails = null;
					try{
						empdetails = entityManager.find(EmployeeDetails.class, emp.getPrimaryKey());
						logger.info("Employee Details Primary id---------->"+emp.getPrimaryKey()); 
						empdetails.setEmployeeAddress(emp.getAddress());
						empdetails.setEmployeeCode(emp.getEmployeeCode());
						empdetails.setEmployeeEmailID(emp.getEmailID());
						empdetails.setEmployeeName(emp.getName());
						empdetails.setEmployeePhoneNumber(emp.getPhoneNumber());
						empdetails.setEmployeeType(emp.getEmpType());
						empdetails.setEmpRole(emp.getEmpRole());
						empdetails.setEmpSalary(emp.getSalary());
						empdetails.setRefCode(emp.getRefEmploy());
						entityManager.merge(empdetails);
						logger.info("-------- After Merge details --------->"); 
						emp.setStatus("success");
					}catch(Exception e){
						emp.setStatus("failure");
						logger.info("update Employee Exception ---------->"+e.getMessage());
					}finally{
						entityManager.flush();
						entityManager.clear();
					}
					return emp;
				}
				
				//------------- getEmp Report View -----------
				@Transactional(value="transactionManager")
				@SuppressWarnings("unchecked")
				public ArrayList<Employee> getEmpReport(String employeeCode,ArrayList<Employee> empreportlist){
					Query query=null;
					Employee emp;
					ArrayList<EmployeeDetails> resultList;
					ArrayList<AgentDetails> list;
					logger.info("[DAO]Employee Code ------------>"+employeeCode);
					try{
						query=entityManager.createQuery("from EmployeeDetails where empCode=?");
						query.setParameter(1, employeeCode);
						resultList=(ArrayList<EmployeeDetails>)query.getResultList();
						logger.info("Employee Details List size ---------->"+resultList.size());
						if(resultList.size() >0){
							query=entityManager.createQuery("from AgentDetails where refEmpCode=?");
							query.setParameter(1, employeeCode);
							list=(ArrayList<AgentDetails>)query.getResultList();
							logger.info("Agent Details List size -------------->"+resultList.size());
							int i=1;
							for(AgentDetails aglist :list){
								emp = new Employee();
								emp.setsNo(i);
								emp.setEmployeeCode(employeeCode);
								emp.setName(resultList.get(0).getEmployeeName());
								emp.setPhoneNumber(resultList.get(0).getEmployeePhoneNumber());
								emp.setEmpRole(resultList.get(0).getEmpRole());
								emp.setRefEmploy(resultList.get(0).getRefCode());
								emp.setSelectedCountry(resultList.get(0).getCountry());
								emp.setCreatedDate(resultList.get(0).getAcctCreated_date()); 
								emp.setAgentName(aglist.getAgentName());
								emp.setAgentCode(aglist.getAgentCode()); 
								emp.setAgentphoneNumber(aglist.getAgentPhoneNumber()); 
								emp.setAgentType(aglist.getAgentType()); 
								if(resultList.get(0).getEmpRole().equalsIgnoreCase("Clinic Non-Manager") ||
										resultList.get(0).getEmpRole().equalsIgnoreCase("Investment Manager")){
									emp.setClinicName(resultList.get(0).getClinicdetails().get(0).getClinicName());
									emp.setClinicCode(resultList.get(0).getClinicdetails().get(0).getClinicCode());
								}else if(resultList.get(0).getEmpRole().equalsIgnoreCase("Investment Non-Manager") ||
										resultList.get(0).getEmpRole().equalsIgnoreCase("Clinic Manager")){
									if(aglist.getClinicdetails().get(0).getEmployee_details().getEmployee_Details_ID() == resultList.get(0).getEmployee_Details_ID()){
										emp.setClinicName(resultList.get(0).getClinicdetails().get(0).getClinicName());
										emp.setClinicCode(resultList.get(0).getClinicdetails().get(0).getClinicCode());
									}else{
										emp.setClinicName("");
										emp.setClinicCode(""); 
									}
								}	
								empreportlist.add(emp);
								i++;
							}
							
						}
					}catch(Exception e){ 
						logger.info("EmpReport Exception ---------->"+e.getMessage());
					}finally{
						
					}
					return empreportlist;
				}
				
		
	//-------- Search Agent List for Not All --------------
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public ArrayList<Agent> searchAgent(ArrayList<Agent> searchagList,Agent agent,String customQuery) {
		Query query=null;
		List<AgentDetails> resultList;
		try {	
			query=entityManager.createQuery(customQuery);
			resultList=(ArrayList<AgentDetails>)query.getResultList();
			logger.info("Result List ------->"+resultList.size());
			int i=1;
			for(AgentDetails agdetails :resultList){
				agent = new Agent();
				agent.setsNo(i);
				agent.setPrimaryKey(agdetails.getAgent_Details_ID());
		    	agent.setPhoneNumber(agdetails.getAgentPhoneNumber());
		    	agent.setAddress(agdetails.getAgentAddress());
		    	agent.setEmailID(agdetails.getAgentEmailID());
		    	agent.setAccountNumber(agdetails.getAgentAccountNumber());
		    	agent.setBankBranchName(agdetails.getAgentBankBranchName());
		    	agent.setRefEmploy(agdetails.getRefEmpCode()); 				
		    	agent.setAgentCode(agdetails.getAgentCode());
		    	agent.setName(agdetails.getAgentName());
		    	agent.setBankName(agdetails.getAgentBankName());
		    	agent.setAgentType(agdetails.getAgentType());
		    	agent.setSelectedCountry(agdetails.getCountry());
		    	searchagList.add(agent);
		    	i++;
			}
			logger.info("Search agList --------->"+searchagList.size()); 
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[DAO]SearchAgent Exception -------->"+e.getMessage()); 
		}finally{
			
		}
		return searchagList;
	}
			
			
	//-------- Search Employee List for Country and Code and Name and Type Values --------------
	@Transactional(value="transactionManager")
	@SuppressWarnings("unchecked")
	public ArrayList<Employee> searchEmployee(ArrayList<Employee> searchempList,Employee emp,String customQuery) {
		Query query=null;
		List<EmployeeDetails> resultList;
		logger.info("searchEmployee-Query ---------->"+customQuery);
		try {
			query=entityManager.createQuery(customQuery);
			resultList=(ArrayList<EmployeeDetails>)query.getResultList();
			logger.info("Result List ------->"+resultList.size());
			int i=1;
			for(EmployeeDetails empdet :resultList){
				emp = new Employee();
				emp.setsNo(i);
				emp.setPrimaryKey(empdet.getEmployee_Details_ID());
				emp.setEmployeeCode(empdet.getEmployeeCode()); 
				emp.setName(empdet.getEmployeeName());
				emp.setPhoneNumber(empdet.getEmployeePhoneNumber());
				emp.setEmailID(empdet.getEmployeeEmailID());
				emp.setEmpType(empdet.getEmployeeType());
				emp.setSalary(empdet.getEmpSalary()); 
				emp.setAddress(empdet.getEmployeeAddress()); 
				emp.setEmpRole(empdet.getEmpRole()); 
				emp.setRefEmploy(empdet.getRefCode()); 
				emp.setSelectedCountry(empdet.getCountry());
			   searchempList.add(emp);
			   i++;
			}
			logger.info("Search empList --------->"+searchempList.size()); 
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[DAO]SearchEmployee Exception -------->"+e.getMessage()); 
		}finally{
			
		}
		return searchempList;
	}
		
			
			//-------- Search Clinic List for AgentName Based --------------
			@Transactional(value="transactionManager")
			public ArrayList<Clinic> searchClinic(ArrayList<Clinic> searchclinicList,Clinic clinic) {
				try{
					logger.info("------- Search Clinic -----------");
					AgentDetails agentdetails = entityManager.find(AgentDetails.class, clinic.getAgentPK());
					if(agentdetails.getClinicdetails().size()>0){
						logger.info("List Size ------------->"+agentdetails.getClinicdetails().size());
						int j=1;
						for(int i=0;i<agentdetails.getClinicdetails().size();i++){
							clinic = new Clinic();	
							clinic.setsNo(j);
							clinic.setPrimaryKey(agentdetails.getClinicdetails().get(i).getClinic_Details_ID());
							clinic.setClinicName(agentdetails.getClinicdetails().get(i).getClinicName()); 
							clinic.setClinicCode(agentdetails.getClinicdetails().get(i).getClinicCode());
							clinic.setPhoneNumber(agentdetails.getClinicdetails().get(i).getClinicPhoneNumber());
							clinic.setAddress(agentdetails.getClinicdetails().get(i).getClinicAddress());
							clinic.setEmailID(agentdetails.getClinicdetails().get(i).getClinicEmailID()); 
							clinic.setName(agentdetails.getClinicdetails().get(i).getAgent_details().getAgentName());
							searchclinicList.add(clinic);
							j++;
						}
					}
				}catch(Exception e){
					logger.info("Search Clinic Exception ------->"+e.getMessage()); 
				}
				
				return searchclinicList;
			}
			
			//------------ Search Partner Clinic ----------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public ArrayList<GLGMem> searchPartnerClinic(ArrayList<GLGMem> searchPartnerClinic,Clinic clinic) {
				Query query=null;
				GLGMem glgmember=null;
				List<UserDetail> resultList;
				List<ClinicDetails> clinicList;
				try{
										
					if(clinic.getAgentCode().equalsIgnoreCase("All") && clinic.getName().equalsIgnoreCase("All")){
						logger.info("------[DAO] Both Agent and Employee are All --------");
						query=entityManager.createQuery("from UserDetail where clinic_status=? and add_clinic_date between ? and ? order by add_clinic_date desc");
						query.setParameter(1, "Registered");
						query.setParameter(2, clinic.getFromDate());
						query.setParameter(3, clinic.getToDate());
						resultList=(ArrayList<UserDetail>)query.getResultList();
						System.out.println("Clinic List Size on Agent andEmployee All ---->"+resultList.size());
						int i=1;
						for(UserDetail user :resultList){
							glgmember = new GLGMem();
							glgmember.setsNo(i);
							glgmember.setMemberID(user.getMemberID());
							glgmember.setMemberName(user.getFirstname()+"  "+user.getLastname());
							glgmember.setMemberEmail(user.getEmail1());
							glgmember.setMemberPhone(user.getPhonenumber1());
							glgmember.setMemberType(user.getAcctType());
							glgmember.setTotalAmount(user.getTotalFees());
							glgmember.setNoofclinics(user.getNoofclinics());
							glgmember.setPaymentStatus(user.getPayStatus());
							glgmember.setStatus(user.getClinic_status());
							searchPartnerClinic.add(glgmember);
							i++;
						}
					}
					
					else if(clinic.getAgentCode().equalsIgnoreCase("All")){
						System.out.println("------[DAO] Agent only All  --------");
						query=entityManager.createQuery("from ClinicDetails where employee_Details_ID=?");
						query.setParameter(1, clinic.getEmployeePK());
						clinicList=(ArrayList<ClinicDetails>)query.getResultList();
						System.out.println("Clinic List Size on Agent All ---->"+clinicList.size());
						if(clinicList.size() >1){
							 for(ClinicDetails cd :clinicList){
								 query=entityManager.createQuery("from UserDetail where user_Details_ID =? and clinic_status=? and add_clinic_date between ? and ? order by add_clinic_date desc");
								 query.setParameter(1, cd.getUserdetails().getUser_Details_ID());
								 query.setParameter(2, "Registered");
								 query.setParameter(3, clinic.getFromDate());
								 query.setParameter(4, clinic.getToDate());
								 resultList=(ArrayList<UserDetail>)query.getResultList();
								 System.out.println("Partner Userdetails List Size on Agent All ------>"+resultList.size());
								 int i=1;
								 for(int j=0;j<=resultList.size();j++){
							            for(int k=j+1;k<=resultList.size();k++){
							            	glgmember = new GLGMem();
											glgmember.setsNo(i);
											glgmember.setMemberID(resultList.get(j).getMemberID());
											glgmember.setMemberName(resultList.get(j).getFirstname()+"  "+resultList.get(j).getLastname());
											glgmember.setMemberEmail(resultList.get(j).getEmail1());
											glgmember.setMemberPhone(resultList.get(j).getPhonenumber1());
											glgmember.setMemberType(resultList.get(j).getAcctType());
											glgmember.setTotalAmount(resultList.get(j).getTotalFees());
											glgmember.setNoofclinics(resultList.get(j).getNoofclinics());
											glgmember.setPaymentStatus(resultList.get(j).getPayStatus());
											glgmember.setStatus(resultList.get(j).getClinic_status());
											searchPartnerClinic.add(glgmember);
							                if(resultList.get(j).getMemberID().equals(resultList.get(k).getMemberID())){
							                	searchPartnerClinic.remove(k);
							                }
							            }
							        }
							 }
						}else{
							System.out.println("--------- No data for Agent All ---------");
							query=null;
							query=entityManager.createQuery("from UserDetail where user_Details_ID =? and clinic_status=? and add_clinic_date between ? and ? order by add_clinic_date desc");
							query.setParameter(1, clinicList.get(0).getUserdetails().getUser_Details_ID());
							query.setParameter(2, "Registered");
							query.setParameter(3, clinic.getFromDate());
							query.setParameter(4, clinic.getToDate());
							resultList=(ArrayList<UserDetail>)query.getResultList();
							System.out.println("Partner Userdetails List Size on Agent All -------->"+resultList.size());
							int i=1;
							for(int j=0;j<=resultList.size();j++){
					            for(int k=j+1;k<=resultList.size();k++){
					            	glgmember = new GLGMem();
									glgmember.setsNo(i);
									glgmember.setMemberID(resultList.get(j).getMemberID());
									glgmember.setMemberName(resultList.get(j).getFirstname()+"  "+resultList.get(j).getLastname());
									glgmember.setMemberEmail(resultList.get(j).getEmail1());
									glgmember.setMemberPhone(resultList.get(j).getPhonenumber1());
									glgmember.setMemberType(resultList.get(j).getAcctType());
									glgmember.setTotalAmount(resultList.get(j).getTotalFees());
									glgmember.setNoofclinics(resultList.get(j).getNoofclinics());
									glgmember.setPaymentStatus(resultList.get(j).getPayStatus());
									glgmember.setStatus(resultList.get(j).getClinic_status());
									searchPartnerClinic.add(glgmember);
					                if(resultList.get(j).getMemberID().equals(resultList.get(k).getMemberID())){
					                	searchPartnerClinic.remove(k);
					                }
					            }
					        }
							
						}
						
					}
					
					else if(clinic.getName().equalsIgnoreCase("All")){

						System.out.println("------[DAO] Employee only All  --------");
						query=entityManager.createQuery("from ClinicDetails where agent_Details_ID=?");
						query.setParameter(1, clinic.getAgentPK());
						clinicList=(ArrayList<ClinicDetails>)query.getResultList();
						System.out.println("Clinic List Size on Employee All ---->"+clinicList.size());
						if(clinicList.size() >1){
							 for(ClinicDetails cd :clinicList){
								 System.out.println("User ID =======>"+cd.getUserdetails().getUser_Details_ID());
								 query=entityManager.createQuery("from UserDetail where user_Details_ID =? and clinic_status=? and add_clinic_date between ? and ? order by add_clinic_date desc");
								 query.setParameter(1, cd.getUserdetails().getUser_Details_ID());
								 query.setParameter(2, "Registered");
								 query.setParameter(3, clinic.getFromDate());
								 query.setParameter(4, clinic.getToDate());
								 resultList=(ArrayList<UserDetail>)query.getResultList();
								 System.out.println("Partner Userdetails List Size on Employee All ------>"+resultList.size());
								 int i=1;
								 for(int j=0;j<=resultList.size();j++){
							            for(int k=j+1;k<=resultList.size();k++){
							            	glgmember = new GLGMem();
											glgmember.setsNo(i);
											glgmember.setMemberID(resultList.get(j).getMemberID());
											glgmember.setMemberName(resultList.get(j).getFirstname()+"  "+resultList.get(j).getLastname());
											glgmember.setMemberEmail(resultList.get(j).getEmail1());
											glgmember.setMemberPhone(resultList.get(j).getPhonenumber1());
											glgmember.setMemberType(resultList.get(j).getAcctType());
											glgmember.setTotalAmount(resultList.get(j).getTotalFees());
											glgmember.setNoofclinics(resultList.get(j).getNoofclinics());
											glgmember.setPaymentStatus(resultList.get(j).getPayStatus());
											glgmember.setStatus(resultList.get(j).getClinic_status());
											searchPartnerClinic.add(glgmember);
							                if(resultList.get(j).getMemberID().equals(resultList.get(k).getMemberID())){
							                	searchPartnerClinic.remove(k);
							                }
							            }
							        }
							 }
						}else{
							System.out.println("--------- No data for Employee All ---------");
							query=null;
							query=entityManager.createQuery("from UserDetail where user_Details_ID =? and clinic_status=? and add_clinic_date between ? and ? order by add_clinic_date desc");
							query.setParameter(1, clinicList.get(0).getUserdetails().getUser_Details_ID());
							query.setParameter(2, "Registered");
							query.setParameter(3, clinic.getFromDate());
							query.setParameter(4, clinic.getToDate());
							resultList=(ArrayList<UserDetail>)query.getResultList();
							System.out.println("Partner Userdetails List Size on Employee All -------->"+resultList.size());
							int i=1;
							for(int j=0;j<=resultList.size();j++){
					            for(int k=j+1;k<=resultList.size();k++){
					            	glgmember = new GLGMem();
									glgmember.setsNo(i);
									glgmember.setMemberID(resultList.get(j).getMemberID());
									glgmember.setMemberName(resultList.get(j).getFirstname()+"  "+resultList.get(j).getLastname());
									glgmember.setMemberEmail(resultList.get(j).getEmail1());
									glgmember.setMemberPhone(resultList.get(j).getPhonenumber1());
									glgmember.setMemberType(resultList.get(j).getAcctType());
									glgmember.setTotalAmount(resultList.get(j).getTotalFees());
									glgmember.setNoofclinics(resultList.get(j).getNoofclinics());
									glgmember.setPaymentStatus(resultList.get(j).getPayStatus());
									glgmember.setStatus(resultList.get(j).getClinic_status());
									searchPartnerClinic.add(glgmember);
					                if(resultList.get(j).getMemberID().equals(resultList.get(k).getMemberID())){
					                	searchPartnerClinic.remove(k);
					                }
					            }
					        }
						}
						
					}else{
						
						System.out.println("------[DAO] Employee and Agent are not All  --------");
						query=entityManager.createQuery("from ClinicDetails where agent_Details_ID=? and employee_Details_ID=?");
						query.setParameter(1, clinic.getAgentPK());
						query.setParameter(2, clinic.getEmployeePK());
						clinicList=(ArrayList<ClinicDetails>)query.getResultList();
						System.out.println("Partner Clinic List Size -------->"+clinicList.size());
						if(clinicList.size() >1){
							 for(ClinicDetails cd :clinicList){
								 System.out.println("User ID =======>"+cd.getUserdetails().getUser_Details_ID());
								 query=entityManager.createQuery("from UserDetail where user_Details_ID =? and clinic_status=? and add_clinic_date between ? and ? order by add_clinic_date desc");
								 query.setParameter(1, cd.getUserdetails().getUser_Details_ID());
								 query.setParameter(2, "Registered");
								 query.setParameter(3, clinic.getFromDate());
								 query.setParameter(4, clinic.getToDate());
								 resultList=(ArrayList<UserDetail>)query.getResultList();
								 System.out.println("Partner Userdetails List Size -------->"+clinicList.size());
								 int i=1;
								 for(int j=0;j<=resultList.size();j++){
							            for(int k=j+1;k<=resultList.size();k++){
							            	glgmember = new GLGMem();
											glgmember.setsNo(i);
											glgmember.setMemberID(resultList.get(j).getMemberID());
											glgmember.setMemberName(resultList.get(j).getFirstname()+"  "+resultList.get(j).getLastname());
											glgmember.setMemberEmail(resultList.get(j).getEmail1());
											glgmember.setMemberPhone(resultList.get(j).getPhonenumber1());
											glgmember.setMemberType(resultList.get(j).getAcctType());
											glgmember.setTotalAmount(resultList.get(j).getTotalFees());
											glgmember.setNoofclinics(resultList.get(j).getNoofclinics());
											glgmember.setPaymentStatus(resultList.get(j).getPayStatus());
											glgmember.setStatus(resultList.get(j).getClinic_status());
											searchPartnerClinic.add(glgmember);
							                if(resultList.get(j).getMemberID().equals(resultList.get(k).getMemberID())){
							                	searchPartnerClinic.remove(k);
							                }
							            }
							        }
								/* HashSet<GLGMem> set = new HashSet<GLGMem>(searchPartnerClinic);
								 searchPartnerClinic.clear();
								 searchPartnerClinic.addAll(set);
								 searchPartnerClinic = (ArrayList<GLGMem>) searchPartnerClinic.stream().distinct().collect(Collectors.toList());*/
								System.out.println("List size ----->"+searchPartnerClinic.size());
							 }
						}else{
							System.out.println("--------- No data for Agent and Employee not All ---------");
							query=null;
							query=entityManager.createQuery("from UserDetail where user_Details_ID =? and clinic_status=? and add_clinic_date between ? and ? order by add_clinic_date desc");
							query.setParameter(1, clinicList.get(0).getUserdetails().getUser_Details_ID());
							query.setParameter(2, "Registered");
							query.setParameter(3, clinic.getFromDate());
							query.setParameter(4, clinic.getToDate());
							resultList=(ArrayList<UserDetail>)query.getResultList();
							int i=1;
							for(int j=0;j<=resultList.size();j++){
					            for(int k=j+1;k<=resultList.size();k++){
					            	glgmember = new GLGMem();
									glgmember.setsNo(i);
									glgmember.setMemberID(resultList.get(j).getMemberID());
									glgmember.setMemberName(resultList.get(j).getFirstname()+"  "+resultList.get(j).getLastname());
									glgmember.setMemberEmail(resultList.get(j).getEmail1());
									glgmember.setMemberPhone(resultList.get(j).getPhonenumber1());
									glgmember.setMemberType(resultList.get(j).getAcctType());
									glgmember.setTotalAmount(resultList.get(j).getTotalFees());
									glgmember.setNoofclinics(resultList.get(j).getNoofclinics());
									glgmember.setPaymentStatus(resultList.get(j).getPayStatus());
									glgmember.setStatus(resultList.get(j).getClinic_status());
									searchPartnerClinic.add(glgmember);
					                if(resultList.get(j).getMemberID().equals(resultList.get(k).getMemberID())){
					                	searchPartnerClinic.remove(k);
					                }
					            }
					        }
						}
						
					}
				}catch(Exception e){
					logger.info("[DAO]searchPartnerClinic Exception --------->"+e.getMessage());
				}finally{
					
				}			
				return searchPartnerClinic;
			}
			
			//--------- Get Sales Report for Clinic --------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public ArrayList<Agent> getSalesClincReport(ArrayList<Agent> searchMonthlyClinicReport,String selectedMonth,String reportName,String name){
				Query query=null;
				List<AgentDetails> agList;
				List<ClinicDetails> clinicList;
				Agent agent = null;
				BigDecimal totalclinics = BigDecimal.valueOf(0);
				try{
		
					if(name.equalsIgnoreCase("All")){
						
						query=entityManager.createQuery("from AgentDetails");
						agList=(ArrayList<AgentDetails>)query.getResultList();
						logger.info("Agent Details List for sales -------->"+agList.size());
						int i=1;
						for(AgentDetails agentdet : agList) {
							agent = new Agent();
							agent.setCreatedDate(agentdet.getAcctCreated_date());
							logger.info("Created Date --->"+agent.getCreatedDate());						   
							SimpleDateFormat month_date = new SimpleDateFormat("MMM");
							String month_name = month_date.format(agent.getCreatedDate());
							logger.info("Month Name ::::" + month_name);
							if(month_name.equalsIgnoreCase(selectedMonth)){
								
								logger.info("Month Name are Equal ::::" + month_name + "   ==   "+selectedMonth);
								query=null;
								query=entityManager.createQuery("from AgentDetails where agentType=? and acctCreated_date=?");
								query.setParameter(1,"Clinic Agent");
								query.setParameter(2, agent.getCreatedDate());
								List<AgentDetails> agdetList=(ArrayList<AgentDetails>)query.getResultList();
								logger.info("List Size ::::" + agdetList.size());
								if(agdetList.size()>0) {
									
									for(AgentDetails agdetails : agdetList) {
										agent = new Agent();
										agent.setsNo(i);   
										agent.setName(agdetails.getAgentName());
										agent.setAgentCode(agdetails.getAgentCode());
										agent.setRefEmploy(agdetails.getRefEmpCode());
										agent.setSelectedCountry(agdetails.getCountry());
										agent.setPhoneNumber(agdetails.getAgentPhoneNumber());
										agent.setEmailID(agdetails.getAgentEmailID());
										agent.setBankName(agdetails.getAgentBankName());
										agent.setBankBranchName(agdetails.getAgentBankBranchName());
										agent.setAccountNumber(agdetails.getAgentAccountNumber());
										agent.setAddress(agdetails.getAgentAddress());
										agent.setPrimaryKey(agdetails.getAgent_Details_ID()); 
										
									
										query=entityManager.createQuery("from ClinicDetails where agent_Details_ID=?");
										query.setParameter(1, agdetails.getAgent_Details_ID());
										clinicList=(ArrayList<ClinicDetails>)query.getResultList();
										logger.info("Clinic List Size ----->"+clinicList.size()); 
										agent.setClinicCount(clinicList.size()); 
										
										totalclinics = totalclinics.add(new BigDecimal(agent.getClinicCount()));
										logger.info("Total Clinics --->"+totalclinics);
										agent.setTotalClincs(totalclinics);  
										
										searchMonthlyClinicReport.add(agent);
										i++;
									}
									
								}else{
									logger.info("------------ No Result for ClinicAgent for this Month -----------");
								}
								
							}else{
								logger.info("------------ No Result for Both SelectedMonth and DatabaseMonth for ClinicSale Report -----------");
							}
						 }
						
					}
					else{
						
						String agentCode = name.substring(name.lastIndexOf("-")+1);
						logger.info("Agent Code --------->"+agentCode); 
						
						query=entityManager.createQuery("from AgentDetails");
						agList=(ArrayList<AgentDetails>)query.getResultList();
						logger.info("Agent Details List for sales not all -------->"+agList.size());
						int i=1;
						for(AgentDetails agentdet : agList) {
							agent = new Agent();
							agent.setCreatedDate(agentdet.getAcctCreated_date());
							logger.info("Created Date --->"+agent.getCreatedDate());						   
							SimpleDateFormat month_date = new SimpleDateFormat("MMM");
							String month_name = month_date.format(agent.getCreatedDate());
							logger.info("Month Name ::::" + month_name);
							if(month_name.equalsIgnoreCase(selectedMonth)){
								
								logger.info("Month Name are Equal ::::" + month_name + "   ==   "+selectedMonth);
								query=null;
								query=entityManager.createQuery("from AgentDetails where agentType=? and agentCode=? and acctCreated_date=?");
								query.setParameter(1,"Clinic Agent");
								query.setParameter(2, agentCode);
								query.setParameter(3, agent.getCreatedDate());
								List<AgentDetails> agdetList=(ArrayList<AgentDetails>)query.getResultList();
								logger.info("List Size ::::" + agdetList.size());
								if(agdetList.size()>0) {
									
									for(AgentDetails agdetails : agdetList) {
										agent = new Agent();
										agent.setsNo(i);   
										agent.setName(agdetails.getAgentName());
										agent.setAgentCode(agdetails.getAgentCode());
										agent.setRefEmploy(agdetails.getRefEmpCode());
										agent.setSelectedCountry(agdetails.getCountry());
										agent.setPhoneNumber(agdetails.getAgentPhoneNumber());
										agent.setEmailID(agdetails.getAgentEmailID());
										agent.setBankName(agdetails.getAgentBankName());
										agent.setBankBranchName(agdetails.getAgentBankBranchName());
										agent.setAccountNumber(agdetails.getAgentAccountNumber());
										agent.setAddress(agdetails.getAgentAddress());
										agent.setPrimaryKey(agdetails.getAgent_Details_ID()); 
										
									
										query=entityManager.createQuery("from ClinicDetails where agent_Details_ID=?");
										query.setParameter(1, agdetails.getAgent_Details_ID());
										clinicList=(ArrayList<ClinicDetails>)query.getResultList();
										logger.info("Clinic List Size ----->"+clinicList.size()); 
										agent.setClinicCount(clinicList.size()); 
										
										totalclinics = totalclinics.add(new BigDecimal(agent.getClinicCount()));
										logger.info("Total Clinics --->"+totalclinics);
										agent.setTotalClincs(totalclinics);  
										
										searchMonthlyClinicReport.add(agent);
										i++;
									}
									
								}else{
									logger.info("------------ No Result for ClinicAgent for this Month -----------");
								}
								
							}else{
								logger.info("------------ No Result for Both SelectedMonth and DatabaseMonth for ClinicSale Report -----------");
							}
						 }
					}
					
				}catch(Exception e){					
					logger.info("[DAO]searchMonthlyClinicReport Exception --------->"+e.getMessage());
				}finally{
					
				}			
				return searchMonthlyClinicReport;
			}
			
			//--------- Get Sales Report for Clinic --------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public ArrayList<Member> getSalesPartnerReport(ArrayList<Member> searchMonthlyPartnerReport,String selectedMonth,String memberName){
				Query query=null;
				List<UserDetail> userList;
				Member member;
				BigDecimal totalamount = BigDecimal.valueOf(0);
				try{
					if(memberName.equalsIgnoreCase("All")){
						
						query=entityManager.createQuery("from UserDetail");
						userList=(ArrayList<UserDetail>)query.getResultList();
						logger.info("User Details List for sales -------->"+userList.size());
						int i=1;
						for(UserDetail userdet : userList) {
							member = new Member();
							member.setBookingdate(userdet.getAcctCreated_date());
							logger.info("Created Date --->"+member.getBookingdate());						   
							SimpleDateFormat month_date = new SimpleDateFormat("MMM");
							String month_name = month_date.format(member.getBookingdate());
							if(month_name.equalsIgnoreCase(selectedMonth)){
								logger.info("Month Name are Equal ::::" + month_name + "   ==   "+selectedMonth);
								query=null;
								query=entityManager.createQuery("from UserDetail where acctCreated_date=? and NOT memberID='TRIO681111' and NOT memberID='TRIO581111' and NOT memberID='TRIO481111'");
								query.setParameter(1,member.getBookingdate());
								List<UserDetail> userdetList=(ArrayList<UserDetail>)query.getResultList();
								logger.info("userdet List Size ------>"+userdetList.size());
								for(UserDetail user :userdetList){
									member = new Member();
									member.setsNo(i);
									member.setFirstName(user.getFirstname() + " " + user.getLastname());
									member.setActType(user.getAcctType());
									member.setAgentCode(user.getAgent_code());
									member.setEmailID(user.getEmail1());
									member.setBankName(user.getBankName());
									member.setBankAcctNumber(user.getBankAcctNumber());
									member.setBookingStatus(user.getClinic_status());
									member.setMemberID(user.getMemberID());
									member.setNoofclinics(user.getNoofclinics());
									member.setPaymentpath(user.getPayStatus());
									member.setPhoneNumber(user.getPhonenumber1());
									member.setTotalAmount(user.getTotalFees()); 
									member.setSelectedCountry(user.getCountry()); 
									member.setUserLoginPrimaryKey(user.getUser_Details_ID()); 
									
									int temp=0;
									int finalAmount= 0;
									temp = (int)(user.getTotalFees()*(20.0f/100.0f));
									finalAmount = temp / 4;	
									/*if(user.getAcctType().equalsIgnoreCase("silver")) { 
										temp = (int)(user.getTotalFees()*(25.0f/100.0f));
										finalAmount = temp / 4;									
									}
									if(user.getAcctType().equalsIgnoreCase("gold")) {
										temp = (int)(user.getTotalFees()*(30.0f/100.0f));
										finalAmount = temp / 4;		 
									}
									if(user.getAcctType().equalsIgnoreCase("platinum")) {
										temp = (int)(user.getTotalFees()*(35.0f/100.0f));					
										finalAmount = temp / 4;
									}	*/
									member.setWithdrawAmount(finalAmount); 								
									totalamount = totalamount.add(new BigDecimal(member.getWithdrawAmount()));
									logger.info("Total Amount --->"+totalamount);
									member.setInvestmentAmount(totalamount);  
									logger.info("--------- Before add the member value to the list ----------");
									searchMonthlyPartnerReport.add(member);
									i++;
								}
								logger.info("searchMonthlyPartnerReport List Size --------->"+searchMonthlyPartnerReport.size());
							}else{
								logger.info("------- No Result for Both SelectedMonth and DatabaseMonth for PartnerSale Report -----------");
							}   
						}
						
					}else{
						
						String memberNumber = memberName.substring(memberName.lastIndexOf("-")+1);
						logger.info("Member Number --------->"+memberNumber); 
						
						query=entityManager.createQuery("from UserDetail");
						userList=(ArrayList<UserDetail>)query.getResultList();
						logger.info("User Details List for sales Not All-------->"+userList.size());
						int i=1;
						for(UserDetail userdet : userList) {
							member = new Member();
							member.setBookingdate(userdet.getAcctCreated_date());
							logger.info("Created Date --->"+member.getBookingdate());						   
							SimpleDateFormat month_date = new SimpleDateFormat("MMM");
							String month_name = month_date.format(member.getBookingdate());
							if(month_name.equalsIgnoreCase(selectedMonth)){
								logger.info("Month Name are Equal ::::" + month_name + "   ==   "+selectedMonth);
								query=null;
								query=entityManager.createQuery("from UserDetail where memberID=? and acctCreated_date=? ");
								query.setParameter(1,memberNumber);
								query.setParameter(2,member.getBookingdate());
								List<UserDetail> userdetList=(ArrayList<UserDetail>)query.getResultList();
								logger.info("userdet List Size ------>"+userdetList.size());
								for(UserDetail user :userdetList){
									member = new Member();
									member.setsNo(i);
									member.setFirstName(user.getFirstname() + " " + user.getLastname());
									member.setActType(user.getAcctType());
									member.setAgentCode(user.getAgent_code());
									member.setEmailID(user.getEmail1());
									member.setBankName(user.getBankName());
									member.setBankAcctNumber(user.getBankAcctNumber());
									member.setBookingStatus(user.getClinic_status());
									member.setMemberID(user.getMemberID());
									member.setNoofclinics(user.getNoofclinics());
									member.setPaymentpath(user.getPayStatus());
									member.setPhoneNumber(user.getPhonenumber1());
									member.setTotalAmount(user.getTotalFees()); 
									member.setSelectedCountry(user.getCountry()); 
									member.setUserLoginPrimaryKey(user.getUser_Details_ID()); 
									
									int temp=0;
									int finalAmount= 0;
									temp = (int)(user.getTotalFees()*(20.0f/100.0f));
									finalAmount = temp / 4;	
									/*if(user.getAcctType().equalsIgnoreCase("silver")) { 
										temp = (int)(user.getTotalFees()*(25.0f/100.0f));
										finalAmount = temp / 4;									
									}
									if(user.getAcctType().equalsIgnoreCase("gold")) {
										temp = (int)(user.getTotalFees()*(30.0f/100.0f));
										finalAmount = temp / 4;		 
									}
									if(user.getAcctType().equalsIgnoreCase("platinum")) {
										temp = (int)(user.getTotalFees()*(35.0f/100.0f));					
										finalAmount = temp / 4;
									}*/	
									member.setWithdrawAmount(finalAmount); 								
									totalamount = totalamount.add(new BigDecimal(member.getWithdrawAmount()));
									logger.info("Total Amount --->"+totalamount);
									member.setInvestmentAmount(totalamount);  
									logger.info("--------- Before add the member value to the list ----------");
									searchMonthlyPartnerReport.add(member);
									i++;
								}
								logger.info("searchMonthlyPartnerReport List Size --------->"+searchMonthlyPartnerReport.size());
							}else{
								logger.info("------- No Result for Both SelectedMonth and DatabaseMonth for PartnerSale Report -----------");
							}   
						}
						
					}
					
				}catch(Exception e){
					logger.info("[DAO]PartnerSaleReport Exception ------>"+e.getMessage());
				}
				finally{
					
				}
				return searchMonthlyPartnerReport;
			}
			
			//------------- Sales EmployeeManager Report ----------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public ArrayList<Employee> getSalesEmployeeReport(ArrayList<Employee> searchMonthlyEmployeeReport,String selectedMonth,String reportName,String empName){
				Query query=null;
				List<EmployeeDetails> empList;
				List<ClinicDetails> clinicList;
				List<AgentDetails> agList;
				Employee emp;
				BigDecimal totalclinics = BigDecimal.valueOf(0);
				BigDecimal totalagents = BigDecimal.valueOf(0);
				try{
					
					if(empName.equalsIgnoreCase("All")){
						
						query=entityManager.createQuery("from EmployeeDetails");
						empList=(ArrayList<EmployeeDetails>)query.getResultList();
						logger.info("Employee Details List for sales -------->"+empList.size());
						int i=1;
						for(EmployeeDetails empdetails : empList) {
							emp = new Employee();
							emp.setCreatedDate(empdetails.getAcctCreated_date());
							logger.info("Created Date --->"+emp.getCreatedDate());						   
							SimpleDateFormat month_date = new SimpleDateFormat("MMM");
							String month_name = month_date.format(emp.getCreatedDate());
							if(month_name.equalsIgnoreCase(selectedMonth)){
								logger.info("Month Name are Equal ::::" + month_name + "   ==   "+selectedMonth);
								if(reportName.equalsIgnoreCase("empManager")){
									query=null;
									query=entityManager.createQuery("from EmployeeDetails where (empRole='Clinic Manager' or empRole='Investment Manager') and acctCreated_date=?");
									//query.setParameter(1, "Manager");
									query.setParameter(1,emp.getCreatedDate());
									List<EmployeeDetails> empdet=(ArrayList<EmployeeDetails>)query.getResultList();
									logger.info("Employee Details List for sales Both Manager-------->"+empdet.size());
									for(int j=0;j<=empdet.size();j++){
							            for(int k=j+1;k<=empdet.size();k++){
							            	emp = new Employee();
											emp.setsNo(i);
											emp.setName(empdet.get(j).getEmployeeName()); 
											emp.setEmployeeCode(empdet.get(j).getEmployeeCode());
											emp.setAddress(empdet.get(j).getEmployeeAddress());
											emp.setEmailID(empdet.get(j).getEmployeeEmailID());
											emp.setPhoneNumber(empdet.get(j).getEmployeePhoneNumber());
											emp.setSalary(empdet.get(j).getEmpSalary());
											emp.setEmpType(empdet.get(j).getEmployeeType());
											emp.setSelectedCountry(empdet.get(j).getCountry());
											emp.setJoin_date(empdet.get(j).getJoin_date()); 
											emp.setPrimaryKey(empdet.get(j).getEmployee_Details_ID());
											query=entityManager.createQuery("from ClinicDetails where employee_Details_ID=?");
											query.setParameter(1, empdet.get(j).getEmployee_Details_ID()); 
											clinicList=(ArrayList<ClinicDetails>)query.getResultList();
											logger.info("Clinic List Size ----->"+clinicList.size()); 
											emp.setClinicCount(clinicList.size());
											
											totalclinics = totalclinics.add(new BigDecimal(emp.getClinicCount()));
											logger.info("Total Clinics --->"+totalclinics);
											emp.setTotalClincs(totalclinics); 
											
											query=entityManager.createQuery("from AgentDetails where refEmpCode=?");
											query.setParameter(1, empdet.get(j).getEmployeeCode()); 
											agList=(ArrayList<AgentDetails>)query.getResultList();
											logger.info("Agent List Size ----->"+agList.size()); 
											emp.setAgentCount(agList.size());
											
											totalagents = totalagents.add(new BigDecimal(emp.getAgentCount()));
											logger.info("Total Agents --->"+totalagents);
											emp.setTotalAgents(totalagents); 
											
											searchMonthlyEmployeeReport.add(emp);
							                if(empdet.get(j).getEmployee_Details_ID() == empdet.get(k).getEmployee_Details_ID()){
							                	System.out.println("-- Inside same value remove method --");
							                	searchMonthlyEmployeeReport.remove(k);
							                }
							                i++;
								            System.out.println("searchMonthlyEmployee Manger Report List size ----->"+searchMonthlyEmployeeReport.size());
							            }	
							           
							        }
								}else if(reportName.equalsIgnoreCase("empNon-Manager")){
									

									query=null;
									query=entityManager.createQuery("from EmployeeDetails where (empRole='Clinic Non-Manager' or empRole='Investment Non-Manager') and acctCreated_date=?");
									//query.setParameter(1, "Non-Manager");
									query.setParameter(1,emp.getCreatedDate());
									List<EmployeeDetails> empdet=(ArrayList<EmployeeDetails>)query.getResultList();
									logger.info("Employee Details List for sales Both Non-Manager -------->"+empdet.size());
									for(int j=0;j<=empdet.size();j++){
							            for(int k=j+1;k<=empdet.size();k++){
							            	emp = new Employee();
											emp.setsNo(i);
											emp.setName(empdet.get(j).getEmployeeName()); 
											emp.setEmployeeCode(empdet.get(j).getEmployeeCode());
											emp.setAddress(empdet.get(j).getEmployeeAddress());
											emp.setEmailID(empdet.get(j).getEmployeeEmailID());
											emp.setPhoneNumber(empdet.get(j).getEmployeePhoneNumber());
											emp.setSalary(empdet.get(j).getEmpSalary());
											emp.setEmpType(empdet.get(j).getEmployeeType());
											emp.setSelectedCountry(empdet.get(j).getCountry());
											emp.setJoin_date(empdet.get(j).getJoin_date()); 
											emp.setRefEmploy(empdet.get(j).getRefCode());
											emp.setPrimaryKey(empdet.get(j).getEmployee_Details_ID());
											query=entityManager.createQuery("from ClinicDetails where employee_Details_ID=?");
											query.setParameter(1, empdet.get(j).getEmployee_Details_ID()); 
											clinicList=(ArrayList<ClinicDetails>)query.getResultList();
											logger.info("Clinic List Size ----->"+clinicList.size()); 
											emp.setClinicCount(clinicList.size());
											
											totalclinics = totalclinics.add(new BigDecimal(emp.getClinicCount()));
											logger.info("Total Clinics --->"+totalclinics);
											emp.setTotalClincs(totalclinics); 
											
											query=entityManager.createQuery("from AgentDetails where refEmpCode=?");
											query.setParameter(1, empdet.get(j).getRefCode()); 
											agList=(ArrayList<AgentDetails>)query.getResultList();
											logger.info("Agent List Size ----->"+agList.size()); 
											emp.setAgentCount(agList.size());
											
											totalagents = totalagents.add(new BigDecimal(emp.getAgentCount()));
											logger.info("Total Agents --->"+totalagents);
											emp.setTotalAgents(totalagents); 
											
											searchMonthlyEmployeeReport.add(emp);
							                if(empdet.get(j).getEmployeeCode().equals(empdet.get(k).getEmployeeCode())){
							                	searchMonthlyEmployeeReport.remove(k);
							                }
								            System.out.println("searchMonthlyEmployee Non-Manager Report List size ----->"+searchMonthlyEmployeeReport.size());
							            }	
							            i++;
							        }
								}
							}else{
								logger.info("------- No Result for Both SelectedMonth and DatabaseMonth for EmployeeSale Report -----------");
							}
						}
						
					}else{
						
						String empCode = empName.substring(empName.lastIndexOf("-")+1);
						logger.info("Employee Code --------->"+empCode); 
						
						query=entityManager.createQuery("from EmployeeDetails");
						empList=(ArrayList<EmployeeDetails>)query.getResultList();
						logger.info("Employee Details List for sales else condition -------->"+empList.size());
						int i=1;
						for(EmployeeDetails empdetails : empList) {
							emp = new Employee();
							emp.setCreatedDate(empdetails.getAcctCreated_date());
							logger.info("Created Date --->"+emp.getCreatedDate());						   
							SimpleDateFormat month_date = new SimpleDateFormat("MMM");
							String month_name = month_date.format(emp.getCreatedDate());
							if(month_name.equalsIgnoreCase(selectedMonth)){
								logger.info("Month Name are Equal ::::" + month_name + "   ==   "+selectedMonth);
								if(reportName.equalsIgnoreCase("empManager")){
									query=null;
									query=entityManager.createQuery("from EmployeeDetails where (empRole='Clinic Manager' or empRole='Investment Manager') and empCode=? and acctCreated_date=?");
									//query.setParameter(1, "Manager");
									query.setParameter(1, empCode);
									query.setParameter(2,emp.getCreatedDate());
									List<EmployeeDetails> empdet=(ArrayList<EmployeeDetails>)query.getResultList();
									logger.info("Employee Manager Sales Report ---------->"+empdet.size());
									for(int j=0;j<=empdet.size();j++){
							            for(int k=j+1;k<=empdet.size();k++){
							            	emp = new Employee();
											emp.setsNo(i);
											emp.setName(empdet.get(j).getEmployeeName()); 
											emp.setEmployeeCode(empdet.get(j).getEmployeeCode());
											emp.setAddress(empdet.get(j).getEmployeeAddress());
											emp.setEmailID(empdet.get(j).getEmployeeEmailID());
											emp.setPhoneNumber(empdet.get(j).getEmployeePhoneNumber());
											emp.setSalary(empdet.get(j).getEmpSalary());
											emp.setEmpType(empdet.get(j).getEmployeeType());
											emp.setSelectedCountry(empdet.get(j).getCountry());
											emp.setJoin_date(empdet.get(j).getJoin_date()); 
											emp.setPrimaryKey(empdet.get(j).getEmployee_Details_ID());
											query=entityManager.createQuery("from ClinicDetails where employee_Details_ID=?");
											query.setParameter(1, empdet.get(j).getEmployee_Details_ID()); 
											clinicList=(ArrayList<ClinicDetails>)query.getResultList();
											logger.info("Clinic List Size ----->"+clinicList.size()); 
											emp.setClinicCount(clinicList.size());
											
											totalclinics = totalclinics.add(new BigDecimal(emp.getClinicCount()));
											logger.info("Total Clinics --->"+totalclinics);
											emp.setTotalClincs(totalclinics); 
											
											query=entityManager.createQuery("from AgentDetails where refEmpCode=?");
											query.setParameter(1, empdet.get(j).getEmployeeCode()); 
											agList=(ArrayList<AgentDetails>)query.getResultList();
											logger.info("Agent List Size ----->"+agList.size()); 
											emp.setAgentCount(agList.size());
											
											totalagents = totalagents.add(new BigDecimal(emp.getAgentCount()));
											logger.info("Total Agents --->"+totalagents);
											emp.setTotalAgents(totalagents); 
											
											searchMonthlyEmployeeReport.add(emp);
							                if(empdet.get(j).getEmployeeCode().equals(empdet.get(k).getEmployeeCode())){
							                	searchMonthlyEmployeeReport.remove(k);
							                }
								            System.out.println("search Monthly Employee Specific Manager Report List size ----->"+searchMonthlyEmployeeReport.size());
							            }	
							            i++;
							        }
								}else if(reportName.equalsIgnoreCase("empNon-Manager")){
									

									query=null;
									query=entityManager.createQuery("from EmployeeDetails where (empRole='Clinic Non-Manager' or empRole='Investment Non-Manager') and empCode=? and acctCreated_date=?");
									//query.setParameter(1, "Non-Manager");
									query.setParameter(1, empCode);
									query.setParameter(2,emp.getCreatedDate());
									List<EmployeeDetails> empdet=(ArrayList<EmployeeDetails>)query.getResultList();
									logger.info("Employee Non-Manager Sales Report ---------->"+empdet.size());
									for(int j=0;j<=empdet.size();j++){
							            for(int k=j+1;k<=empdet.size();k++){
							            	emp = new Employee();
											emp.setsNo(i);
											emp.setName(empdet.get(j).getEmployeeName()); 
											emp.setEmployeeCode(empdet.get(j).getEmployeeCode());
											emp.setAddress(empdet.get(j).getEmployeeAddress());
											emp.setEmailID(empdet.get(j).getEmployeeEmailID());
											emp.setPhoneNumber(empdet.get(j).getEmployeePhoneNumber());
											emp.setSalary(empdet.get(j).getEmpSalary());
											emp.setEmpType(empdet.get(j).getEmployeeType());
											emp.setSelectedCountry(empdet.get(j).getCountry());
											emp.setJoin_date(empdet.get(j).getJoin_date()); 
											emp.setRefEmploy(empdet.get(j).getRefCode());
											emp.setPrimaryKey(empdet.get(j).getEmployee_Details_ID());
											query=entityManager.createQuery("from ClinicDetails where employee_Details_ID=?");
											query.setParameter(1, empdet.get(j).getEmployee_Details_ID()); 
											clinicList=(ArrayList<ClinicDetails>)query.getResultList();
											logger.info("Clinic List Size ----->"+clinicList.size()); 
											emp.setClinicCount(clinicList.size());
											
											totalclinics = totalclinics.add(new BigDecimal(emp.getClinicCount()));
											logger.info("Total Clinics --->"+totalclinics);
											emp.setTotalClincs(totalclinics); 
											
											query=entityManager.createQuery("from AgentDetails where refEmpCode=?");
											query.setParameter(1, empdet.get(j).getRefCode()); 
											agList=(ArrayList<AgentDetails>)query.getResultList();
											logger.info("Agent List Size ----->"+agList.size()); 
											emp.setAgentCount(agList.size());
											
											totalagents = totalagents.add(new BigDecimal(emp.getAgentCount()));
											logger.info("Total Agents --->"+totalagents);
											emp.setTotalAgents(totalagents); 
											
											searchMonthlyEmployeeReport.add(emp);
							                if(empdet.get(j).getEmployeeCode().equals(empdet.get(k).getEmployeeCode())){
							                	searchMonthlyEmployeeReport.remove(k);
							                }
							                System.out.println("search Monthly Employee Specific Non-Manager Report List size ----->"+searchMonthlyEmployeeReport.size());
							            }
							            i++;
							        }
								}
							}else{
								logger.info("------- No Result for Both SelectedMonth and DatabaseMonth for EmployeeSale Report -----------");
							}
						}
						
					}
					
				}catch(Exception e){
					logger.info("[DAO] SalesEmployee Exception --------------------->"+e.getMessage());
				}finally{
					
				}
				return searchMonthlyEmployeeReport;
			}
			
			// ---------------------- load My ClinicDetails on Sales ClinicAgent -------------------------
			@Transactional(value="transactionManager")
			@SuppressWarnings (value="unchecked")
			public ArrayList<Clinic> getSalesMyClinicReport(ArrayList<Clinic> clinicReportList,Clinic clinic){
				Query query=null;
				List<ClinicDetails> clinicdet;
				logger.info("[DAO]Agent Code ---------------->"+clinic.getAgentCode());
				try {						
					query = entityManager.createQuery("from ClinicDetails where agent_Details_ID=?");
					query.setParameter(1, clinic.getPrimaryKey());
					clinicdet = (List<ClinicDetails>) query.getResultList();
					logger.info("Clinic Details List size -------->"+clinicdet.size());
					if (clinicdet.size() > 0) {
						int i=1;
						for(ClinicDetails cd :clinicdet){
							clinic = new Clinic();
							clinic.setsNo(i);
							clinic.setClinicCode(cd.getClinicCode());
							clinic.setClinicName(cd.getClinicName());
							clinic.setAddress(cd.getClinicAddress());
							clinic.setClinicPhoneNumber(cd.getClinicPhoneNumber());
							clinic.setCreatedDate(cd.getAcctCreated_date());
							clinic.setName(cd.getAgent_details().getAgentName());
							clinic.setAgentCode(cd.getAgent_details().getAgentCode());
							clinic.setEmployeeCode(cd.getEmployee_details().getEmployeeCode());	
							clinic.setEmpName(cd.getEmployee_details().getEmployeeName()); 
							if(cd.getClinicStatus().equalsIgnoreCase("not available")){
								clinic.setMemberNumber(cd.getUserdetails().getMemberID());
								clinic.setMemberName(cd.getUserdetails().getFirstname() + " " +cd.getUserdetails().getLastname());
							}else if(cd.getClinicStatus().equalsIgnoreCase("available")){
								clinic.setMemberNumber(" ");
								clinic.setMemberName(" ");
							}
							clinicReportList.add(clinic);
							i++;
						}
					}
					logger.info("clinicReport List Size -------->"+clinicReportList.size()); 
				}catch(Exception e) {
					logger.info("Exception ----------->"+e.getMessage());
				}finally{
					
				}		
				return clinicReportList;		
			}
			
			// ---------------------- load My EmployeeDetails on Sales ClinicAgent -------------------------
			@Transactional(value="transactionManager")
			@SuppressWarnings (value="unchecked")
			public ArrayList<Employee> getSalesMyEmployeeView(ArrayList<Employee> myEmpReport, String refEmploy){
				Query query=null;
				List<EmployeeDetails> empdet;
				Employee emp;
				logger.info("[DAO]Employee Code ---------------->"+refEmploy);
				try {						
					query = entityManager.createQuery("from EmployeeDetails where empCode=?");
					query.setParameter(1, refEmploy);
					empdet = (List<EmployeeDetails>) query.getResultList();
					logger.info("Employee Details List size ------>"+empdet.size());
					if (empdet.size() > 0) {
						int i=1;
						for(EmployeeDetails e :empdet){
							emp = new Employee();
							emp.setsNo(i);
							emp.setEmployeeCode(e.getEmployeeCode());
							emp.setName(e.getEmployeeName());
							emp.setAddress(e.getEmployeeAddress());
							emp.setEmailID(e.getEmployeeEmailID());
							emp.setCreatedDate(e.getAcctCreated_date());
							emp.setPhoneNumber(e.getEmployeePhoneNumber());
							emp.setEmpRole(e.getEmpRole()); 
							emp.setEmpType(e.getEmployeeType());
							emp.setSalary(e.getEmpSalary());
							emp.setRefEmploy(e.getRefCode());
							emp.setSelectedCountry(e.getCountry());
							emp.setJoin_date(e.getJoin_date());
							myEmpReport.add(emp);
							i++;
						}
					}
					logger.info("EmployeeReport List Size -------->"+myEmpReport.size()); 
				}catch(Exception e) {
					logger.info("Exception ----------->"+e.getMessage());
				}finally{
					
				}		
				return myEmpReport;		
			}
			
			// ------------------ view my profile ---------------------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public Member MyClinicNameView(Member member){
				Query query;
				List<ClinicDetails> clinicdet;
				try {
				 	logger.info("Primary Key ------------------>"+member.getUserLoginPrimaryKey()); 
					query = entityManager.createQuery("from ClinicDetails where user_Details_ID=?");
					query.setParameter(1, member.getUserLoginPrimaryKey());
					clinicdet = (List<ClinicDetails>) query.getResultList();
					logger.info("My Clinic Name List size ------>"+clinicdet.size());
					member.setClinicName(clinicdet.get(0).getClinicName());
					member.setClinicName2(clinicdet.get(1).getClinicName());
					member.setClinicName3(clinicdet.get(2).getClinicName());
					member.setClinicName4(clinicdet.get(3).getClinicName());
					member.setClinicName5(clinicdet.get(4).getClinicName());
					member.setClinicName6(clinicdet.get(5).getClinicName());
					member.setClinicName7(clinicdet.get(6).getClinicName());
					member.setClinicName8(clinicdet.get(7).getClinicName());
					member.setClinicName9(clinicdet.get(9).getClinicName()); 
					member.setClinicName10(clinicdet.get(9).getClinicName());
				}catch(Exception e){
					logger.info("Exception -->"+e.getMessage());
				} finally {
					
				}
				return member;
			}
			
			// ---------------------- load My ClinicDetails on Sales ClinicAgent -------------------------
			@Transactional(value="transactionManager")
			@SuppressWarnings (value="unchecked")
			public ArrayList<Clinic> getSalesMyEmpClinicReport(ArrayList<Clinic> myClinicReport, Clinic clinic){
				Query query=null;
				List<ClinicDetails> clinicdet;
				logger.info("[DAO]Employee Code ---------------->"+clinic.getEmployeeCode());
				try {						
					query = entityManager.createQuery("from ClinicDetails where employee_Details_ID=?");
					query.setParameter(1, clinic.getPrimaryKey());
					clinicdet = (List<ClinicDetails>) query.getResultList();
					logger.info("Clinic Details List size ------>"+clinicdet.size());
					if (clinicdet.size() > 0) {
						int i=1;
						for(ClinicDetails cd :clinicdet){
							clinic = new Clinic();
							clinic.setsNo(i);
							clinic.setClinicCode(cd.getClinicCode());
							clinic.setClinicName(cd.getClinicName());
							clinic.setAddress(cd.getClinicAddress());
							clinic.setClinicPhoneNumber(cd.getClinicPhoneNumber());
							clinic.setCreatedDate(cd.getAcctCreated_date());
							clinic.setName(cd.getAgent_details().getAgentName());
							clinic.setAgentCode(cd.getAgent_details().getAgentCode());
							clinic.setEmployeeCode(cd.getEmployee_details().getEmployeeCode());	
							clinic.setEmpName(cd.getEmployee_details().getEmployeeName()); 
							if(cd.getClinicStatus().equalsIgnoreCase("not available")){
								clinic.setMemberNumber(cd.getUserdetails().getMemberID());
								clinic.setMemberName(cd.getUserdetails().getFirstname() + " " +cd.getUserdetails().getLastname());
							}else if(cd.getClinicStatus().equalsIgnoreCase("available")){
								clinic.setMemberNumber(" ");
								clinic.setMemberName(" ");
							}
							myClinicReport.add(clinic);
							i++;
						}
					}
					logger.info("EmpclinicReport List Size -------->"+myClinicReport.size()); 
				}catch(Exception e) {
					logger.info("Exception ----------->"+e.getMessage());
				}finally{
					
				}		
				return myClinicReport;		
			}
			
			//---------------- Get EmpAgentDetails in Sales Report -------------
			@Transactional(value="transactionManager")
			@SuppressWarnings (value="unchecked")
			public ArrayList<Agent> getSalesMyEmpAgentReport(ArrayList<Agent> myAgentReport, Agent agent){
				Query query=null;
				List<AgentDetails> agdet;
				logger.info("[DAO]Employee Code ---------------->"+agent.getRefEmploy());
				try {						
					query = entityManager.createQuery("from AgentDetails where refEmpCode=?");
					query.setParameter(1, agent.getRefEmploy());
					agdet = (List<AgentDetails>) query.getResultList();
					logger.info("Agent Details List size ------>"+agdet.size());
					if (agdet.size() > 0) {
						int i=1;
						for(AgentDetails agdetails :agdet){
							agent = new Agent();
							agent.setsNo(i);
							agent.setName(agdetails.getAgentName());
							agent.setAgentCode(agdetails.getAgentCode());
							agent.setSelectedCountry(agdetails.getCountry());
							agent.setPhoneNumber(agdetails.getAgentPhoneNumber());
							agent.setEmailID(agdetails.getAgentEmailID());
							agent.setBankName(agdetails.getAgentBankName());
							agent.setBankBranchName(agdetails.getAgentBankBranchName());
							agent.setAccountNumber(agdetails.getAgentAccountNumber());
							agent.setAddress(agdetails.getAgentAddress());
							agent.setPrimaryKey(agdetails.getAgent_Details_ID()); 
							myAgentReport.add(agent);
							i++;
						}
					}
					logger.info("EmpAgentReport List Size -------->"+myAgentReport.size()); 
				}catch(Exception e) {
					logger.info("Exception ----------->"+e.getMessage());
				}finally{
					
				}		
				return myAgentReport;	
			}
			
			// ---------- Edit Clinic Name List --------------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public ArrayList<Clinic> getEditClinicName(ArrayList<Clinic> clinicList,String trioNumber){
				Query query=null;
			    Clinic clinic;
				try {					   
					logger.info("[DAO Clinic]Before Edit Clinic Name List ------------->");
					//------------ Same clinicName ------
					query=entityManager.createQuery("from UserDetail where memberID=?");
					query.setParameter(1, trioNumber);
					//List<UserDetail> userList = (ArrayList<UserDetail>)query.getResultList();
					UserDetail userList=(UserDetail)query.getSingleResult();	
					logger.info("UserDetails ID --------->"+userList.getUser_Details_ID());   
					
					query=entityManager.createQuery("from ClinicDetails where user_Details_ID=?");
					query.setParameter(1, userList.getUser_Details_ID());
					List<ClinicDetails> userclinicList = (ArrayList<ClinicDetails>)query.getResultList();
					logger.info("User Clinic List --->"+userclinicList.size());
					if(userclinicList.size() > 0) {
					   for(ClinicDetails clinicdetails : userclinicList) {  
						   clinic = new Clinic();    
						   logger.info("Clinic Name --->"+clinicdetails.getClinicName());
						   clinic.setClinicName(clinicdetails.getClinicName());
						   clinic.setClinicCode(clinicdetails.getClinicCode());
						   clinic.setPrimaryKey(clinicdetails.getClinic_Details_ID());
						   clinicList.add(clinic);
					   }
					   
					}else {
					   clinic = new Clinic();
					   clinic.setClinicName("No Clinic");
					   clinicList.add(clinic);
					   logger.info("----------- Empty Clinic List -----------");
					}
					
					//------------ Edit Other Clini Names -----------
					query=entityManager.createQuery("from ClinicDetails where clinicStatus=?");
					query.setParameter(1, "available");
					List<ClinicDetails> cliList = (ArrayList<ClinicDetails>)query.getResultList();
					logger.info("clinic List --->"+cliList.size());
					if(cliList.size() > 0) {
					   for(ClinicDetails clinicdet : cliList) {
						   clinic = new Clinic();
						   logger.info("Clinic Name --->"+clinicdet.getClinicName());
						   clinic.setClinicName(clinicdet.getClinicName());
						   clinic.setClinicCode(clinicdet.getClinicCode());
						   clinic.setPrimaryKey(clinicdet.getClinic_Details_ID());
						   clinicList.add(clinic);   
					   }
					   
					}else {
					   /*clinic = new Clinic();
					   clinic.setClinicName("No Clinic");
					   clinicList.add(clinic);*/
					   logger.info("----------- Empty Clinic List -----------");
					}		 
			 
					return clinicList;
				}catch(Exception e) {
					logger.info("Exception -->"+e.getMessage());
				}finally {
					
				}
				return clinicList;
			}

			
			// ------------- Add Clinic -------------- 
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked") 
			public Member setPartnerClinicUpdate(Member member){
				Query query = null;			
				logger.info("----------- Inside setPartnerClinicUpdate() DAO ----------");
				try{
				    UserDetail userdetails=new UserDetail();
					logger.info("[DAO] MemberID ---------->"+member.getMemberID());
					query=entityManager.createQuery("from UserDetail where memberID=?");					
					query.setParameter(1, member.getMemberID());					
					UserDetail user = (UserDetail)query.getSingleResult();
					userdetails = entityManager.find(UserDetail.class,user.getUser_Details_ID());
					logger.info("[DAO] Clinic Status ---------->"+userdetails.getClinic_status());
					logger.info("[DAO] Clinic Created Date ---------->"+userdetails.getAdd_clinic_date());
					ClinicDetails child=null;
					
					query=entityManager.createQuery("from ClinicDetails where user_Details_ID=?");					
					query.setParameter(1, user.getUser_Details_ID());
					List<ClinicDetails> userclinicList = (ArrayList<ClinicDetails>)query.getResultList();
					logger.info("User Clinic List --->"+userclinicList.size());
					   for(ClinicDetails clinicdetails : userclinicList) {  
						   logger.info("Clinic Deatils ID --->"+clinicdetails.getClinic_Details_ID());
						   	child = new ClinicDetails();
							child = entityManager.find(ClinicDetails.class,clinicdetails.getClinic_Details_ID());
							child.setClinicStatus("available"); 
							child.setUserdetails(null); 
						    entityManager.merge(child);	   
						    entityManager.flush();
						    entityManager.clear();   
					   }
					
					userdetails.setClinic_status("Registered");				
					for(int i =0;i<member.getNoofclinics();i++) {    
						logger.info("[DAO] addClinic Clinic PK ------------->"+member.clinicPKs[i]);
						child = new ClinicDetails();
						child = entityManager.find(ClinicDetails.class,member.clinicPKs[i]);
						child.setClinicStatus("not available");  
						child.setUserdetails(userdetails);
					    entityManager.merge(child);	
					    entityManager.flush();
					    entityManager.clear();
					}
					
					member.setStatus("success");
				    logger.info("Successfully saved data");		
				}catch(Exception e){
						member.setStatus("failure");
						logger.info("Exception -->"+e.getMessage());
				}finally{
					
				}
				return member;
			}
			
			//--------[DAO] get PartnerName in a list -------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public ArrayList<Member> getPartnerName(ArrayList<Member> parList){
				Query query;
				String Partner_NAME_QUERY = "SELECT ud.user_Details_ID, ud.firstname, ud.lastname,ud.memberID FROM user_details ud where NOT memberID='TRIO681111' and NOT memberID='TRIO581111' and NOT memberID='TRIO481111'";
			    Member member;
				try {
					query= entityManager.createNativeQuery(Partner_NAME_QUERY);
					List<Object[]> list = query.getResultList();
					if(list.size() >0 ) {
						for (Object[] a : list) {
						    logger.info("ID ----  "
						            + a[0]
						            + " Name --- >"
						            + a[1]);
						    member = new Member();
						    member.setUserLoginPrimaryKey((Integer)a[0]);
						    member.setFirstName((String)a[1] + (String)a[2]);
						    member.setMemberID((String)a[3]);
							parList.add(member);
						}

					}else {
						member = new Member();
						member.setUserLoginPrimaryKey(0);
						member.setFirstName("No Partner");
						parList.add(member);
					}
					
				}catch(Exception e) {
					logger.info("Exception -->"+e.getMessage());
					member = new Member();
					member.setFirstName("No Partner");
				}finally {
					
				}
					return parList;
				}
			
			//---------- Search All Partner -----------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public ArrayList<GLGMem> searchAllPartner(ArrayList<GLGMem> searchpartnerList, GLGMem glgmember, String customQuery){
				Query query=null;
				List<UserDetail> resultList;
				try {	
					query=entityManager.createQuery(customQuery);
					resultList=(ArrayList<UserDetail>)query.getResultList();
					logger.info("Result List ------->"+resultList.size());
					int i=1;
					for(UserDetail userdetails : resultList){
						glgmember = new GLGMem();
						glgmember.setsNo(i);
						glgmember.setMemberStatus(userdetails.getUserLogin().getStatus());
						glgmember.setUserLoginPrimaryKey(userdetails.getUserLogin().getUser_Login_ID());						
						glgmember.setMemberID(userdetails.getMemberID());
						glgmember.setMemberName(userdetails.getFirstname()+"  "+userdetails.getLastname());
						glgmember.setMemberEmail(userdetails.getEmail1());
						glgmember.setMemberPhone(userdetails.getPhonenumber1());
						glgmember.setMemberType(userdetails.getAcctType());
						glgmember.setTotalAmount(userdetails.getTotalFees());
						glgmember.setNoofclinics(userdetails.getNoofclinics()); 
						glgmember.setPaymentStatus(userdetails.getPayStatus());
						glgmember.setStatus(userdetails.getClinic_status()); 
						searchpartnerList.add(glgmember);
						i++;
					}
					logger.info("Search Partner List --------->"+searchpartnerList.size()); 
				}catch(Exception e){
					e.printStackTrace();
					logger.info("[DAO]SearchAllPartner Exception -------->"+e.getMessage()); 
				}finally{
					
				}
				return searchpartnerList;
			}
			
			//------------- Search Not Registered Clinic -------------
			@Transactional(value="transactionManager")
			@SuppressWarnings("unchecked")
			public ArrayList<GLGMem> searchPartnerNonClinic(ArrayList<GLGMem> searchPartnerNonClinic, GLGMem glgmember){
				Query query=null;
				List<UserDetail> resultList;
				List<UserLogin> List;
				int i=1;
				try {	
					query=entityManager.createQuery("from UserLogin where status=? and  userRole=? order by user_Login_ID desc");
					query.setParameter(1, "Approved");
					query.setParameter(2, "member");
					List=(ArrayList<UserLogin>)query.getResultList();	
					logger.info("Total Member Size ------------->"+List.size());
					if(List.size()>0){
						if(glgmember.getAgentCode().equalsIgnoreCase("All")){
							query=entityManager.createQuery("from UserDetail where clinic_status=? and acctCreated_date between ? and ? order by acctCreated_date desc");
							query.setParameter(1, "Not Registered");
							query.setParameter(2, glgmember.getFromDate());
							query.setParameter(3, glgmember.getToDate());
							resultList=(ArrayList<UserDetail>)query.getResultList();
							System.out.println("Not Registered Clinic List Size on Agent All ---->"+resultList.size());
							for(UserDetail user :resultList){
								glgmember = new GLGMem();
								logger.info("Clinic Status ------>"+user.getClinic_status());
								glgmember.setMemberStatus(user.getUserLogin().getStatus());
								glgmember.setUserLoginPrimaryKey(user.getUserLogin().getUser_Login_ID());
								glgmember.setMemberID(user.getMemberID());
								glgmember.setMemberName(user.getFirstname()+"  "+user.getLastname());
								glgmember.setMemberEmail(user.getEmail1());
								glgmember.setMemberPhone(user.getPhonenumber1());
								glgmember.setMemberType(user.getAcctType());
								glgmember.setTotalAmount(user.getTotalFees());
								glgmember.setNoofclinics(user.getNoofclinics()); 
								glgmember.setPaymentStatus(user.getPayStatus());
								glgmember.setStatus(user.getClinic_status()); 						
								searchPartnerNonClinic.add(glgmember);
								i++;
							}
						}else{
							query=entityManager.createQuery("from UserDetail where clinic_status=? and agent_code=? and acctCreated_date between ? and ? order by acctCreated_date desc");
							query.setParameter(1, "Not Registered");
							query.setParameter(2, glgmember.getAgentCode());
							query.setParameter(3, glgmember.getFromDate());
							query.setParameter(4, glgmember.getToDate());
							resultList=(ArrayList<UserDetail>)query.getResultList();
							System.out.println("Not Registered Clinic List Size on Agent Not All ---->"+resultList.size());
							for(UserDetail user :resultList){
								glgmember = new GLGMem();
								logger.info("Clinic Status ------>"+user.getClinic_status());
								glgmember.setMemberStatus(user.getUserLogin().getStatus());
								glgmember.setUserLoginPrimaryKey(user.getUserLogin().getUser_Login_ID());
								glgmember.setMemberID(user.getMemberID());
								glgmember.setMemberName(user.getFirstname()+"  "+user.getLastname());
								glgmember.setMemberEmail(user.getEmail1());
								glgmember.setMemberPhone(user.getPhonenumber1());
								glgmember.setMemberType(user.getAcctType());
								glgmember.setTotalAmount(user.getTotalFees());
								glgmember.setNoofclinics(user.getNoofclinics()); 
								glgmember.setPaymentStatus(user.getPayStatus());
								glgmember.setStatus(user.getClinic_status()); 						
								searchPartnerNonClinic.add(glgmember);
								i++;
							}
						}
					}else{
						logger.info("----------------- No Approved Member Data ---------------");
					}				
					logger.info("Search Partner List --------->"+searchPartnerNonClinic.size()); 
				}catch(Exception e){
					e.printStackTrace();
					logger.info("[DAO]Search Not Registered Clinic Exception -------->"+e.getMessage()); 
				}finally{
					
				}
				return searchPartnerNonClinic;
			}
			
}


package com.trio.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trio.dto.Agent;
import com.trio.dto.Clinic;
import com.trio.dto.Employee;
import com.trio.dto.GLGMem;
import com.trio.dto.Member;
import com.trio.dto.User;
import com.trio.model.ClinicDetails;
//import com.trio.model.CommOverrDetail;
import com.trio.model.CountryDetail;
import com.trio.model.IndustryDetail;
//import com.trio.model.MemberId;
import com.trio.model.UserLogin;
//import com.trio.util.Email;

public interface TrioDao {
	public static final Logger logger = LoggerFactory.getLogger(TrioDao.class);

	// File upload GGL Number validation check 
	public String gglNumberCheck(String gglNumber);
	 public Member UpdatePayment(Member member);
	public List<UserLogin> userLogin(User user,List<UserLogin> result);
	public Member userExistingCheck(String requestType,Member member);
	public ArrayList<CountryDetail> getCountry();
	// get country and state
	public ArrayList<IndustryDetail> getState (String country,ArrayList<IndustryDetail> industrylist);
	//public String getMemberCode(String memberCode);
	public int getRandamCode(int newCode,String requestType);	
	//public ArrayList<MemberId> getGroupData(ArrayList<MemberId> memberID,int groupName);
	//public ArrayList<MemberId> getFiltredData(ArrayList<MemberId> memberID,int groupName,int fromNumber,int ToNumber,String treeName);
	// ------------ load All TRIO Member details ------------------------
	public ArrayList<GLGMem> getAllMemberList(String requestType,ArrayList<GLGMem> myMemList);
	public User getApproved(User user,int userLoginPrimaryKey,String requestType);
	//------------------ view my profile ----------------------------------------
	public Member getMyProfile(Member member);
	// ---------------- forget Password use check ------------------------------
	public User Checkuser(User user);
	// ---------------- OTP Validate ------------------------------
	public User OtpCheck(User user);
	// ---------------- resetPassword ------------------------------
	public User resetPassword(User user);
	// Approve withdraw
	public User getApprovewithdraw(User user);
	// ---------------- Load Hotel Name ------------------------------
	public ArrayList<String> getName(ArrayList<String> namelist,Member member);
	//---- update my profile -------
	public Member updateMyProfile(Member member);
	//------- submit withdrawal amount ----------------
	public Member submitWith(Member member);
	public Member addClinic(Member member);
	public Member openWithdraw(Member member);
	public Member createMember(Member member);	
	//Ledger Info Details
	public ArrayList<Member> getLedgerInfo(Member member,ArrayList<Member> mlist);  
	// ----------- Add clinic list ------------
	public ArrayList<GLGMem> getAllClinicList(String requestType, ArrayList<GLGMem> cliniclist);
	public ArrayList<ClinicDetails> getAllClinicList2(ArrayList<ClinicDetails> list,String requestType);
	// Unique Code Generation
	public int getUniqueCode(int newCode,String requestType);
	// Load Agent data
	public ArrayList<Agent> getAgentInfo(ArrayList<Agent> agentList);
	// Save Employee
	public String savePerson(Agent agent);
	// Save Agent 
	public String savePerson(Employee emp);
	// Agent Name List 
	public ArrayList<Agent> getAgentName(ArrayList<Agent> agentList);
	// Employee Name list 
	public ArrayList<Employee> getEmpName(ArrayList<Employee> empList, String empRole);
	// Update Agent Data
	public String setAgentUpdate(Agent agent);
	// Remove Agent data
	public String setAgentRemove(int agPK);
	// Load Employee data
	public ArrayList<Employee> getEmployeeInfo(ArrayList<Employee> employeeList);	
	// Update Employee Data
	public String setEmployeeUpdate(Employee emp);
	// Remove Employee data
	public String setEmployeeRemove(int empPK);
	// Save Clinic data
	public String saveClinic(Clinic clinic);
	// Clinic Name Exist Check
	public String clinicExistCheck(String clinicName);
	// Agent CodeExist Check
	public String agentCodeExistCheck(String agentCode);	
	//----------- getclinicName List ---------
	public ArrayList<Clinic> getClinicName(ArrayList<Clinic> clinicNameList);	
	// Update Clinic Data
	public String setClinicUpdate(Clinic clinic);
	// Remove Clinic data
	public String setClinicRemove(int clinicPK);
	//---------- getAgentProfile -----
	public Agent getAgentProfile(Agent agent);
	//--------- getClinicAgentReport view ---
	public ArrayList<Clinic> getClinicAgentReport(String agentCode, ArrayList<Clinic> clinicReportList);
	//----- getInvAgentReport view ---
	public ArrayList<Member> getInvAgentReport(String agentCode, ArrayList<Member> invreportlist);
	//--------- getAllClinicReportList view for Admin Login ---
	public ArrayList<Clinic> getAllClinicReportList(Clinic clinic,ArrayList<Clinic> allClinicReportList);
	// Investment Agent Name List 
	public ArrayList<Agent> getinvAgentName(ArrayList<Agent> invagentList);
	//--------- getAllInvAgentReport view for Admin Login ---
	public ArrayList<Member> getAllInvAgentReport(Member member,ArrayList<Member> invReportList);	
	public ArrayList<Clinic> getAllMemberClinicReportList(Clinic clinic,ArrayList<Clinic> memberClinicReportList);
	public ArrayList<Member> getAllMemberReportList(Member member,ArrayList<Member> memberReportList, String status); 
	public ArrayList<Member> getAllPaymentReportList(Member member,ArrayList<Member> memberReportList); 
	public ArrayList<Employee> getAllEmpReportList(Employee emp,ArrayList<Employee> empReportList);
	public Agent updateAgentProfile(Agent agent);
	public Employee getEmployeeProfile(Employee emp);
	public Employee updateEmpProfile(Employee emp); 
	public ArrayList<Employee> getEmpReport(String employeeCode,ArrayList<Employee> empreportlist); 
	public ArrayList<Agent> searchAgent(ArrayList<Agent> searchagList,Agent agent,String customQuery);
	public ArrayList<Employee> searchEmployee(ArrayList<Employee> searchempList, Employee emp,String customQuery);
	public ArrayList<Clinic> searchClinic(ArrayList<Clinic> searchclinicList,Clinic clinic); 
	public ArrayList<GLGMem> searchPartnerClinic(ArrayList<GLGMem> searchPartnerClinic, Clinic clinic);
	public ArrayList<Agent> getSalesClincReport(ArrayList<Agent> searchMonthlyClinicReport,String selectedMonth,String reportName, String name); 
	public ArrayList<Member> getSalesPartnerReport(ArrayList<Member> searchMonthlyPartnerReport, String selectedMonth, String memberName);
	public ArrayList<Employee> getSalesEmployeeReport(ArrayList<Employee> searchMonthlyEmployeeReport,String selectedMonth,String reportName, String empName);
	public ArrayList<Clinic> getSalesMyClinicReport(ArrayList<Clinic> myClinicReport, Clinic clinic); 
	public ArrayList<Employee> getSalesMyEmployeeView(ArrayList<Employee> myEmpReport, String refEmploy); 
	public Member MyClinicNameView(Member member); 
	public ArrayList<Clinic> getSalesMyEmpClinicReport(ArrayList<Clinic> myClinicReport, Clinic clinic); 
	public ArrayList<Agent> getSalesMyEmpAgentReport(ArrayList<Agent> myAgentReport, Agent agent);
	//---------------- Edit ClinicList ------------------  
	public ArrayList<Clinic> getEditClinicName(ArrayList<Clinic> clinicList,String trioNumber); 
	public Member setPartnerClinicUpdate(Member member);   
	//----------------- get Partner Name ----------
	public ArrayList<Member> getPartnerName(ArrayList<Member> parList);
	//-------- Search All Partner ----------
	public ArrayList<GLGMem> searchAllPartner(ArrayList<GLGMem> searchpartnerList, GLGMem glgmember, String customQuery);
	public ArrayList<GLGMem> searchPartnerNonClinic(ArrayList<GLGMem> searchPartnerNonClinic, GLGMem glgmember);
}

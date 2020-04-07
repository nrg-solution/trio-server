package com.trio.bo;

import java.util.ArrayList;
import java.util.HashMap;

import com.trio.dto.Agent;
import com.trio.dto.Clinic;
import com.trio.dto.Employee;
import com.trio.dto.GLGMem;
import com.trio.dto.Member;
import com.trio.dto.User;
import com.trio.util.TrioException;

public interface TrioBo {
	// ---------------- TRIO Number Validate ------------------------------
	public String gglNumberCheck(String gglNumber);
	// ---------------- Update Payment ------------------------------
	public boolean UpdatePayment(String gglNumber);
	
	// ---------------- user login ------------------------------
	public User userLogin(User user);
	//----------------- Create TRIO member ---------------------------
	public Member createMember(Member member) throws TrioException;
	//---------------- load the country list --------------------
	public HashMap<String,String> getCountry(HashMap<String,String> statemap);
	//--------------- Get only My member list -------------------
	//public ArrayList<GLGMem> getMyMemberList(String memberNumber, ArrayList<GLGMem> myMemList);
	//------------ load All GLG Member details ------------------
	public ArrayList<GLGMem> getAllMemberList(String requestType,ArrayList<GLGMem> myMemList);
	//------------ Admin approve --------------------------------
	public User getApproved(User user,int userLoginPrimaryKey,String requestType);
	// ---------------- get My Profile ------------------------------
	public Member getMyProfile(Member member);
	//public ArrayList<GLGMem> getMyCommandOverInfo(String primaryKeyStr,ArrayList<GLGMem> glgmember);
	// ---------------- forget Password use check ------------------------------
	public User Checkuser(User user,int temp);
	// ---------------- Load Hotel Name ------------------------------
	//public ArrayList<String> getName(ArrayList<String> namelist,Member member);
	// get Hotel list 
	//--------update My Profile ------
	public Member updateMyProfile(Member member);
	//submit withdrawal amount
	public Member submitWith(Member member);
		
	// Ledger Info 
	public ArrayList<Member> getLedgerInfo(Member member,ArrayList<Member> mlist);
	//---------- Add Clinic -----------
	public Member addClinic(Member member);
	public Member openWithdraw(Member member);
	// ---------- All Clinic list -------
	public ArrayList<GLGMem> getAllClinicList(String requestType, ArrayList<GLGMem> cliniclist);
	// Clinic List view
	public ArrayList<Clinic> getAllClinicList2(ArrayList<Clinic> list,String requestType);

	public User getApprovewithdraw(User user);
	public Member editClinic(Member member); 
	
	// Load Agent data
	public ArrayList<Agent> getAgentInfo(ArrayList<Agent> agentList);
	
	public String savePerson(Employee emp);
	public String savePerson(Agent agent);
	
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
	// Agent Code Exist Check
	public String agentCodeExistCheck(String agentCode);

	//----------- getclinicName List ---------
	public ArrayList<Clinic> getClinicName(ArrayList<Clinic> clinicNameList);
	
	// Update Clinic Data
	public String setClinicUpdate(Clinic clinic);
	// Remove Clinic data
	public String setClinicRemove(int clinicPK);
	
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
	public ArrayList<Member> getAllMemberReportList(Member member,ArrayList<Member> memberReportList, String requestType, String status); 
	public Agent updateAgentProfile(Agent agent);	
	public Employee getEmployeeProfile(Employee emp);
	public Employee updateEmpProfile(Employee emp);

	public ArrayList<Employee> getAllEmpReportList(Employee emp,ArrayList<Employee> empReportList);
	public ArrayList<Employee> getEmpReport(String employeeCode,ArrayList<Employee> empreportlist);
	public ArrayList<Employee> searchEmployee(ArrayList<Employee> searchempList, Employee emp);
	public ArrayList<Employee> getSalesEmployeeReport(ArrayList<Employee> searchMonthlyEmployeeReport,String selectedMonth,String reportName, String empName);
	public ArrayList<Employee> getSalesMyEmployeeView(ArrayList<Employee> myEmpReport, String refEmploy);

	public ArrayList<Agent> searchAgent(ArrayList<Agent> searchagList,Agent agent);
	
	public ArrayList<Clinic> searchClinic(ArrayList<Clinic> searchclinicList,Clinic clinic);
	public ArrayList<GLGMem> searchPartnerClinic(ArrayList<GLGMem> searchPartnerClinic, Clinic clinic);
	public ArrayList<Agent> getSalesClincReport(ArrayList<Agent> searchMonthlyClinicReport,String selectedMonth,String reportName, String name);
	public ArrayList<Member> getSalesPartnerReport(ArrayList<Member> searchMonthlyPartnerReport, String selectedMonth, String memberName);
	public ArrayList<Clinic> getSalesMyClinicReport(ArrayList<Clinic> myClinicReport, Clinic clinic);
	public Member MyClinicNameView(Member member);
	public ArrayList<Clinic> getSalesMyEmpClinicReport(ArrayList<Clinic> myClinicReport, Clinic clinic);
	public ArrayList<Agent> getSalesMyEmpAgentReport(ArrayList<Agent> myAgentReport, Agent agent);
	//---------------- Edit ClinicList ------------------
	public ArrayList<Clinic> getEditClinicName(ArrayList<Clinic> clinicList,String trioNumber);
	public Member setPartnerClinicUpdate(Member member);   
	//----------------- get Partner Name ----------
	public ArrayList<Member> getPartnerName(ArrayList<Member> parList);
	public ArrayList<GLGMem> searchAllPartner(ArrayList<GLGMem> searchpartnerList, GLGMem member);
	public ArrayList<GLGMem> searchPartnerNonClinic(ArrayList<GLGMem> searchPartnerNonClinic, GLGMem glgmember);
	
	
}
  

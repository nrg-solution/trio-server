package com.trio.dto;

import java.math.BigDecimal;
import java.util.Date;

//import org.springframework.web.multipart.MultipartFile;

public class Member extends Common {
	
	String firstName;
	String lastName;
	String selectedCountry;
	String selectedState;
	String categoryname;
	String cname;
	String phoneNumber;
	String emailID;
	// String status; added into COmmon Class
	// existing member ID
	String agentCode;
	String refmemberID;
	String bankName;//string;
	String  bankAcctNumber;//string;
	String actType; 
	int payAmt;
	int adminFees;
	int totalFees;
	Date bookingdate;

	// new member fild
	int commition;
	int overriding;	
	// referance1 member update filed
	int member1_primaryKey;
	int ref_commition1;
	int ref_ovrriding1;
	String Member_refer_Number1;	
	// referance2 member update filed
	int member2_primaryKey;
	int ref_commition2;
	int ref_ovrriding2;
	String Member_refer_Number2;	
	// referance 3 
	int member3_primaryKey;
	int ref_commition3;
	int ref_ovrriding3;	
	// addition	
	//sequance number and group name
	int sequanceNumber;
	int groupName;
	int leveNumber;
	String treeName;
	String bookingStatus;
	String invoiceNumber;
	
	
	//newly added
	
	int totalAmount;
	int withdrawamt;

	int noofclinics;	
	String paymentpath;
	String clinicName;
	Date investmentDate;
	Date enrollmentDate;
	int withdrawAmount;
	String percentage;
	String wstatus;
	String bclass;
	String clinicName2;
	String clinicName3;
	String clinicName4;
	String clinicName5;
	String clinicName6;
	String clinicName7;
	String clinicName8;
	String clinicName9;
	String clinicName10;
	
	BigDecimal investmentAmount;
	
	//public int[] clinicPKs = new int[10];
	public int[] clinicPKs;// = new int[10];

	
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public Date getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}
	public String getBclass() {
		return bclass;
	}
	public void setBclass(String bclass) {
		this.bclass = bclass;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getPaymentpath() {
		return paymentpath;
	}
	public void setPaymentpath(String paymentpath) {
		this.paymentpath = paymentpath;
	}
	public int getNoofclinics() {
		return noofclinics;
	}
	public void setNoofclinics(int noofclinics) {
		this.noofclinics = noofclinics;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getWithdrawamt() {
		return withdrawamt;
	}
	public void setWithdrawamt(int withdrawamt) {
		this.withdrawamt = withdrawamt;
	}
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public int getLeveNumber() {
		return leveNumber;
	}
	public void setLeveNumber(int leveNumber) {
		this.leveNumber = leveNumber;
	}
	public String getTreeName() {
		return treeName;
	}
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
	/*public String getUserloginPrimaryKeyString() {
		return userloginPrimaryKeyString;
	}
	public void setUserloginPrimaryKeyString(String userloginPrimaryKeyString) {
		this.userloginPrimaryKeyString = userloginPrimaryKeyString;
	}*/
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getSelectedState() {
		return selectedState;
	}
	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}
	/*public String getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}*/
	public int getRef_commition3() {
		return ref_commition3;
	}
	public void setRef_commition3(int ref_commition3) {
		this.ref_commition3 = ref_commition3;
	}
	public int getRef_ovrriding3() {
		return ref_ovrriding3;
	}
	public void setRef_ovrriding3(int ref_ovrriding3) {
		this.ref_ovrriding3 = ref_ovrriding3;
	}
	public int getMember3_primaryKey() {
		return member3_primaryKey;
	}
	public void setMember3_primaryKey(int member3_primaryKey) {
		this.member3_primaryKey = member3_primaryKey;
	}
	public int getMember1_primaryKey() {
		return member1_primaryKey;
	}
	public void setMember1_primaryKey(int member1_primaryKey) {
		this.member1_primaryKey = member1_primaryKey;
	}
	public int getMember2_primaryKey() {
		return member2_primaryKey;
	}
	public void setMember2_primaryKey(int member2_primaryKey) {
		this.member2_primaryKey = member2_primaryKey;
	}
	public int getSequanceNumber() {
		return sequanceNumber;
	}
	public void setSequanceNumber(int sequanceNumber) {
		this.sequanceNumber = sequanceNumber;
	}
	public int getGroupName() {
		return groupName;
	}
	public void setGroupName(int groupName) {
		this.groupName = groupName;
	}
	public String getMember_refer_Number1() {
		return Member_refer_Number1;
	}
	public void setMember_refer_Number1(String member_refer_Number1) {
		Member_refer_Number1 = member_refer_Number1;
	}
	public String getMember_refer_Number2() {
		return Member_refer_Number2;
	}
	public void setMember_refer_Number2(String member_refer_Number2) {
		Member_refer_Number2 = member_refer_Number2;
	}
	/*public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}*/
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/*public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}*/
	/*public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}*/
	public String getCountry() {
		return selectedCountry;
	}
	public void setCountry(String country) {
		this.selectedCountry = country;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getRefmemberID() {
		return refmemberID;
	}
	public void setRefmemberID(String refmemberID) {
		this.refmemberID = refmemberID;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAcctNumber() {
		return bankAcctNumber;
	}
	public void setBankAcctNumber(String bankAcctNumber) {
		this.bankAcctNumber = bankAcctNumber;
	}
	public String getSelectedCountry() {
		return selectedCountry;
	}
	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	public String getActType() {
		return actType;
	}
	public void setActType(String actType) {
		this.actType = actType;
	}
	/*public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}*/
	public int getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(int payAmt) {
		this.payAmt = payAmt;
	}
	
	
	public int getCommition() {
		return commition;
	}
	public void setCommition(int commition) {
		this.commition = commition;
	}
	public int getOverriding() {
		return overriding;
	}
	public void setOverriding(int overriding) {
		this.overriding = overriding;
	}
	
	
	
	public int getRef_commition1() {
		return ref_commition1;
	}
	public void setRef_commition1(int ref_commition1) {
		this.ref_commition1 = ref_commition1;
	}
	public int getRef_ovrriding1() {
		return ref_ovrriding1;
	}
	public void setRef_ovrriding1(int ref_ovrriding1) {
		this.ref_ovrriding1 = ref_ovrriding1;
	}
	public int getRef_commition2() {
		return ref_commition2;
	}
	public void setRef_commition2(int ref_commition2) {
		this.ref_commition2 = ref_commition2;
	}
	public int getRef_ovrriding2() {
		return ref_ovrriding2;
	}
	public void setRef_ovrriding2(int ref_ovrriding2) {
		this.ref_ovrriding2 = ref_ovrriding2;
	}
	/*public int getUserLoginPrimaryKey() {
		return userLoginPrimaryKey;
	}
	public void setUserLoginPrimaryKey(int userLoginPrimaryKey) {
		this.userLoginPrimaryKey = userLoginPrimaryKey;
	}*/
	public int getAdminFees() {
		return adminFees;
	}
	public void setAdminFees(int adminFees) {
		this.adminFees = adminFees;
	}
	public int getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(int totalFees) {
		this.totalFees = totalFees;
	}
	
	
	
	//String bookingTime;

	
/*	public String getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}*/
	
	
	
	
	public Date getInvestmentDate() {
		return investmentDate;
	}
	public void setInvestmentDate(Date investmentDate) {
		this.investmentDate = investmentDate;
	}
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	/*public String getWithdrawDate() {
		return withdrawDate;
	}
	public void setWithdrawDate(String withdrawDate) {
		this.withdrawDate = withdrawDate;
	}*/
	public int getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(int withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	/*public int getRequestType() {
		return requestType;
	}
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}*/
	public String getWstatus() {
		return wstatus;
	}
	public void setWstatus(String wstatus) {
		this.wstatus = wstatus;
	}
	public String getClinicName2() {
		return clinicName2;
	}
	public void setClinicName2(String clinicName2) {
		this.clinicName2 = clinicName2;
	}
	public String getClinicName3() {
		return clinicName3;
	}
	public void setClinicName3(String clinicName3) {
		this.clinicName3 = clinicName3;
	}
	public String getClinicName4() {
		return clinicName4;
	}
	public void setClinicName4(String clinicName4) {
		this.clinicName4 = clinicName4;
	}
	public String getClinicName5() {
		return clinicName5;
	}
	public void setClinicName5(String clinicName5) {
		this.clinicName5 = clinicName5;
	}
	public String getClinicName6() {
		return clinicName6;
	}
	public void setClinicName6(String clinicName6) {
		this.clinicName6 = clinicName6;
	}
	public String getClinicName7() {
		return clinicName7;
	}
	public void setClinicName7(String clinicName7) {
		this.clinicName7 = clinicName7;
	}
	public String getClinicName8() {
		return clinicName8;
	}
	public void setClinicName8(String clinicName8) {
		this.clinicName8 = clinicName8;
	}
	public String getClinicName9() {
		return clinicName9;
	}
	public void setClinicName9(String clinicName9) {
		this.clinicName9 = clinicName9;
	}
	public String getClinicName10() {
		return clinicName10;
	}
	public void setClinicName10(String clinicName10) {
		this.clinicName10 = clinicName10;
	}
	public BigDecimal getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(BigDecimal investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
		
	

}

package com.trio.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the user_details database table.
 * 
 */
@Entity
@Table(name="user_details")
@NamedQuery(name="UserDetail.findAll", query="SELECT u FROM UserDetail u")
public class UserDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int user_Details_ID;

	@Column(name="firstname")
	private String firstname;
	
	@Column(name="middlename")
	private String middlename;
	
	@Column(name="lastname")
	private String lastname;

	@Column(name="email1")
	private String email1;

	@Column(name="phonenumber1")
	private String phonenumber1;
	
	@Column(name="country")
	private String country;
	
	@Column(name="bankName")
	private String bankName;	

	@Column(name="bankAcctNumber")
	private String bankAcctNumber;

	@Column(name="memberID")
	private String memberID;

	@Column(name="member_Ref_ID")
	private String member_Ref_ID;
	
	
	@Column(name="acctType")
	private String acctType;

	@Column(name="payStatus")
	private String payStatus;
	
	@Column(name="payAmt")
	private int payAmt;
	
	@Column(name="acctCreated_date")
	private Date acctCreated_date;
	
	@Column(name="acct_Approved_date")
	private Date acct_Approved_date;
	
	@Column(name="adminFees")
	private int adminFees;
	
	@Column(name="totalFees")
	private int totalFees;
	
	@Column(name="noofClinics")
	int noofclinics;
	
	@Column(name="clinic_status")
	String clinic_status;
	
	@Column(name="add_clinic_date")
	private Date add_clinic_date;
	
	@Column(name="agent_code")
	private String agent_code;
	
	@Column(name="npwp_Number")
	private String npwp_Number;
	
	//bi-directional many-to-one association to UserLogin
	@ManyToOne
	@JoinColumn(name="User_Login_ID")
	private UserLogin userLogin;
	
   @OneToMany(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,mappedBy = "userdetails")
  // private ClinicDetails clinicdetails;
   private List<ClinicDetails> clinicdetails;

   
   //bi-directional many-to-one association to UserDetail
 	//@OneToMany(mappedBy="userLogin",cascade = CascadeType.ALL)
 	//private List<UserDetail> userDetails;

	public UserDetail() {
	}

	public int getUser_Details_ID() {
		return this.user_Details_ID;
	}

	public void setUser_Details_ID(int user_Details_ID) {
		this.user_Details_ID = user_Details_ID;
	}

	public int getNoofclinics() {
		return noofclinics;
	}

	public void setNoofclinics(int noofclinics) {
		this.noofclinics = noofclinics;
	}

	public String getEmail1() {
		return this.email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getPhonenumber1() {
		return this.phonenumber1;
	}

	public void setPhonenumber1(String phonenumber1) {
		this.phonenumber1 = phonenumber1;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getMember_Ref_ID() {
		return member_Ref_ID;
	}

	public void setMember_Ref_ID(String member_Ref_ID) {
		this.member_Ref_ID = member_Ref_ID;
	}

	
	public UserLogin getUserLogin() {
		return this.userLogin;
	}
	
	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public int getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(int payAmt) {
		this.payAmt = payAmt;
	}

	public Date getAcctCreated_date() {
		return acctCreated_date;
	}

	public void setAcctCreated_date(Date acctCreated_date) {
		this.acctCreated_date = acctCreated_date;
	}

	public Date getAcct_Approved_date() {
		return acct_Approved_date;
	}

	public void setAcct_Approved_date(Date acct_Approved_date) {
		this.acct_Approved_date = acct_Approved_date;
	}	

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

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

/*	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
*/
	
	
	public Date getAdd_clinic_date() {
		return add_clinic_date;
	}

	public String getClinic_status() {
		return clinic_status;
	}

	public void setClinic_status(String clinic_status) {
		this.clinic_status = clinic_status;
	}

	public void setAdd_clinic_date(Date add_clinic_date) {
		this.add_clinic_date = add_clinic_date;
	}
	
	public String getAgent_code() {
		return agent_code;
	}

	public void setAgent_code(String agent_code) {
		this.agent_code = agent_code;
	}
	
	public String getNpwp_Number() {
		return npwp_Number;
	}

	public void setNpwp_Number(String npwp_Number) {
		this.npwp_Number = npwp_Number;
	}

	public List<ClinicDetails> getClinicdetails() {
		return clinicdetails;
	}

	public void setClinicdetails(List<ClinicDetails> clinicdetails) {
		this.clinicdetails = clinicdetails;
	}
	
	public ClinicDetails addUserDetail(ClinicDetails clinicDetail) {
		//getClinicdetails().ad
		getClinicdetails().add(clinicDetail);
		clinicDetail.setUserdetails(this);

		return clinicDetail;
	}

	public ClinicDetails removeUserDetail(ClinicDetails clinicDetail) {
		getClinicdetails().remove(clinicDetail);
		clinicDetail.setUserdetails(null);

		return clinicDetail;
	}	
	
	
}
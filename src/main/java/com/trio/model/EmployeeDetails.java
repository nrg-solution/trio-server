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
@Table(name="employee_details")
@NamedQuery(name="EmployeeDetails.findAll", query="SELECT e FROM EmployeeDetails e")
public class EmployeeDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int employee_Details_ID;

	@Column(name="employee_Name")
	private String empName;
	
	@Column(name="employee_PhoneNumber")
	private String empPhoneNumber;
	
	@Column(name="employee_EmailID")
	private String empEmailID;

	@Column(name="employee_BankName")
	private String empBankName;

	@Column(name="employee_AccountNumber")
	private String empAccountNumber;
	
	@Column(name="employee_BankBranchName")
	private String empBankBranchName;
	
	@Column(name="employee_Address")
	private String empAddress;	

	@Column(name="employee_Code")
	private String empCode;

	@Column(name="employee_Type")
	private String empType;

	@Column(name="employee_Salary")
	private int empSalary;
	
	/*@Column(name="employee_Role")
	private String empRole;
	
	@Column(name="reference_Code")
	private String refCode;*/
	
	@Column(name="employee_Role")
	private String empRole;
	
	@Column(name="reference_Code")
	private String refCode;
	
	@Column(name="acctCreated_date")
	private Date acctCreated_date;
	
	@Column(name="country")
	private String country;
	
	@Column(name="join_date")
	private Date join_date;
	
	//bi-directional many-to-one association to UserLogin
	//@ManyToOne
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="User_Login_ID")
	private UserLogin userLogin_emp;
	
	//bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy="employee_details",cascade = CascadeType.ALL)
	private List<ClinicDetails> clinicdetails;
	

	public EmployeeDetails() {
	}

	public int getEmployee_Details_ID() {
		return employee_Details_ID;
	}

	public void setEmployee_Details_ID(int employee_Details_ID) {
		this.employee_Details_ID = employee_Details_ID;
	}

	public String getEmployeeName() {
		return empName;
	}

	public void setEmployeeName(String empName) {
		this.empName = empName;
	}

	public String getEmployeePhoneNumber() {
		return empPhoneNumber;
	}

	public void setEmployeePhoneNumber(String empPhoneNumber) {
		this.empPhoneNumber = empPhoneNumber;
	}

	public String getEmployeeEmailID() {
		return empEmailID;
	}

	public void setEmployeeEmailID(String empEmailID) {
		this.empEmailID = empEmailID;
	}

	public String getEmployeeBankName() {
		return empBankName;
	}

	public void setEmployeeBankName(String empBankName) {
		this.empBankName = empBankName;
	}

	public String getEmployeeAccountNumber() {
		return empAccountNumber;
	}

	public void setEmployeeAccountNumber(String empAccountNumber) {
		this.empAccountNumber = empAccountNumber;
	}

	public String getEmployeeBankBranchName() {
		return empBankBranchName;
	}

	public void setEmployeeBankBranchName(String empBankBranchName) {
		this.empBankBranchName = empBankBranchName;
	}

	public String getEmployeeAddress() {
		return empAddress;
	}

	public void setEmployeeAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public String getEmployeeCode() {
		return empCode;
	}

	public void setEmployeeCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmployeeType() {
		return empType;
	}

	public void setEmployeeType(String empType) {
		this.empType = empType;
	}

	public UserLogin getUserLogin() {
		return userLogin_emp;
	}

	public void setUserLogin(UserLogin userLogin_emp) {
		this.userLogin_emp = userLogin_emp;
	}

	public int getEmpSalary() {
		return empSalary;
	}

	public void setEmpSalary(int empSalary) {
		this.empSalary = empSalary;
	}

	
	
	
	/*public UserLogin getUserLogin() {
		return this.userLogin;
	}
	*/
	// Newly Added

	public String getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public void setAcctCreated_date(Date acctCreated_date) {
		this.acctCreated_date = acctCreated_date;
	}
	
	public Date getAcctCreated_date() {
		return acctCreated_date;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getJoin_date() {
		return join_date;
	}

	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}

	public List<ClinicDetails> getClinicdetails() {
		return clinicdetails;
	}
	
	public void setClinicdetails(List<ClinicDetails> clinicdetails) {
		this.clinicdetails = clinicdetails;
	}
	
	public ClinicDetails addEmployeeDetail(ClinicDetails clinicdetail) {
		getClinicdetails().add(clinicdetail);
		clinicdetail.setEmployee_details(this);
		return clinicdetail;
	}

	public ClinicDetails removeEmployeeDetail(ClinicDetails clinicdetail) {
		getClinicdetails().remove(clinicdetail);
		clinicdetail.setEmployee_details(null);
		return clinicdetail;
	} 
	
	
}
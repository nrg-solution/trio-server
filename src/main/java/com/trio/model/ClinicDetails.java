package com.trio.model;

import java.io.Serializable;
//import java.util.List;
import java.sql.Date;

import javax.persistence.*;

/**
 * The persistent class for the user_details database table.
 * 
 */
@Entity
@Table(name="clinic_details")
@NamedQuery(name="ClinicDetails.findAll", query="SELECT c FROM ClinicDetails c")
public class ClinicDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int clinic_Details_ID;

	@Column(name="clinic_Name")
	private String clinicName;
	
	@Column(name="clinic_PhoneNumber")
	private String clinicPhoneNumber;
	
	@Column(name="clinic_EmailID")
	private String clinicEmailID;

	
	@Column(name="clinic_Address")
	private String clinicAddress;	

	@Column(name="clinic_Code")
	private String clinicCode;

	@Column(name="clinic_Type")
	private String clinicType;

	@Column(name="clinic_status")
	private String clinicStatus;
	
	@Column(name="acctCreated_date")
	private Date acctCreated_date;
	
	//bi-directional many-to-one association to UserLogin
	@ManyToOne
	@JoinColumn(name="agent_Details_ID")
	private AgentDetails agent_details;

	
	//bi-directional many-to-one association to UserLogin
	@ManyToOne
	@JoinColumn(name="employee_Details_ID")
	private EmployeeDetails employee_details;

	//bi-directional many-to-one association to UserLogin
	@ManyToOne
	@JoinColumn(name="user_Details_ID")
	private UserDetail userdetails;
	
	
	public ClinicDetails() {
	}

	

	public int getClinic_Details_ID() {
		return clinic_Details_ID;
	}



	public void setClinic_Details_ID(int clinic_Details_ID) {
		this.clinic_Details_ID = clinic_Details_ID;
	}



	public String getClinicName() {
		return clinicName;
	}



	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}



	public String getClinicPhoneNumber() {
		return clinicPhoneNumber;
	}



	public void setClinicPhoneNumber(String clinicPhoneNumber) {
		this.clinicPhoneNumber = clinicPhoneNumber;
	}



	public String getClinicEmailID() {
		return clinicEmailID;
	}



	public void setClinicEmailID(String clinicEmailID) {
		this.clinicEmailID = clinicEmailID;
	}



	public String getClinicAddress() {
		return clinicAddress;
	}



	public void setClinicAddress(String clinicAddress) {
		this.clinicAddress = clinicAddress;
	}



	public String getClinicCode() {
		return clinicCode;
	}



	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}



	public String getClinicType() {
		return clinicType;
	}



	public void setClinicType(String clinicType) {
		this.clinicType = clinicType;
	}



	public String getClinicStatus() {
		return clinicStatus;
	}



	public void setClinicStatus(String clinicStatus) {
		this.clinicStatus = clinicStatus;
	}



	public Date getAcctCreated_date() {
		return acctCreated_date;
	}



	public void setAcctCreated_date(Date acctCreated_date) {
		this.acctCreated_date = acctCreated_date;
	}



	public AgentDetails getAgent_details() {
		return agent_details;
	}



	public void setAgent_details(AgentDetails agent_details) {
		this.agent_details = agent_details;
	}



	public EmployeeDetails getEmployee_details() {
		return employee_details;
	}



	public void setEmployee_details(EmployeeDetails employee_details) {
		this.employee_details = employee_details;
	}



	public UserDetail getUserdetails() {
		return userdetails;
	}



	public void setUserdetails(UserDetail userdetails) {
		this.userdetails = userdetails;
	}
	
	
	

	/*public void setUserLogin(AgentDetails agent_details) {
		this.agent_details = agent_details;
	}
*/
	
	
	/*public UserLogin getUserLogin() {
		return this.userLogin;
	}
	*/
	
	
	// Clinic Employee
	
	
}
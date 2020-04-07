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
@Table(name="agent_details")
@NamedQuery(name="AgentDetails.findAll", query="SELECT a FROM AgentDetails a")
public class AgentDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int agent_Details_ID;

	@Column(name="agent_Name")
	private String agentName;
	
	@Column(name="agent_PhoneNumber")
	private String agentPhoneNumber;
	
	@Column(name="agent_EmailID")
	private String agentEmailID;

	@Column(name="agent_BankName")
	private String agentBankName;

	@Column(name="agent_AccountNumber")
	private String agentAccountNumber;
	
	@Column(name="agent_BankBranchName")
	private String agentBankBranchName;
	
	@Column(name="agent_Address")
	private String agentAddress;	

	@Column(name="agent_Code")
	private String agentCode;

	@Column(name="agent_Type")
	private String agentType;
	
	@Column(name="ref_emp_Code")
	private String refEmpCode;
	
	@Column(name="country")
	private String country;	
	
	@Column(name="acctCreated_date")
	private Date acctCreated_date;
	
	//bi-directional many-to-one association to UserLogin
//	@ManyToOne(cascade = CascadeType.REMOVE)
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="User_Login_ID")
	private UserLogin userLogin_agent;

	
	//bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy="agent_details",cascade = CascadeType.ALL)
	private List<ClinicDetails> clinicdetails;

	public AgentDetails() {
	}

	public int getAgent_Details_ID() {
		return agent_Details_ID;
	}

	public void setAgent_Details_ID(int agent_Details_ID) {
		this.agent_Details_ID = agent_Details_ID;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentPhoneNumber() {
		return agentPhoneNumber;
	}

	public void setAgentPhoneNumber(String agentPhoneNumber) {
		this.agentPhoneNumber = agentPhoneNumber;
	}

	public String getAgentEmailID() {
		return agentEmailID;
	}

	public void setAgentEmailID(String agentEmailID) {
		this.agentEmailID = agentEmailID;
	}

	public String getAgentBankName() {
		return agentBankName;
	}

	public void setAgentBankName(String agentBankName) {
		this.agentBankName = agentBankName;
	}

	public String getAgentAccountNumber() {
		return agentAccountNumber;
	}

	public void setAgentAccountNumber(String agentAccountNumber) {
		this.agentAccountNumber = agentAccountNumber;
	}

	public String getAgentBankBranchName() {
		return agentBankBranchName;
	}

	public void setAgentBankBranchName(String agentBankBranchName) {
		this.agentBankBranchName = agentBankBranchName;
	}

	public String getAgentAddress() {
		return agentAddress;
	}

	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getRefEmpCode() {
		return refEmpCode;
	}

	public void setRefEmpCode(String refEmpCode) {
		this.refEmpCode = refEmpCode;
	}

	public UserLogin getUserLogin() {
		return userLogin_agent;
	}

	public void setUserLogin(UserLogin userLogin_agent) {
		this.userLogin_agent = userLogin_agent;
	}

	
		// Newly Added

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getAcctCreated_date() {
		return acctCreated_date;
	}

	public void setAcctCreated_date(Date acctCreated_date) {
		this.acctCreated_date = acctCreated_date;
	} 
	
	public List<ClinicDetails> getClinicdetails() {
		return clinicdetails;
	}

	public void setClinicdetails(List<ClinicDetails> clinicdetails) {
		this.clinicdetails = clinicdetails;
	}
	
	public ClinicDetails addAgentDetail(ClinicDetails clinicdetail) {
		getClinicdetails().add(clinicdetail);
		clinicdetail.setAgent_details(this);

		return clinicdetail;
	}

	

	public ClinicDetails removeAgentDetail(ClinicDetails clinicdetail) {
		getClinicdetails().remove(clinicdetail);
		clinicdetail.setAgent_details(null);
		return clinicdetail;
	}
		
	
}
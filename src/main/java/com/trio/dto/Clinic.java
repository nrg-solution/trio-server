package com.trio.dto;


public class Clinic extends Person{
	
	public int agentPK;
	public int employeePK;
	public String empName;
	public String clinicName;
	public String clinicCode;
	private String agentCode;
	private String employeeCode;
	public String clinicPhoneNumber;
	public int noofclinics;
	public int totalAmount;
	public String memberNumber;
	public String memberName;
	
	public int getAgentPK() {
		return agentPK;
	}

	public void setAgentPK(int agentPK) {
		this.agentPK = agentPK;
	}

	public int getEmployeePK() {
		return employeePK;
	}

	public void setEmployeePK(int employeePK) {
		this.employeePK = employeePK;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public String getClinicPhoneNumber() {
		return clinicPhoneNumber;
	}

	public void setClinicPhoneNumber(String clinicPhoneNumber) {
		this.clinicPhoneNumber = clinicPhoneNumber;
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

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	

	/*public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	*/

}

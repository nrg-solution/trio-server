package com.trio.dto;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

public abstract class Person {
	
	private int primaryKey;
	private String name;
	private String phoneNumber;
	private String emailID;
	private String bankName;
	private String accountNumber;
	private String bankBranchName;
	private String address;
	private String status;
	
	private String refEmploy;
	public Date createdDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;
	
	private String userLoginPrimaryKey;
	private String username;
	private String selectedCountry;
	private int sNo;
	private String selectedMonth;
	private int clinicCount;
	private int agentCount;
	private BigDecimal totalClincs;
	private BigDecimal totalAgents;

	public int getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRefEmploy() {
		return refEmploy;
	}
	public void setRefEmploy(String refEmploy) {
		this.refEmploy = refEmploy;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUserLoginPrimaryKey() {
		return userLoginPrimaryKey;
	}
	public void setUserLoginPrimaryKey(String userLoginPrimaryKey) {
		this.userLoginPrimaryKey = userLoginPrimaryKey;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSelectedCountry() {
		return selectedCountry;
	}
	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public String getSelectedMonth() {
		return selectedMonth;
	}
	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}
	public int getClinicCount() {
		return clinicCount;
	}
	public void setClinicCount(int clinicCount) {
		this.clinicCount = clinicCount;
	}
	public int getAgentCount() {
		return agentCount;
	}
	public void setAgentCount(int agentCount) {
		this.agentCount = agentCount;
	}
	public BigDecimal getTotalClincs() {
		return totalClincs;
	}
	public void setTotalClincs(BigDecimal totalClincs) {
		this.totalClincs = totalClincs;
	}
	public BigDecimal getTotalAgents() {
		return totalAgents;
	}
	public void setTotalAgents(BigDecimal totalAgents) {
		this.totalAgents = totalAgents;
	}
		

	
}

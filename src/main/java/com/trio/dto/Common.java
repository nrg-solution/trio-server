package com.trio.dto;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

public abstract class Common {
	String username;
	String password;
	String memberID;
	String userloginPrimaryKeyString;
	String memberNumber;
	int memberCommition;
	int memberOvrriding;
	int sNo;
	int requestType;
	int userLoginPrimaryKey;
	String requestTypeStr;
	String withdrawDate;
	String status;
	String npwpNumber;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getUserloginPrimaryKeyString() {
		return userloginPrimaryKeyString;
	}
	public void setUserloginPrimaryKeyString(String userloginPrimaryKeyString) {
		this.userloginPrimaryKeyString = userloginPrimaryKeyString;
	}
	public int getUserLoginPrimaryKey() {
		return userLoginPrimaryKey;
	}
	public void setUserLoginPrimaryKey(int userLoginPrimaryKey) {
		this.userLoginPrimaryKey = userLoginPrimaryKey;
	}
	public String getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}
	public int getMemberCommition() {
		return memberCommition;
	}
	public void setMemberCommition(int memberCommition) {
		this.memberCommition = memberCommition;
	}
	public int getMemberOvrriding() {
		return memberOvrriding;
	}
	public void setMemberOvrriding(int memberOvrriding) {
		this.memberOvrriding = memberOvrriding;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public int getRequestType() {
		return requestType;
	}
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
	public String getRequestTypeStr() {
		return requestTypeStr;
	}
	public void setRequestTypeStr(String requestTypeStr) {
		this.requestTypeStr = requestTypeStr;
	}
	public String getWithdrawDate() {
		return withdrawDate;
	}
	public void setWithdrawDate(String withdrawDate) {
		this.withdrawDate = withdrawDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNpwpNumber() {
		return npwpNumber;
	}
	public void setNpwpNumber(String npwpNumber) {
		this.npwpNumber = npwpNumber;
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
	
	
	
}

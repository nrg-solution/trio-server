package com.trio.dto;

import java.sql.Date;

public class User extends Common{
	int id;
	String status; // return status
	String userRole;	
	String email_ID;
	String otp;
    String accoutType;
    int noofclinics;
    private Date acct_Approved_date;
    private int withdrawAmount;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getEmail_ID() {
		return email_ID;
	}
	public void setEmail_ID(String email_ID) {
		this.email_ID = email_ID;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getAccoutType() {
		return accoutType;
	}
	public void setAccoutType(String accoutType) {
		this.accoutType = accoutType;
	}
	public int getNoofclinics() {
		return noofclinics;
	}
	public void setNoofclinics(int noofclinics) {
		this.noofclinics = noofclinics;
	}
	public Date getAcct_Approved_date() {
		return acct_Approved_date;
	}
	public void setAcct_Approved_date(Date acct_Approved_date) {
		this.acct_Approved_date = acct_Approved_date;
	}
	public int getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(int withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	

}

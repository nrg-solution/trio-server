package com.trio.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GLGMem extends Common  {
	  
	//  int userLoginPrimaryKey; //commanded and brought to super class
	 // String memberID; //commanded and brought to super class
	  String  memberName;
	  String  memberType;
	  String memberEmail;
	  String memberPhone;
/*	  int memberCommition;
	  int memberOvrriding;*/ //commanded and brought to super class
	  String memberStatus;
	  int sNo;
	  Date created_date;
	  int totalAmount;
	  int grandTotal;
	  int noofclinics;
	  String paymentStatus;
	  String agentCode;
	  String selectedCountry;
	 List<String> clinicName = new  ArrayList<String>();
	//  String[] clinicName = new String[10];
	  
	  
	/*public int getUserLoginPrimaryKey() {
		return userLoginPrimaryKey;
	}
	public void setUserLoginPrimaryKey(int userLoginPrimaryKey) {
		this.userLoginPrimaryKey = userLoginPrimaryKey;
	}*/
	/*public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}*/
	  
	public String getMemberName() {
		return memberName;
	}
	public int getNoofclinics() {
		return noofclinics;
	}
	public void setNoofclinics(int noofclinics) {
		this.noofclinics = noofclinics;
	}
	
	
	
	
	
	public List<String> getClinicName() {
		return clinicName;
	}
	public void setClinicName(List<String> clinicName) {
		this.clinicName = clinicName;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	/*public int getMemberCommition() {
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
	}*/
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(int grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getSelectedCountry() {
		return selectedCountry;
	}
	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	
	  
	  
	  

}

package com.trio.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the comm_overr_details database table.
 * 
 */
@Entity
@Table(name="comm_overr_details")
@NamedQuery(name="CommOverrDetail.findAll", query="SELECT c FROM CommOverrDetail c")
public class CommOverrDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int com_Overr_ID;

	@Column(name="withdraw_amt")
	private int withdraw_amt;

	
	@Column(name="overriding_amt")
	private int overridingAmt;

	@Temporal(TemporalType.DATE)
	@Column(name="investment_date")
	private Date investment_date;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="enrollment_date")
	private Date enrollment_date;
	
	@Column(name="withdraw_date")
	private String withdraw_date;
	
	@Column(name="value_type")
	private String value_type;
	
	
	@Column(name="status")
	private String status;
	
	
	@Column(name="Member_Number")
	private String Member_Number;
	

/*	//bi-directional many-to-one association to MemberId
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="Member_ID")
	private MemberId memberId;
*/
	public CommOverrDetail() {
	}

	public int getCom_Overr_ID() {
		return this.com_Overr_ID;
	}

	public void setCom_Overr_ID(int com_Overr_ID) {
		this.com_Overr_ID = com_Overr_ID;
	}

	/*public int getCommissionAmt() {
		return this.withdraw_amt;
	}

	public void setCommissionAmt(int withdraw_amt) {
		this.withdraw_amt = withdraw_amt;
	}*/

	

	public int getOverridingAmt() {
		return this.overridingAmt;
	}

	public int getWithdraw_amt() {
		return withdraw_amt;
	}

	public void setWithdraw_amt(int withdraw_amt) {
		this.withdraw_amt = withdraw_amt;
	}

	public void setOverridingAmt(int overridingAmt) {
		this.overridingAmt = overridingAmt;
	}

	

	/*public Date getCreated_date() {
		return investment_date;
	}

	public void setCreated_date(Date investment_date) {
		this.investment_date = investment_date;
	}
*/
	
	
	public String getValue_type() {
		return value_type;
	}

	public String getWithdraw_date() {
		return withdraw_date;
	}

	public void setWithdraw_date(String withdraw_date) {
		this.withdraw_date = withdraw_date;
	}

	public Date getInvestment_date() {
		return investment_date;
	}

	public void setInvestment_date(Date investment_date) {
		this.investment_date = investment_date;
	}

	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}

	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	public Date getEnrollment_date() {
		return enrollment_date;
	}

	public void setEnrollment_date(Date enrollment_date) {
		this.enrollment_date = enrollment_date;
	}

	public String getMember_Number() {
		return Member_Number;
	}

	public void setMember_Number(String member_Number) {
		Member_Number = member_Number;
	}

/*	public MemberId getMemberId() {
		return this.memberId;
	}

	public void setMemberId(MemberId memberId) {
		this.memberId = memberId;
	}
*/
	
	
}
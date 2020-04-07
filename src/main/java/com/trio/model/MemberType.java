package com.trio.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the member_type database table.
 * 
 */
@Entity
@Table(name="member_type_master")
@NamedQuery(name="MemberType.findAll", query="SELECT m FROM MemberType m")
public class MemberType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int member_Type_ID;

	@Column(name="Member_Type_Name")
	private String member_Type_Name;
	
	@Column(name="Member_Type_Cost")
	private int member_Type_Cost;

	@Column(name="Member_Type_Currency")
	private String member_Type_Currency;
	

	public MemberType() {
	}

	public int getMember_Type_ID() {
		return this.member_Type_ID;
	}

	public void setMember_Type_ID(int member_Type_ID) {
		this.member_Type_ID = member_Type_ID;
	}

	public int getMember_Type_Cost() {
		return this.member_Type_Cost;
	}

	public void setMember_Type_Cost(int member_Type_Cost) {
		this.member_Type_Cost = member_Type_Cost;
	}

	public String getMember_Type_Currency() {
		return this.member_Type_Currency;
	}

	public void setMember_Type_Currency(String member_Type_Currency) {
		this.member_Type_Currency = member_Type_Currency;
	}

	public String getMember_Type_Name() {
		return this.member_Type_Name;
	}

	public void setMember_Type_Name(String member_Type_Name) {
		this.member_Type_Name = member_Type_Name;
	}

}
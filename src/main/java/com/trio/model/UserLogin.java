package com.trio.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user_login database table.
 * 
 */
@Entity
@Table(name="user_login")
@NamedQuery(name="UserLogin.findAll", query="SELECT u FROM UserLogin u")
public class UserLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int user_Login_ID;

	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="status")
	private String status;

	@Column(name="user_role")
	private String userRole;

	@Column(name="user_otp")
	private String userOtp;

	@Temporal(TemporalType.DATE)
	@Column(name="last_login")
	private Date lastLogin;
	
	//bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy="userLogin",cascade = CascadeType.ALL)
	private List<UserDetail> userDetails;

	
	//bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy="userLogin_agent",cascade = CascadeType.ALL)
	private List<AgentDetails> agentDetails;

	
	//bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy="userLogin_emp",cascade = CascadeType.ALL)
	private List<EmployeeDetails> empDetails;
		
	public UserLogin() {
	}

	public int getUser_Login_ID() {
		return this.user_Login_ID;
	}

	public void setUser_Login_ID(int user_Login_ID) {
		this.user_Login_ID = user_Login_ID;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserOtp() {
		return this.userOtp;
	}

	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	// Member 
	public List<UserDetail> getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(List<UserDetail> userDetails) {
		this.userDetails = userDetails;
	}

	public UserDetail addUserDetail(UserDetail userDetail) {
		getUserDetails().add(userDetail);
		userDetail.setUserLogin(this);

		return userDetail;
	}

	public UserDetail removeUserDetail(UserDetail userDetail) {
		getUserDetails().remove(userDetail);
		userDetail.setUserLogin(null);

		return userDetail;
	}



	// Agent details method 
	
	public List<AgentDetails> getAgentDetails() {
		return agentDetails;
	}

	public void setAgentDetails(List<AgentDetails> agentDetails) {
		this.agentDetails = agentDetails;
	}
	
	public AgentDetails addUserDetail(AgentDetails agentDetail) {
		getAgentDetails().add(agentDetail);
		agentDetail.setUserLogin(this);

		return agentDetail;
	}

	public AgentDetails removeUserDetail(AgentDetails agentDetail) {
		getAgentDetails().remove(agentDetail);
		agentDetail.setUserLogin(null);

		return agentDetail;
	} 
	
	
	// Employee details 
	
	
		public List<EmployeeDetails> getEmployeeDetails() {
			return empDetails;
		}

		public void setEmployeeDetails(List<EmployeeDetails> empDetails) {
			this.empDetails = empDetails;
		}
		
		public EmployeeDetails addUserDetail(EmployeeDetails empDetail) {
			getEmployeeDetails().add(empDetail);
			empDetail.setUserLogin(this);

			return empDetail;
		}

		public EmployeeDetails removeUserDetail(EmployeeDetails empDetail) {
			getEmployeeDetails().remove(empDetail);
			empDetail.setUserLogin(null);

			return empDetail;
		} 
	
	/*public List<AgentDetails> agentDetails2() {
		return this.agentDetails;
	}

	public void (List<AgentDetails> agentDetails) {
		this.agentDetails = agentDetails;
	}

	public UserDetail addUserDetail(AgentDetails agentDetails) {
		getUserDetails().add(agentDetails);
		userDetail.setUserLogin(this);

		return agentDetails;
	}

	public AgentDetails removeUserDetail(AgentDetails agentDetails) {
		getUserDetails().remove(agentDetails);
		userDetail.setUserLogin(null);

		return agentDetails;
	}*/
	
	// Booking details table 
	
	

}
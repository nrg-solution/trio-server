package com.trio.service;

//import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//import javax.enterprise.inject.Produces;

import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import com.trio.bo.*;
import com.trio.dto.Agent;
import com.trio.dto.Dropbox;
import com.trio.dto.Employee;
import com.trio.dto.Clinic;
import com.trio.dto.GLGMem;
import com.trio.dto.Member;
import com.trio.dto.User;
import com.trio.util.TrioException;

//import static org.mockito.Matchers.anyBoolean;



import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@RestController
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class UiApplication implements Filter{
	
	public static final Logger logger = LoggerFactory.getLogger(UiApplication.class);

	User user;
	Member member;
	Dropbox dropbox;
	
	@Value("$ {crossorigin.server}")
	private String crossorigin;
	 
	@Autowired
	StorageService storageService;
	
	List<String> files = new ArrayList<String>();
	
	@Autowired
	TrioBo bo;
	
	Map<String,Integer> agentMap;
	Map<String,Integer> empMap;
	Map<String,Integer> clinicMap;	
	ArrayList<String> clinicNameList = null;
	ArrayList<Employee> employeeList = null; 
	ArrayList<String> agList = null;
	ArrayList<String> empList = null;
	ArrayList<Agent> agentList = null;
	ArrayList<Clinic> clinicList = null;
	ArrayList<String> listCountry=null;
	ArrayList<String> listState=null;
	Member m=null;
	ArrayList<Member> list = null;
	// get all State
	HashMap<String,String> hm =  new HashMap<String,String>();	
	// get all Agent Name
	HashMap<Integer,String> hmAgentName =  new HashMap<Integer,String>();
	// get all Employee Name
	HashMap<Integer,String> hmEmpName =  new HashMap<Integer,String>();
	ArrayList<String> invagList = null;
	Map<String,Integer> invagentMap;
	Map<String,Integer> partnerMap;
	ArrayList<String> partnerList = null;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

	   // HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	  //  response.setHeader("Access-Control-Allow-Origin", "https://www.gglway.com");
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	   // response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

	  //  Access-Control-Allow-Origin: https://www.gglway.com

	    chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
	
	// 52.24.234.95
	// http://52.24.234.95:80
	// http://52.24.234.95:80
	// -------------------Login check -------------------------------------------	
	@CrossOrigin(origins ="http://52.24.234.95:80")
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public ResponseEntity<?>  user(@RequestParam String username,@RequestParam String password) {
	logger.info("[UI Application] User Name ---------------->"+username);
	logger.info("[UI Application] Password  ---------------->"+password);
	try {
			user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user = bo.userLogin(user);
			logger.info("[UI Application] Member Number ---------------->"+user.getMemberNumber());
			//user.setStatus(bo.userLogin(user).getStatus());
		}catch(Exception e) {
		user.setStatus("Network Error Please try again");
		logger.info("Exception -->"+e.getMessage());
	}finally {
		
	}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	//----------------- Check user -------------------------	
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/Checkuser",method=RequestMethod.GET)
	public ResponseEntity<?>  Checkuser(@RequestParam String username) {
		logger.info("[UI Application-Checkuser] User Name ---------------->"+username);		
		try {
			user = new User();
			user.setUsername(username);
			logger.info("Before Calling BO Checkuser");
			user = bo.Checkuser(user,1);
			logger.info("Successfully Called BO Checkuser");
		}catch(Exception e) {
			user.setStatus("Network Error Please try again");
			logger.info("Exception ------------->"+e.getMessage());
		}finally {
			
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	  }
		
	//----------------- reset Password submit -------------------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/resetPassword",method=RequestMethod.GET)
	public ResponseEntity<User>  resetPassword(@RequestParam String newPassword,@RequestParam String userName) {
		logger.info("------------ Inside resetPassword method -------------");
		logger.info("[UI Application-resetPassword] New Password ---------------->"+newPassword);
		try {
			user = new User();
			user.setPassword(newPassword);
			user.setUsername(userName);
			logger.info("Before Calling BO Checkuser");
			user = bo.Checkuser(user,3);					
			logger.info("Successfully Called BO Checkuser");
		}catch(Exception e) {
			user.setStatus("Network Error Please try again");
			logger.info("Exception ------------->"+e.getMessage());
		}finally {
			
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	  }

	//----------------- OTP Validate check -------------------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/OTPCheck",method=RequestMethod.GET)
	public ResponseEntity<?>  OTPCheck(@RequestParam String otp) {
		logger.info("[UI Application-OTPCheck] User Name ---------------->"+otp);
		//String tmpOTP;
		try {
			user = new User();
			user.setMemberID(otp);
			user = bo.Checkuser(user,2);
		}catch(Exception e) {
			user.setStatus("Network Error Please try again");
			logger.info("Exception ------------->"+e.getMessage());
		}finally {
			
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	  }	
	
	// -------------------Create a Member -------------------------------------------	
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<Member>  createMember(@RequestBody Member member) {
	   logger.info("------------------ Inside Member Create -----------------");
       logger.info("[UiApplication] Email ID ----------->"+member.getEmailID()); // Email
       logger.info("[UiApplication] Country ----------->"+member.getSelectedCountry()); // Country
       logger.info("[UiApplication] Phone number ----------->"+member.getPhoneNumber()); // Phone number	      
       logger.info("[UiApplication] First name ----------->"+member.getFirstName()); // First name
       logger.info("[UiApplication] Last name ----------->"+member.getLastName()); // Last name 
       logger.info("[UiApplication] User name ----------->"+member.getUsername()); // User name 
       logger.info("[UiApplication] Password ----------->"+member.getPassword()); // Password	       
       logger.info("[UiApplication] Bank name ----------->"+member.getBankName()); // bank name
       logger.info("[UiApplication] Acct Number ----------->"+member.getBankAcctNumber()); // bank account number 
       logger.info("[UiApplication] Acct type  ----------->"+member.getActType()); // bank account type
       logger.info("[UiApplication] No.of Clinics  ----------->"+member.getNoofclinics()); // No.of Clinics
       logger.info("[UiApplication] NPWP Number  ----------->"+member.getNpwpNumber()); 
       try 
   			{
    	   logger.info("Before Calling BO agentCodeExistCheck");
    	   String response = bo.agentCodeExistCheck(member.getAgentCode());
    	   logger.info("Successfully Called BO agentCodeExistCheck");
    	   logger.info("Agent Code Validate Status --------------->"+response);
		   if(response.equalsIgnoreCase("exit")) {
		       bo.createMember(member);
		       logger.info("Successfully called Bo"+member.getStatus());
		   }else {
			   response = "not exit";
				member.setStatus(response);
		   }
   		} 
	 catch(TrioException ge) {
		logger.info("TRIO Exception --------------->"+ge.getMessage()); 
	 	}
	 catch(Exception e) {
			logger.info("Exception --------------->"+e.getMessage()); 
	 	}
   return new ResponseEntity<Member>(member, HttpStatus.CREATED);
}
	
	

	// -------------------- drop down value --------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getAllCountry",method=RequestMethod.GET)
	public ResponseEntity<?> getAllCountry()
	{
	 logger.info("------------ Inside getAllCountry method -------------");
	   try {
			listCountry = new ArrayList<String>();
			hm=bo.getCountry(hm);		
			Set<String> keys = hm.keySet();
		    for(String key: keys){
		        listCountry.add(key);
		    }
			}catch(Exception e){
				logger.info("Exception -->"+e.getMessage());
		}finally{
			
		}
		return new ResponseEntity<ArrayList<String>>(listCountry, HttpStatus.CREATED);

	}
	
	// -------------------- drop down value --------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getState",method=RequestMethod.GET)
	public ResponseEntity<?> getState(@RequestParam String country)
	{
		logger.info("------------ Inside getState method -------------");
			try {
				listState = new ArrayList<String>();
				String stateString=hm.get(country);
				String[] stateStringArray = stateString.split("-");
				for (String r : stateStringArray) {
					listState.add(r);
				}
   	return new ResponseEntity<ArrayList<String>>(listState, HttpStatus.CREATED);
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());

		}
	    return new ResponseEntity<ArrayList<String>>(listState, HttpStatus.CREATED);

	}			

	// ---------------- get My Profile view ------------
	 @CrossOrigin(origins = "http://localhost:8092")
	 @RequestMapping(value="/getMyProfile",method=RequestMethod.GET)
	 public ResponseEntity<?> getMyProfile(String primaryKeyStr)
		{
		// Member member = new Member();
		 	member = new Member();
		 	member.setUserloginPrimaryKeyString(primaryKeyStr); // set the user login primary key			 	
		 	member = bo.getMyProfile(member);
			logger.info("-------------- getMyProfile Called--------------");
		    return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	
		}

		 
	  // ------------------- Load ALL TRIO Member list method --------------------------------------
	    @CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/getAllMemberList",method=RequestMethod.GET)
		public ResponseEntity<?> getAllMemberList(@RequestParam String requestType)
		{
			logger.info("------------ Inside getAllMemberList ---------------");
			logger.info("Request type ------------>"+requestType);
			ArrayList<GLGMem> glglist = null;	
			logger.info("-------------- getAllReservationDetails Called --------------");
			glglist = new ArrayList<GLGMem>();
			logger.info("-------------- Before Calling  getAllMemberList --------------");
			glglist = bo.getAllMemberList(requestType,glglist);
			logger.info("-------------- Successfully Called  getAllMemberList --------------");
			logger.info("-------------- glglist Size --------------"+glglist.size());
			return new ResponseEntity<ArrayList<GLGMem>>(glglist, HttpStatus.CREATED);
		
		}

		
		
    // ---------------------- Approval -------------------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getApproved",method=RequestMethod.GET)
	public ResponseEntity<?>  getApproved(@RequestParam int userLoginPrimaryKey,@RequestParam String requestType) {
		logger.info("------------ Inside getApproved method -------------");//+userLoginPrimaryKey);
		logger.info("Primary Key -------------->"+userLoginPrimaryKey);
		try {
			user = new User();
			logger.info("Before Calling BO -->"+requestType);
			logger.info("Before Calling BO getApproved");
			user = bo.getApproved(user,userLoginPrimaryKey,requestType);
			logger.info("Successfully Called BO getApproved");
			//user.setStatus("success");
		}catch(Exception e) {
			user.setStatus("Network Error Please try again");
		}finally {
			
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	  }		
	
/*
	 // ---------------- get My Comm and Overriding ------------
	 @CrossOrigin(origins = "http://52.24.234.95:80")
	 @RequestMapping(value="/getMyCommandOverInfo",method=RequestMethod.GET)
	 public ResponseEntity<?> getMyCommandOverInfo(String primaryKeyStr)
		{		
		 	logger.info("------------ Inside getMyCommandOverInfo method -------------");
			ArrayList<GLGMem> myMemList = null;
		    GLGMem glgmem = new GLGMem();
			myMemList = new ArrayList<GLGMem>();
			glgmem.setUserloginPrimaryKeyString(primaryKeyStr); // set the user login primary key	
			logger.info("Before Calling BO getMyCommandOverInfo");
			myMemList = bo.getMyCommandOverInfo(primaryKeyStr,myMemList);
			logger.info("Successfully Called BO getMyCommandOverInfo");
		    return new ResponseEntity<ArrayList<GLGMem>>(myMemList, HttpStatus.CREATED);

		}
	*/
		// My Profile update method 
		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/updateMyProfile",method=RequestMethod.POST)
		public ResponseEntity<?> updateMyProfile(@RequestBody Member member)
		{
			logger.info("------------ Inside updateMyProfile method -------------");
			logger.info("Primary Key String -->"+member.getUserloginPrimaryKeyString());
			logger.info("Primary Key -->"+member.getMember1_primaryKey());
			logger.info("Bank Name -->"+member.getBankName());
			logger.info("First name -->"+member.getFirstName());
			logger.info("Last Name -->"+member.getLastName());
			logger.info("Bank A/C number -->"+member.getBankAcctNumber());
			logger.info("Country -->"+member.getCountry());
			logger.info("Phone Number -->"+member.getPhoneNumber());
			logger.info("Email -->"+member.getEmailID());
			logger.info("No.of Clinics -->"+member.getNoofclinics());
			logger.info("Total Amount -->"+member.getTotalAmount());
			logger.info("Npwp Number -->"+member.getNpwpNumber());
		   try {
			  logger.info("Before Calling BO updateMyProfile");
			  member= bo.updateMyProfile(member);
			  logger.info("Successfully Called BO updateMyProfile");
			   return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			   
		   }catch(Exception e){
					logger.info("UI Application Exception -->"+e.getMessage());
					member.setStatus("failure");
				    return new ResponseEntity<Member>(member, HttpStatus.CREATED);
			}finally{
				
			}
		}	
		
		//------- withdrawal form ------
		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/submitWith",method=RequestMethod.POST)
		public ResponseEntity<Member>  submitWith(@RequestBody Member member) {
			logger.info("------------ Inside submitWith method -------------");
			logger.info("Total Amount----->"+member.getTotalAmount());
			logger.info("Given Amount----> "+member.getWithdrawamt());
			
			if(member.getTotalAmount() < member.getWithdrawamt()){
				member.setStatus("Given Amount is Exceed.Plese give the valid amount");
			}
			if(member.getWithdrawamt()==0){
				member.setStatus("Please give the valid amount");
			}
			if(member.getTotalAmount() > member.getWithdrawamt()){
				bo.submitWith(member);
				member.setStatus("success");
	
			}
			
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
	// ------------- getReceipt ---------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getReceipt",method=RequestMethod.POST)
	public ResponseEntity<Member>getReceipt(@RequestBody String trioNumber) {
		logger.info("------------ Inside getReceipt method -------------");
		Member m=null;
		try {
			logger.info("TRIO Number =============>"+trioNumber);
			m=new Member();
			m.setStatus("success");
			//6weito
			String filePathString="/home/ec2-user/Weblogic/user_projects/domains2/servers/server4/tmp/_WL_user/upload/bkvwnf/war/trio_payment/" +trioNumber+".jpg";
			File f = new File(filePathString);
			if(f.exists() && !f.isDirectory()) { 
			    // do something
				m.setPaymentpath("http://52.24.234.95:80:7006/upload/trio_payment/"+trioNumber+".jpg");
			}
			else {
				m.setPaymentpath("http://52.24.234.95:80:7006/upload/trio_payment/NoPay.jpg");
			}
			logger.info("TRIO Path =============>"+m.getPaymentpath());
		}catch(Exception e) {
			m.setStatus("failure");
			logger.info("Exception--->"+e.getMessage());
		}
		return new ResponseEntity<Member>(m, HttpStatus.CREATED);
	}
	
	
	//------------ Add Clinic Name ------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/addClinic",method=RequestMethod.POST)
	public ResponseEntity<Member>addClinic(@RequestBody Member member) {
		logger.info("--------------Inside the addClinic------------");
		try {
			logger.info("[UI Application] Member ID --------->"+member.getMemberID()); 
			logger.info("[UI Application] Clinic Name --------->"+member.getClinicName());
			logger.info("[UI Application] Clinic Name2 --------->"+member.getClinicName2());
			logger.info("[UI Application] Clinic Name3 --------->"+member.getClinicName3());
			logger.info("[UI Application] Clinic Name4 --------->"+member.getClinicName4());
			logger.info("[UI Application] Clinic Name5 --------->"+member.getClinicName5());
			logger.info("[UI Application] Clinic Name6 --------->"+member.getClinicName6());
			logger.info("[UI Application] Clinic Name7 --------->"+member.getClinicName7());
			logger.info("[UI Application] Clinic Name8 --------->"+member.getClinicName8());
			logger.info("[UI Application] Clinic Name9 --------->"+member.getClinicName9());
			logger.info("[UI Application] Clinic Name10 --------->"+member.getClinicName10());
			logger.info("[UI Application] No.of Clinics  ---->"+member.getNoofclinics());
			logger.info("---------------Before calling addClinic() BO------------------");
			List<String> clinicList = new ArrayList<String>();
			clinicList.add(member.getClinicName());
			clinicList.add(member.getClinicName2());
			clinicList.add(member.getClinicName3());
			clinicList.add(member.getClinicName4());
			clinicList.add(member.getClinicName5());
			clinicList.add(member.getClinicName6());
			clinicList.add(member.getClinicName7());
			clinicList.add(member.getClinicName8());
			clinicList.add(member.getClinicName9());
			clinicList.add(member.getClinicName10());
			clinicList.removeAll(Collections.singleton(null));
			clinicList.removeAll(Collections.singleton(""));

			member.clinicPKs = new int[10];
			for(int i=0;i<member.getNoofclinics();i++) {
				//String clinicCode[] = member.getClinicName().split("-");
				String clinicCode[] = clinicList.get(i).split("-");				
				logger.info("After Split Clinic Code ---->"+clinicCode[1]);
				int temp = clinicMap.get(clinicCode[1]);
				member.clinicPKs[i] = temp;
				logger.info("Clinic Primary Key ----------->"+temp);
				//member.setUserLoginPrimaryKey(value1); // this Clinic details table primary key		
			}
			logger.info("Before Calling BO addClinic");
			member = bo.addClinic(member); 	
			logger.info("Successfully Called BO addClinic");
			logger.info("Status -------------------------->"+member.getStatus());
		}catch(Exception e) {
			member.setStatus("failure");
			logger.info("[UI Application-addClinic]  Exception--->"+e.getMessage());
		}
		return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	}
	
	// ---------------- Edit Clinic ---------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/editClinic",method=RequestMethod.POST)
	public ResponseEntity<Member>editClinic(@RequestBody Member member) {
		logger.info("--------------Inside the AddClinic------------");
		try {
			member.setMemberID(member.getMemberID());
			member.setNoofclinics(member.getNoofclinics()); 
			logger.info("[UI Application] Member ID ---------"+member.getMemberID()); 
			logger.info("[UI Application] Noofclinics ---------"+member.getNoofclinics());
			logger.info("Before Calling BO editClinic");
			member = bo.editClinic(member); 
			logger.info("Successfully Called BO editClinic");
		}catch(Exception e) {
			member.setStatus("failure");
			logger.info("Exception--->"+e.getMessage());
		}
		return new ResponseEntity<Member>(member, HttpStatus.CREATED);
	}

	// ------------------- Load ALL Clinic list method --------------------------------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/openWithdraw",method=RequestMethod.POST)
	public ResponseEntity<?> openWithdraw(@RequestBody Member member)
	{
		logger.info("------------ Inside openWithdraw method -------------");
		try {
			logger.info("Member ID for Withdraw ------------>"+member.getMemberID());
			logger.info("Date ------------>"+member.getWithdrawDate());
			logger.info("Before Calling BO openWithdraw");
			member= bo.openWithdraw(member); 	
			logger.info("Successfully Called BO openWithdraw");
			member.setStatus("success");
			}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
			member.setStatus("failure");
		}
	return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		
  }
	
	 // ------------------- Load ALL Clinic list method --------------------------------------
	 @CrossOrigin(origins = "http://52.24.234.95:80")
	 @RequestMapping(value="/getAllClinicList",method=RequestMethod.GET)
	 public ResponseEntity<?> getAllClinicList(@RequestParam String requestType)
	 {
		logger.info("------------ Inside getAllClinicList method -------------");
		 ArrayList<GLGMem> glglist = null;	
		 try {
			 	if(requestType.equalsIgnoreCase("Approved")) {
					glglist = new ArrayList<GLGMem>();
					logger.info("Before Calling BO getAllClinicList");
					glglist = bo.getAllClinicList(requestType,glglist);
					logger.info("Successfully Called BO getAllClinicList");
					//logger.info("2 List Size -------------->"+glglist.size());
				}
				else {
				}
				return new ResponseEntity<ArrayList<GLGMem>>(glglist, HttpStatus.CREATED);
 
		 }catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
		 }
		 finally {
			 
		 }
			return new ResponseEntity<ArrayList<GLGMem>>(glglist, HttpStatus.CREATED);
		}
	
	
	 // ------------------- Load ALL Clinic list method --------------------------------------
	 @CrossOrigin(origins = "http://52.24.234.95:80")
	 @RequestMapping(value="/getAllClinicList2",method=RequestMethod.GET)
	 public ResponseEntity<?> getAllClinicList2(@RequestParam String requestType)
	 {
		logger.info("------------ Inside getAllClinicList2 method -------------");
		try {
			 if(requestType.equalsIgnoreCase("All")) {
				 logger.info("--------- Inside If condition for All--------------");
				 this.clinicList = new ArrayList<Clinic>();
				 logger.info("Before Calling BO getAllClinicList2");
				 this.clinicList = bo.getAllClinicList2(clinicList,requestType);
				 logger.info("Successfully Called BO getAllClinicList2");
  				 logger.info("List Size -------------->"+clinicList.size());
	
			 }
			 else {
				 logger.info("------------ Inside else condition for All not found -------------");
			 }
				
				return new ResponseEntity<ArrayList<Clinic>>(clinicList, HttpStatus.CREATED);
 
		 }catch(Exception e) {
			 		logger.info("Exception -->"+e.getMessage());
		 }
		 finally {
			 
		 }
			return new ResponseEntity<ArrayList<Clinic>>(clinicList, HttpStatus.CREATED);
		}
	
	 // ---------------- get Ledger view ------------
	 @CrossOrigin(origins = "http://52.24.234.95:80")
	 @RequestMapping(value="/getLedgerInfo",method=RequestMethod.GET)
	 public ResponseEntity<?> getLedgerInfo(String primaryKeyStr,int requestType)
		{
			logger.info("------------ Inside getLedgerInfo method -------------");
			ArrayList<Member> ledgetList = null;
			Member member = new Member();
		 	//member = new Member();
		 	ledgetList = new ArrayList<Member>();
		 	member.setUserloginPrimaryKeyString(primaryKeyStr); 
		 	member.setRequestType(requestType);
		 	// set the user login primary key			 	
		 	logger.info("Before Calling BO getLedgerInfo");
		 	ledgetList = bo.getLedgerInfo(member,ledgetList);
		 	logger.info("Successfully Called BO getLedgerInfo");		 	
		 	logger.info("List size -------->"+ledgetList.size());
		 	for(Member m : ledgetList) {
			 	logger.info("Withdraw Status ------->"+m.getStatus());
			 	logger.info("Withdraw Action Status ------->"+m.getWstatus());
			 	logger.info("-----------------------------------------------");
		 	}
			return new ResponseEntity<ArrayList<Member>>(ledgetList, HttpStatus.CREATED);

		}
	 
   		//------------------- Withdraw Approval -------------------
 		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/getApprovewithdraw",method=RequestMethod.POST)
		public ResponseEntity<User>getApprovewithdraw(@RequestBody User user) {
 			logger.info("------------ Inside getApprovewithdraw method -------------");
 			try {
			//user = new User();
			logger.info("Member ID  -->"+user.getMemberID());
			logger.info("Request type -->"+user.getRequestTypeStr());
			logger.info("[getApprovewithdraw] Before Calling BO");
			user = bo.getApprovewithdraw(user);
			logger.info("[getApprovewithdraw] Successfully Called BO");
 			}catch(Exception e) {
			user.setStatus("Network Error Please try again");
		}finally {
			
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	  }	

    // Save Employee
    //------------------ Save Employee -------------------   
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/saveEmployee",method=RequestMethod.POST)
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee)
	{
		logger.info("------------ Inside saveEmployee method -------------");
		//Employee Personal Info
		logger.info("Employee Name -->"+employee.getName());
		logger.info("Employee Phone Number -->"+employee.getPhoneNumber());
		logger.info("Employee Email Address -->"+employee.getEmailID());
		logger.info("Employee Address -->"+employee.getAddress());
		logger.info("Employee Salary -->"+employee.getSalary()); 
		logger.info("Employee Type --->"+employee.getEmpType());
		logger.info("Employee Role --->"+employee.getEmpRole());
		logger.info("Employee Reference Code --->"+employee.getRefEmploy());
		logger.info("Selected Country ------>"+employee.getSelectedCountry());
		logger.info("Joining Date ---------->"+employee.getJoin_date());
	   try {
		   logger.info("Before Calling BO savePerson");
		   String response = bo.savePerson(employee);
		   logger.info("Successfully Called BO savePerson");
		   logger.info("Server respone Code from BO ---------->"+response);
		   employee.setStatus(response);  
	   }
	   catch(Exception e) {
		   employee.setStatus("failed");
			logger.info("Exception -->"+e.getMessage());
	   }finally {
		   
	   }
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}
			
	//------------------ Save Agent Data -------------------   
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/saveAgent",method=RequestMethod.POST)
	public ResponseEntity<Agent> saveAgent(@RequestBody Agent agent)
	{
		logger.info("------------ Inside saveAgent method -------------");
		//Agent Personal Info
		logger.info("Agent Name -->"+agent.getName());
		logger.info("Agent Phone Number -->"+agent.getPhoneNumber());
		logger.info("Agent Email Address -->"+agent.getEmailID());
		logger.info("Agent Address -->"+agent.getAddress());

		// Agent Bank Info
		logger.info("Agent Account Number -->"+agent.getAccountNumber());
		logger.info("Agent Bank Name -->"+agent.getBankName());
		logger.info("Agent Branch Name -->"+agent.getBankBranchName());
		logger.info("Agent Type --->"+agent.getAgentType());
		//Employee Info
		logger.info("Reference Employee --->"+agent.getRefEmploy());
		logger.info("Agent Country --->"+agent.getSelectedCountry());
	if(agent.getAgentType().equalsIgnoreCase("investmentAgent")) {
		agent.setAgentType("Investment Agent");
	}
	if(agent.getAgentType().equalsIgnoreCase("clinicAgent")) {
		agent.setAgentType("Clinic Agent");
	}
	
	   try {
		   // Call BO
		    /*String agentCode[] = agent.getRefEmploy().split("-");
		   	int value1 = agentMap.get(agentCode[1]);
			logger.info("Employee Code Primary Key ----------->"+value1);		
			agent.setPrimaryKey(value1);*/
			
		   	logger.info("Before Calling Agent Save - BO");
			String response = bo.savePerson(agent);
			logger.info("Successfully Called BO savePerson");	
			agent.setStatus(response);  
		
	   }
	   catch(Exception e) {
		   agent.setStatus("failed");
			logger.info("Exception -->"+e.getMessage());
	   }finally {
		   
	   }

		return new ResponseEntity<Agent>(agent, HttpStatus.CREATED);
	}
	

	//------------------ get Agent Data  -------------------   
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getAgentInfo",method=RequestMethod.GET)
	public ResponseEntity<?> getAgentInfo()
	{
		logger.info("--------------------- Get Agent Info ----------------------------");
		agentList=null;
	   try {
		   this.agentList =  new ArrayList<Agent>();
		   this.agentList = bo.getAgentInfo(agentList);
		   logger.info("Agent List ---------->"+agentList.size()); 

	   }catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());

	   }finally {
		   
	   }
	   return new ResponseEntity<ArrayList<Agent>>(agentList, HttpStatus.CREATED);
	}
	
				
	// View Method - Use GET
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getAgentView",method=RequestMethod.GET)
	public ResponseEntity<Agent> getAgentView(int agentPK,String agentCode,String requestType) // agentPK is is Primary Key 
	{
		logger.info("------------ getAgentView -------------->");
		Agent agent =  null;
		try {
			logger.info("Requested Type -------------->"+requestType);
			if(requestType.equalsIgnoreCase("agent")) {
				logger.info("Agent List ----->"+agentList.size()); 
				agent = new Agent();
				for(Agent a : agentList) {
					if(a.getPrimaryKey()==agentPK) {
						logger.info("agentPK -------------->"+agentPK);
						logger.info("List agentPK -------------->"+a.getPrimaryKey());
						agent.setName(a.getName());
						agent.setAgentCode(a.getAgentCode());
						agent.setPhoneNumber(a.getPhoneNumber());
						agent.setEmailID(a.getEmailID());
						agent.setAddress(a.getAddress());
						agent.setBankName(a.getBankName());
						agent.setAccountNumber(a.getAccountNumber());
						agent.setBankBranchName(a.getBankBranchName());
						agent.setAgentType(a.getAgentType()); 
						agent.setPrimaryKey(a.getPrimaryKey()); 
						agent.setRefEmploy(a.getRefEmploy()); 
						agent.setSelectedCountry(a.getSelectedCountry()); 
						logger.info("Agent Name -------------->"+agent.getName());
						logger.info("Agent Code  ----------->"+agent.getAgentCode());
						logger.info("Agent Phone Number -------------->"+agent.getPhoneNumber());
						logger.info("Agent Type -------------->"+agent.getAgentType());
						logger.info("Agent Pk -------------->"+agent.getPrimaryKey());
						logger.info("Agent Reference Employee -------------->"+agent.getRefEmploy());
					}
				}
			}
			if(requestType.equalsIgnoreCase("employee")) {
								// Later on 
			}			
			
		}catch(Exception e) {
			logger.info("Exception -->"+e.getMessage());
		}finally {
			
		}
	return new ResponseEntity<Agent>(agent, HttpStatus.CREATED);

	}
				
	// Edit Method - Use PUT 
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/setAgentUpdate",method=RequestMethod.POST)
	public ResponseEntity<Agent> setAgentUpdate(@RequestBody Agent agent)
	{
		String reponse ="failure";
		logger.info("Agent Code -------------->"+agent.getAgentCode());
		logger.info("Agent Name -------------->"+agent.getName());
		logger.info("Agent Phone Number -------------->"+agent.getPhoneNumber());
		logger.info("Agent Address -------------->"+agent.getAddress());
		logger.info("Agent Email -------------->"+agent.getEmailID());
		logger.info("Agent Bank Name -------------->"+agent.getBankName());
		logger.info("Agent A/c Number -------------->"+agent.getAccountNumber());
		logger.info("Agent Bank Branch Name -------------->"+agent.getBankBranchName());
		logger.info("Agent Type -------------->"+agent.getAgentType());
		logger.info("Agent Reference Employee -------->"+agent.getRefEmploy()); 
		logger.info("Agent Country -------->"+agent.getSelectedCountry());
		try {
			for(Agent a : agentList) {
				if(a.getPrimaryKey()==agent.getPrimaryKey()) {
					logger.info("Agent Pk both equal  ----------------->"+a.getPrimaryKey() + "  ==  " +agent.getPrimaryKey()); 
					agent.setName(agent.getName());
					agent.setAgentCode(agent.getAgentCode());
					agent.setPhoneNumber(agent.getPhoneNumber());
					agent.setEmailID(agent.getEmailID());
					agent.setAddress(agent.getAddress());
					agent.setBankName(agent.getBankName());
					agent.setAccountNumber(agent.getAccountNumber());
					agent.setBankBranchName(agent.getBankBranchName());
					agent.setAgentType(agent.getAgentType()); 
					agent.setRefEmploy(agent.getRefEmploy()); 
					agent.setSelectedCountry(agent.getSelectedCountry()); 
					reponse = bo.setAgentUpdate(agent);
					agent.setStatus(reponse);
				}
			}			   
	   }catch(Exception e){
			logger.info("Exception -->"+e.getMessage());
			agent.setStatus("failure");
		}finally{
			
		}
	   return new ResponseEntity<Agent>(agent, HttpStatus.CREATED);
	}
			
	// Remove Method - Use DELETE 		
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/setAgentRemove",method=RequestMethod.DELETE)
	public ResponseEntity<Agent> setAgentRemove(int agentPK,String agentCode)
	{
		logger.info("Agent Remove Called....");
		Agent agent = null;//new Agent();
		String response = null;
		try {
			agent = new Agent();
			for(Agent a : agentList) {
				if(a.getPrimaryKey()==agentPK) {
					response = bo.setAgentRemove(a.getPrimaryKey());
				}
			}
			agent.setStatus(response);
		}catch(Exception e) {
			agent.setStatus(response);
					logger.info("Exception -->"+e.getMessage());
		}finally {
			
		}
	return new ResponseEntity<Agent>(agent, HttpStatus.CREATED);
	}		
	
	
	// get the Agent name for drop down list in UI
 	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getAgentName",method=RequestMethod.GET)
	public ResponseEntity<?> getAgentName()
	{
	   try {
		   agentMap = new HashMap<String,Integer>();
			logger.info("Server side Agent Called");
			agList = new ArrayList<String>();
			ArrayList<Agent> agentList = new ArrayList<Agent>();
			agentList = bo.getAgentName(agentList);
		    for(Agent a: agentList){
		    	agentMap.put(a.getAgentCode(),a.getPrimaryKey());
		    	agList.add(a.getName() + "-" +a.getAgentCode());

		    }
			}catch(Exception e){
					logger.info("Exception -->"+e.getMessage());
		}finally{
			
		}
		return new ResponseEntity<ArrayList<String>>(agList, HttpStatus.CREATED);

	} 
		 
		
 	// Load the Employee Name for drop down
 	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getEmployeeName",method=RequestMethod.GET)
	public ResponseEntity<?> getEmployeeName(String empRole)
	{
	   try {
		   empMap = new HashMap<String,Integer>();
		   System.out.println("Employee Role ----------->"+empRole);
			logger.info("Server side Employee Called");
			empList = new ArrayList<String>();
			ArrayList<Employee> empList1 = new ArrayList<Employee>();
			empList1 = bo.getEmpName(empList1,empRole);
		    for(Employee e: empList1){
		    	empMap.put(e.getEmployeeCode(),e.getPrimaryKey());
		    	empList.add(e.getName() + "-" +e.getEmployeeCode());
		    }
			}catch(Exception e){
					logger.info("Exception -->"+e.getMessage());
		}finally{
			
		}
		return new ResponseEntity<ArrayList<String>>(empList, HttpStatus.CREATED);

	} 
		
 	// Load Clinic Name for Drop down 
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getClinicName",method=RequestMethod.GET)
	public ResponseEntity<?> getClinicName()
	{
		logger.info("--------------------- Get Clinic Info ----------------------------");
		clinicNameList=null;
	   try {
		   clinicMap = new HashMap<String,Integer>();

		   logger.info("----------- Server side Clinic Called ------------");
		   clinicNameList =  new ArrayList<String>();
		   ArrayList<Clinic> clinicList = new ArrayList<Clinic>();
		   clinicList = bo.getClinicName(clinicList);
		   for(Clinic c: clinicList){
			   clinicMap.put(c.getClinicCode(),c.getPrimaryKey());
    	       clinicNameList.add(c.getClinicName() + "-" +c.getClinicCode());
		    }
		    	logger.info("Clinic List ---------->"+clinicNameList.size()); 
	   }catch(Exception e) {
		   
	   }finally {
		   
	   }
	   return new ResponseEntity<ArrayList<String>>(clinicNameList, HttpStatus.CREATED);
	}
	
		 	
 	//-------------- Employee Info ------------- 
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getEmployeeInfo",method=RequestMethod.GET)
	public ResponseEntity<?> getEmployeeInfo()
	{
		logger.info("--------------------- Get Employee Info ----------------------------");
		employeeList=null;
	   try {
		   this.employeeList =  new ArrayList<Employee>();
		   this.employeeList = bo.getEmployeeInfo(employeeList);
		 
		    	logger.info("Employee List ---------->"+employeeList.size()); 
	   }catch(Exception e) {
		   
	   }finally {
		   
	   }
	   return new ResponseEntity<ArrayList<Employee>>(employeeList, HttpStatus.CREATED);
	}

	// View Method - Use GET
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getEmployView",method=RequestMethod.GET)
	public ResponseEntity<Employee> getEmployView(int employeePK,String employeeCode,String requestType)  
	{
		logger.info("------------ getEmployeeView -------------->");
		Employee emp =  null;
		try {
			logger.info("Requested Type -------------->"+requestType);
			if(requestType.equalsIgnoreCase("employee")) {
				logger.info("Employee List ----->"+employeeList.size()); 
				emp = new Employee();
				for(Employee e : employeeList) {
					if(e.getPrimaryKey()==employeePK) {
						logger.info("employePK -------------->"+employeePK);
						emp.setName(e.getName());
						emp.setEmployeeCode(e.getEmployeeCode());
						emp.setPhoneNumber(e.getPhoneNumber());
						emp.setEmailID(e.getEmailID());
						emp.setAddress(e.getAddress());
						emp.setEmpType(e.getEmpType());
						emp.setSalary(e.getSalary());
						emp.setPrimaryKey(e.getPrimaryKey());
						emp.setEmpRole(e.getEmpRole()); 
						emp.setRefEmploy(e.getRefEmploy());
						emp.setSelectedCountry(e.getSelectedCountry());
						emp.setJoin_date(e.getJoin_date()); 
						logger.info("Employee Name -------------->"+e.getName());
						logger.info("Employee Code  ----------->"+e.getEmployeeCode());
						logger.info("Employee Phone Number -------------->"+e.getPhoneNumber());
						logger.info("Employee Type -------------->"+e.getEmpType());
						logger.info("Employee Pk -------------->"+e.getPrimaryKey());
						logger.info("Employee Role -------------->"+e.getEmpRole());
					}
				}
			}
		}catch(Exception e) {
					logger.info("Exception -->"+e.getMessage());
		}finally {
			
		}
	return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);

	}
			
	// Edit Method - Use PUT 
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/setEmployeeUpdate",method=RequestMethod.POST)
	public ResponseEntity<Employee> setEmployeeUpdate(@RequestBody Employee emp)
	{
		String reponse ="failure";
		logger.info("Employee Code -------------->"+emp.getEmployeeCode());
		logger.info("Employee Name -------------->"+emp.getName());
		logger.info("Employee Phone Number -------------->"+emp.getPhoneNumber());
		logger.info("Employee Address -------------->"+emp.getAddress());
		logger.info("Employee Email -------------->"+emp.getEmailID());
		logger.info("Employee Salary -------------->"+emp.getSalary());
		logger.info("Employee Type -------------->"+emp.getEmpType());
		logger.info("Selected Country --------->"+emp.getSelectedCountry()); 
		logger.info("Join Date --------->"+emp.getJoin_date()); 
	   try {
			for(Employee e : employeeList) {
					if(e.getPrimaryKey()==emp.getPrimaryKey()) {
						logger.info("Employee Pk both equal  ----------------->"+e.getPrimaryKey() + "  ==  " +emp.getPrimaryKey()); 
						emp.setName(emp.getName());
						emp.setEmployeeCode(emp.getEmployeeCode());
						emp.setPhoneNumber(emp.getPhoneNumber());
						emp.setEmailID(emp.getEmailID());
						emp.setAddress(emp.getAddress());
						emp.setSalary(emp.getSalary());
						emp.setEmpType(emp.getEmpType()); 
						emp.setEmpRole(emp.getEmpRole()); 
						emp.setRefEmploy(emp.getRefEmploy());
						emp.setSelectedCountry(emp.getSelectedCountry()); 
						emp.setJoin_date(emp.getJoin_date());
						reponse = bo.setEmployeeUpdate(emp);
						emp.setStatus(reponse);
					}
				}			   
	   }catch(Exception e){
			logger.info("UI Application Exception -->"+e.getMessage());
			emp.setStatus("failure");
		}finally{
			
		}
	   return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
	}
					
	// Remove Employee Info 				
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/setEmployeeRemove",method=RequestMethod.DELETE)
	public ResponseEntity<Employee> setEmployeeRemove(int employeePK,String employeeCode)
	{
		logger.info("Employee Remove Called....");
		Employee emp = null;//new Agent();
		String response = null;
		try {
			emp = new Employee();
			logger.info(" Primary Key ----->"+employeePK);
			for(Employee e : employeeList) {
				if(e.getPrimaryKey()==employeePK) {
					response = bo.setEmployeeRemove(e.getPrimaryKey());									
				}
			}
				
			emp.setStatus(response);
		}catch(Exception e) {
			emp.setStatus(response);
					logger.info("Exception -->"+e.getMessage());
		}finally {
			
		}	
		return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
	}	
	
	// Save Clinic
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/saveClinic",method=RequestMethod.POST)
	public ResponseEntity<Clinic> saveClinic(@RequestBody Clinic clinic)
	{
	
		String response="failure";
		logger.info("Clinic Name --------------->"+clinic.getClinicName());
		logger.info("Agent drop down Name --------------->"+clinic.getName());
		logger.info("Employee drop down Name --------------->"+clinic.getEmpName());
		try {
			
			// Check Clinic Name exist start
			response = bo.clinicExistCheck(clinic.getClinicName());
			// Check Clinic Name exist end
			if(response!="exit") {
				String agentCode[] = clinic.getName().split("-");
				String empCode[] = clinic.getEmpName().split("-");
				logger.info("After Split Agent Code ---->"+agentCode[1]);
				logger.info("After Split Employee Code ---->"+empCode[1]);
				int value1 = agentMap.get(agentCode[1]);
				int value2 = empMap.get(empCode[1]);
				logger.info("Agent Code Primary Key ----------->"+value1);
				logger.info("Employee Code Primary Key ----------->"+value2);
				
				clinic.setAgentPK(value1);
				clinic.setEmployeePK(value2);
				logger.info("Save Clinic | Before Calling BO---------");
				response = bo.saveClinic(clinic);
				logger.info("Response -------->"+response);
				clinic.setStatus(response);
				
			} else {
				response = "exit";
				clinic.setStatus(response);
			}
			
			
	}catch(Exception e) {
				logger.info("Exception -->"+e.getMessage());
		clinic.setStatus(response);
	}finally {
		
	}
	   return new ResponseEntity<Clinic>(clinic, HttpStatus.CREATED);

	}
	
	// View Method - Use GET
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getClinicView",method=RequestMethod.GET)
	public ResponseEntity<Clinic> getClinicView(int clinicPK,String clinicCode,String requestType)  
	{
		logger.info("------------ getClinicView -------------->");
		Clinic clinic =  null;
		try { 
			logger.info("Requested Type -------------->"+requestType);
			logger.info("Clinic List ----->"+clinicList.size());
			clinic = new Clinic();
			int i=1;
			for(Clinic c : clinicList) {
				if(c.getPrimaryKey()==clinicPK) {
					logger.info("clinicPK -------------->"+clinicPK);
					logger.info("List clinicPK -------------->"+c.getPrimaryKey());
					clinic.setsNo(i);
					clinic.setClinicName(c.getClinicName());
					clinic.setClinicCode(c.getClinicCode());
					clinic.setPhoneNumber(c.getPhoneNumber());
					clinic.setAddress(c.getAddress());
					clinic.setName(c.getName());
					clinic.setEmpName(c.getEmpName());
					clinic.setPrimaryKey(c.getPrimaryKey()); 
					logger.info("Clinic Name -------------->"+clinic.getClinicName());
					logger.info("Clinic Code  ----------->"+clinic.getClinicCode());
					logger.info("Clinic Pk -------------->"+clinic.getPrimaryKey());
					logger.info("Clinic Address --------->"+clinic.getAddress());
					logger.info("Clinic Phone Number --------->"+clinic.getPhoneNumber());
				}
				i++;
			}	
			
		}catch(Exception e) {
					logger.info("Exception -->"+e.getMessage());
		}finally {
			
		}
	return new ResponseEntity<Clinic>(clinic, HttpStatus.CREATED);

	}
						
	// Edit Method - Use PUT 
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/setClinicUpdate",method=RequestMethod.POST)
	public ResponseEntity<Clinic> setClinicUpdate(@RequestBody Clinic clinic)
	{
		logger.info("------------ Inside setClinicUpdate method -------------");
		String reponse ="failure";
		logger.info("Clinic Name -------------->"+clinic.getClinicName());
		logger.info("Clinic Code  ----------->"+clinic.getClinicCode());
		logger.info("Clinic Pk -------------->"+clinic.getPrimaryKey());
		logger.info("Clinic Address --------->"+clinic.getAddress());
		logger.info("Clinic Phone Number --------->"+clinic.getPhoneNumber());
		logger.info("Agent Name -------------->"+clinic.getName());
		logger.info("Employee Name -------------->"+clinic.getEmpName());
	   try {
			for(Clinic c : clinicList) {
				if(c.getPrimaryKey()==clinic.getPrimaryKey()) {
					logger.info("Clinic Pk both equal  ----------------->"+c.getPrimaryKey() + "  ==  " +clinic.getPrimaryKey());							
					String agentCode[] = clinic.getName().split("-");
					String empCode[] = clinic.getEmpName().split("-");
					logger.info("After Split Agent Code ---->"+agentCode[1]);
					logger.info("After Split Employee Code ---->"+empCode[1]);
					int value1 = agentMap.get(agentCode[1]);
					int value2 = empMap.get(empCode[1]);
					logger.info("Agent Code Primary Key ----------->"+value1);
					logger.info("Employee Code Primary Key ----------->"+value2);					
					clinic.setAgentPK(value1);
					clinic.setEmployeePK(value2);							
					clinic.setName(clinic.getName());
					clinic.setClinicCode(clinic.getClinicCode());
					clinic.setPrimaryKey(clinic.getPrimaryKey());
					clinic.setPhoneNumber(clinic.getPhoneNumber());
					clinic.setAddress(clinic.getAddress());
					clinic.setClinicName(clinic.getClinicName());
					clinic.setEmpName(clinic.getEmpName());
					logger.info("Before Calling BO setClinicUpdate");
					reponse = bo.setClinicUpdate(clinic);
					logger.info("Successfully Called BO setClinicUpdate");
					clinic.setStatus(reponse);
				}
			}			   
	   }catch(Exception e){
			logger.info("UI Application Exception -->"+e.getMessage());
			clinic.setStatus("failure");
		}finally{
			
		}
	   return new ResponseEntity<Clinic>(clinic, HttpStatus.CREATED);
	}
					
	// Remove Method - Use DELETE 		
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/setClinicRemove",method=RequestMethod.DELETE)
	public ResponseEntity<Clinic> setClinicRemove(int clinicPK,String clinicCode)
	{
		logger.info("------------ Inside setClinicRemove method -------------");
		Clinic clinic = null;
		String response = null;
		try {
			clinic = new Clinic();
			logger.info("Clinic PK ----->"+clinicPK); 
			for(Clinic c : clinicList) {
				if(c.getPrimaryKey()==clinicPK) {
					logger.info("Before Calling BO setClinicRemove");
					response = bo.setClinicRemove(c.getPrimaryKey());
					logger.info("Successfully Called BO setClinicRemove");
				}
			}
			clinic.setStatus(response);
		}catch(Exception e) {
			clinic.setStatus(response);
			logger.info("Exception -->"+e.getMessage());
		}finally {
			
		}
	return new ResponseEntity<Clinic>(clinic, HttpStatus.CREATED);
	}	
	
	// ------------------- Load Agent Clinic list method for Admin Login --------------------------------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getAgentClinicReport",method=RequestMethod.GET)
	public ResponseEntity<?> getAgentClinicReport(String agentName,String fromDate,String toDate)
		
	{
		 ArrayList<Clinic> allClinicReportList = null;	
		 Clinic clinic = null;
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 try {
				System.out.println("UI From date --->" + fromDate);
				System.out.println("UI To date --->" + toDate);
				java.util.Date fdt = df.parse(fromDate);
				java.sql.Date startDate = new java.sql.Date(fdt.getTime());
				System.out.println("UI startDate ------->"+startDate);
				java.util.Date tdt = df.parse(toDate);
				java.sql.Date endDate = new java.sql.Date(tdt.getTime());
				System.out.println("UI endDate ------->"+endDate);
				
			 	String agentCode[] = agentName.split("-");
				int value1 = agentMap.get(agentCode[1]);
				
			 	allClinicReportList = new ArrayList<Clinic>();
		 		clinic = new Clinic();
		 		clinic.setAgentPK(value1);
		 		clinic.setAgentCode(agentName);
		 		clinic.setFromDate(startDate);
		 		clinic.setToDate(endDate); 
		 		allClinicReportList = bo.getAllClinicReportList(clinic,allClinicReportList);

		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 finally {
			 
		 }
		return new ResponseEntity<ArrayList<Clinic>>(allClinicReportList, HttpStatus.CREATED);
	}
	
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getinvAgentName",method=RequestMethod.GET)
	public ResponseEntity<?> getinvAgentName()
	{
	   try {
		   invagentMap = new HashMap<String,Integer>();
			System.out.println("Server side InvAgent Called");
			invagList = new ArrayList<String>();
			ArrayList<Agent> invagentList = new ArrayList<Agent>();
			invagentList = bo.getinvAgentName(invagentList);
		    for(Agent i: invagentList){
		    	invagentMap.put(i.getAgentCode(),i.getPrimaryKey());
		    	invagList.add(i.getName() + "-" +i.getAgentCode());

		    }
			}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return new ResponseEntity<ArrayList<String>>(invagList, HttpStatus.CREATED);

	} 
	
	// ------------------- Load Agent Investment list method for Admin Login  --------------------------------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getAllInvAgentReport",method=RequestMethod.GET)
	public ResponseEntity<?> getAllInvAgentReport(String agentName,String fromDate,String toDate)
		
	{
	 ArrayList<Member> invReportList = null;
	 Member member = null;
	 SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	 try {
		 	invReportList = new ArrayList<Member>();
	 		member = new Member();
	 		member.setAgentCode(agentName);
	 		member.setBookingdate(ft.parse(fromDate));
	 		member.setInvestmentDate(ft.parse(toDate)); 
	 		invReportList = bo.getAllInvAgentReport(member,invReportList);
	 		
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
	 finally {
		 
	 }
		return new ResponseEntity<ArrayList<Member>>(invReportList, HttpStatus.CREATED);
	}
	
	// ---------------- get Agent Profile view ------------
	 @CrossOrigin(origins = "http://52.24.234.95:80")
	 @RequestMapping(value="/getAgentMyProfile",method=RequestMethod.GET)
	 public ResponseEntity<?> getAgentMyProfile(String primaryKeyStr)
	 {
	 	Agent agent = new Agent();
	 	agent.setUserLoginPrimaryKey(primaryKeyStr); 	 	
	 	agent = bo.getAgentProfile(agent);
		System.out.println("-------------- getAgentMyProfile Called--------------");
	    return new ResponseEntity<Agent>(agent, HttpStatus.CREATED);
	
	 }
	 
	// ---------------- get ClinicAgent Report for Agent Login ------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getClinicAgentReport",method=RequestMethod.GET)
	public ResponseEntity<?> getClinicAgentReport(String agentCode)
	{
		System.out.println("------ getClinicAgentReport ---------"); 
	 	ArrayList<Clinic> clinicreportlist = null;
		try{
	
			clinicreportlist = new ArrayList<Clinic>();
			clinicreportlist = bo.getClinicAgentReport(agentCode,clinicreportlist);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		return new ResponseEntity<ArrayList<Clinic>>(clinicreportlist, HttpStatus.CREATED);
	
	}
	
	// ---------------- get ClinicAgent Report for Agent Login ------------
	@CrossOrigin(origins = "http://52.24.234.95:80")
	@RequestMapping(value="/getInvAgentReport",method=RequestMethod.GET)
	public ResponseEntity<?> getInvAgentReport(String agentCode)
	{
	 	ArrayList<Member> invreportlist = null;	
	 	invreportlist = new ArrayList<Member>();
		invreportlist = bo.getInvAgentReport(agentCode,invreportlist);
		
		return new ResponseEntity<ArrayList<Member>>(invreportlist, HttpStatus.CREATED);
	
	}
	
	// ------------------- Load Member Clinic Report for Admin Login --------------------------------------
		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/getMemberClinicReport",method=RequestMethod.GET)
		public ResponseEntity<?> getMemberClinicReport(String fromDate,String toDate,String name,String empName,String memberName)		
		{
			 ArrayList<Clinic> memberClinicReportList = null;	
			 Clinic clinic = null;
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 try {
				System.out.println("Agent Name ----->"+name);
				System.out.println("Employee Name -------->"+empName);
				System.out.println("Partner Name ------>"+memberName);
				java.util.Date fdt = df.parse(fromDate);
				java.sql.Date startDate = new java.sql.Date(fdt.getTime());
				System.out.println("UI startDate --------->"+startDate);
				java.util.Date tdt = df.parse(toDate);
				java.sql.Date endDate = new java.sql.Date(tdt.getTime());
				System.out.println("UI endDate ------->"+endDate);
				
				memberClinicReportList = new ArrayList<Clinic>();
		 		clinic = new Clinic();
		 		clinic.setFromDate(startDate);
		 		clinic.setToDate(endDate);
		 		clinic.setName(name); 
				clinic.setEmpName(empName);
				clinic.setMemberName(memberName); 
		 		
				if(name.equalsIgnoreCase("All") && empName.equalsIgnoreCase("All")){
					
					System.out.println("--- Both Agent and Employee are Equal to All -----");
					
				}else if(name.equalsIgnoreCase("All")){
					
					String empCode[] = empName.split("-");
					int value2 = empMap.get(empCode[1]);
					logger.info("Employee Code Primary Key ----------->"+value2);
					clinic.setEmployeePK(value2); 
									
				}else if(empName.equalsIgnoreCase("All")){ 
					
					String agentCode[] = name.split("-");
					int value1 = agentMap.get(agentCode[1]);
					logger.info("Agent Code Primary Key ----------->"+value1);
					clinic.setAgentPK(value1);
					
				}else {
					
					String agentCode[] = name.split("-");
					String empCode[] = empName.split("-");
					int value1 = agentMap.get(agentCode[1]);
					int value2 = empMap.get(empCode[1]);
					logger.info("Agent Code Primary Key ----------->"+value1);
					logger.info("Employee Code Primary Key ----------->"+value2);
					clinic.setAgentPK(value1);
					clinic.setEmployeePK(value2);
					
				}

		 		memberClinicReportList = bo.getAllMemberClinicReportList(clinic,memberClinicReportList);
	 
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
			 finally {
				 
			 }
				return new ResponseEntity<ArrayList<Clinic>>(memberClinicReportList, HttpStatus.CREATED);
			}
	
			// ------------------- Load Member Clinic Report for Admin Login --------------------------------------
			@CrossOrigin(origins = "http://52.24.234.95:80")
			@RequestMapping(value="/getMemberReport",method=RequestMethod.GET)
			public ResponseEntity<?> getMemberReport(String fromDate,String toDate,String requestType,String payStatus,String status){
				 ArrayList<Member> memberReportList = null;	
				 Member member = null;
				 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				 try {
						java.util.Date fdt = df.parse(fromDate);
						java.sql.Date startDate = new java.sql.Date(fdt.getTime());
						java.util.Date tdt = df.parse(toDate);
						java.sql.Date endDate = new java.sql.Date(tdt.getTime());
						System.out.println("RequestType ------->"+requestType);
						System.out.println("Status ------>"+status);
						memberReportList = new ArrayList<Member>();
				 		member = new Member();
				 		member.setBookingdate(startDate);
				 		member.setEnrollmentDate(endDate); 
				 		member.setWstatus(payStatus); 
				 		memberReportList = bo.getAllMemberReportList(member,memberReportList,requestType,status);
		 
				 }catch(Exception e) {
					 e.printStackTrace();
				 }
				 finally {
					 
				 }
					return new ResponseEntity<ArrayList<Member>>(memberReportList, HttpStatus.CREATED);
				}
			
			// ------------------- Load Employee Report method for Admin Login --------------------------------------
			@CrossOrigin(origins = "http://52.24.234.95:80")
			@RequestMapping(value="/getEmployeeReport",method=RequestMethod.GET)
			public ResponseEntity<?> getEmployeeReport(String fromDate,String toDate,String empRole)
				
			{
				 ArrayList<Employee> empReportList = null;	
				 Employee emp = null;
				 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				 try {
						java.util.Date fdt = df.parse(fromDate);
						java.sql.Date startDate = new java.sql.Date(fdt.getTime());
						java.util.Date tdt = df.parse(toDate);
						java.sql.Date endDate = new java.sql.Date(tdt.getTime());
						
						empReportList = new ArrayList<Employee>();
				 		emp = new Employee();
				 		emp.setFromDate(startDate);
				 		emp.setToDate(endDate); 
				 		emp.setEmpRole(empRole); 
				 		empReportList = bo.getAllEmpReportList(emp,empReportList);
		 
				 }catch(Exception e) {
					 e.printStackTrace();
				 }
				 finally {
					 
				 }
					return new ResponseEntity<ArrayList<Employee>>(empReportList, HttpStatus.CREATED);
			}
			
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/updateAgentProfile",method=RequestMethod.POST)
			public ResponseEntity<?> updateAgentProfile(@RequestBody Agent agent){
				logger.info("------------ Inside updateAgentProfile method -------------");
				logger.info("[UI]UserLoginPrimary Key ----->"+agent.getUserLoginPrimaryKey());
				logger.info("[UI]Primary Key -->"+agent.getPrimaryKey());
				logger.info("[UI]User Name ----->"+agent.getUsername());
				logger.info("[UI]Agent Name ----->"+agent.getName());
				logger.info("[UI]Agent Address ----->"+agent.getAddress());
				logger.info("[UI]Bank Name ----->"+agent.getBankName());
				logger.info("[UI]Branch Name ----->"+agent.getBankBranchName());
				logger.info("[UI]A/C Number ----->"+agent.getAccountNumber());
				try{
					agent = bo.updateAgentProfile(agent);
					return new ResponseEntity<Agent>(agent , HttpStatus.CREATED);
				}catch(Exception e){
					agent.setStatus("failure");
					e.printStackTrace();
				}
				finally{
					
				}
				return new ResponseEntity<Agent>(agent , HttpStatus.CREATED);
			}
			
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/getEmployeeProfile",method=RequestMethod.GET)
			public ResponseEntity<?> getEmployeeProfile(String primaryKeyStr){
				System.out.println("-------------- getEmployeeProfile Called--------------");
				Employee emp = new Employee();
				emp.setUserLoginPrimaryKey(primaryKeyStr);
				emp = bo.getEmployeeProfile(emp);
				return new ResponseEntity<Employee>(emp , HttpStatus.CREATED);
			}
			
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/updateEmpProfile",method=RequestMethod.POST)
			public ResponseEntity<?> updateEmpProfile(@RequestBody Employee emp){
				System.out.println("---------- Inside updateEmpProfile --------------");
				logger.info("[UI]UserLoginPrimary Key ----->"+emp.getUserLoginPrimaryKey());
				logger.info("[UI]Primary Key -->"+emp.getPrimaryKey());
				logger.info("[UI]User Name ----->"+emp.getUsername());
				logger.info("[UI]Employee Name ----->"+emp.getName());
				logger.info("[UI]Employee Address ----->"+emp.getAddress());
				try{
					emp = bo.updateEmpProfile(emp);
					return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					
				}
				return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
			}
			
			//--------- Employee Report for employee Login --------------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/getEmpReport",method=RequestMethod.GET)
			public ResponseEntity<?> getEmpReport(String employeeCode){
			 	ArrayList<Employee> empreportlist = null;
			 	try{
			 		logger.info("Employee Code ----------->"+employeeCode);
			 		empreportlist = new ArrayList<Employee>();
			 		empreportlist = bo.getEmpReport(employeeCode,empreportlist);				
				}catch(Exception e){
					e.printStackTrace();
				}
				finally{
					
				}
				return new ResponseEntity<ArrayList<Employee>>(empreportlist, HttpStatus.CREATED);
			}
			
			
			//--------- Search Agent ------------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/searchAgent",method=RequestMethod.GET)
			public ResponseEntity<?> searchAgent(String selectedCountry,String agentType,String name,
				String agentCode,String bankName){
				Agent agent = null;
				ArrayList<Agent> searchagList = null;
				try{
					logger.info("Selected Country ----------->"+selectedCountry);
					logger.info("Agent Type ----------->"+agentType);
					logger.info("Agent Name ----------->"+name);
					logger.info("Agent Code ----------->"+agentCode);
					logger.info("Bank Name ----------->"+bankName);
					agent = new Agent();
					searchagList= new ArrayList<Agent>();
					agent.setSelectedCountry(selectedCountry);
					agent.setAgentType(agentType);
					agent.setName(name);
					agent.setAgentCode(agentCode);
					agent.setBankName(bankName);
					searchagList = bo.searchAgent(searchagList,agent);
					
				}catch(Exception e){
					System.out.println("[UI]SearchAgent Exception -------->"+e.getMessage()); 
				}
				finally{
					
				}			
				return new ResponseEntity<ArrayList<Agent>>(searchagList, HttpStatus.CREATED);
			}
			
			//--------- Search Employee ------------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/searchEmployee",method=RequestMethod.GET)
			public ResponseEntity<?> searchEmployee(String selectedCountry,String empType,String name,String employeeCode){
				Employee emp = null;
				ArrayList<Employee> searchempList = null;
				try{
					logger.info("Selected Country ----------->"+selectedCountry);
					logger.info("Employee Type ----------->"+empType);
					logger.info("Employee Name ----------->"+name);
					logger.info("Employee Code ----------->"+employeeCode);
					emp = new Employee();
					searchempList = new ArrayList<Employee>();
					emp.setSelectedCountry(selectedCountry);
					emp.setEmpType(empType);
					emp.setName(name); 
					emp.setEmployeeCode(employeeCode);
					searchempList = bo.searchEmployee(searchempList,emp);
					
				}catch(Exception e){
					System.out.println("[UI]SearchEmployee Exception -------->"+e.getMessage()); 
				}
				finally{
					
				}			
				return new ResponseEntity<ArrayList<Employee>>(searchempList, HttpStatus.CREATED);
			}
			
			//--------- Search Employee ------------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/searchClinic",method=RequestMethod.GET)
			public ResponseEntity<?> searchClinic(String name){
				System.out.println("---------- Inside SearchClinic --------");
				Clinic clinic = null;
				ArrayList<Clinic> searchclinicList = null;
				try{
					searchclinicList = new ArrayList<Clinic>();
					clinic = new Clinic();
					logger.info("Agent Name ----------->"+name);
					/*String agentCode[] = name.split("-");
					int value1 = agentMap.get(agentCode[1]);					
					clinic.setAgentPK(value1);
					System.out.println("Agent Pk ------>"+clinic.getAgentPK()); */
					clinic.setName(name); 
					searchclinicList = bo.searchClinic(searchclinicList,clinic);					
				}catch(Exception e){
					System.out.println("[UI]SearchClinic Exception -------->"+e.getMessage()); 
				}
				finally{
					
				}
				return new ResponseEntity<ArrayList<Clinic>>(searchclinicList, HttpStatus.CREATED);
			}
			
			//---------- searchPartnerClinic --------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/searchPartnerClinic",method=RequestMethod.GET)
			public ResponseEntity<?> searchPartnerClinic(String fromDate,String toDate,String agentCode,String name){
				Clinic clinic = null;
				ArrayList<GLGMem> searchPartnerClinic = null;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try{
					java.util.Date fdt = df.parse(fromDate);
					java.sql.Date startDate = new java.sql.Date(fdt.getTime());
					java.util.Date tdt = df.parse(toDate);
					java.sql.Date endDate = new java.sql.Date(tdt.getTime());					
					clinic = new Clinic();
					searchPartnerClinic = new ArrayList<GLGMem>();
					clinic.setFromDate(startDate);
					clinic.setToDate(endDate);
					if(agentCode.equalsIgnoreCase("All") && name.equalsIgnoreCase("All")){
						System.out.println("-------[UI] AgentCode and EmployeeName All -----------");
						clinic.setAgentCode(agentCode);
						clinic.setName(name); 
						System.out.println("[UI]Agent Code ------>"+clinic.getAgentCode());
						System.out.println("[UI]Employee Code ------>"+clinic.getName());
					}else if(agentCode.equalsIgnoreCase("All")){
						System.out.println("-------[UI] AgentCode only All -----------");
						clinic.setAgentCode(agentCode);
						clinic.setName(name); 
						String empCode[] = name.split("-");
						int value1 = empMap.get(empCode[1]);					
						clinic.setEmployeePK(value1);
						System.out.println("[UI]Agent Code ------>"+clinic.getAgentCode());
						System.out.println("[UI]Employee PK ------>"+clinic.getEmployeePK());
					}else if(name.equalsIgnoreCase("All")){
						System.out.println("-------[UI] EmployeeName only All -----------");
						clinic.setAgentCode(agentCode);
						clinic.setName(name); 
						String agent[] = agentCode.split("-");
						int value2 = agentMap.get(agent[1]);					
						clinic.setAgentPK(value2);
						System.out.println("[UI]Agent PK ------>"+clinic.getAgentPK());
						System.out.println("[UI]Employee Code ------>"+clinic.getName());
					}else{
						System.out.println("-------[UI] Both AgentCode and EmployeeName Not All -----------");
						clinic.setAgentCode(agentCode);
						clinic.setName(name); 
						String agent[] = agentCode.split("-");
						int value2 = agentMap.get(agent[1]);					
						clinic.setAgentPK(value2);
						String empCode[] = name.split("-");
						int value1 = empMap.get(empCode[1]);					
						clinic.setEmployeePK(value1);
						System.out.println("[UI]Agent PK ------>"+clinic.getAgentPK());
						System.out.println("[UI]Employee PK ------>"+clinic.getEmployeePK());
					}
					
					
					searchPartnerClinic = bo.searchPartnerClinic(searchPartnerClinic,clinic);
					
				}catch(Exception e){
					System.out.println("[UI]searchPartnerClinic Exception -------->"+e.getMessage()); 
				}
				finally{
					
				}			
				return new ResponseEntity<ArrayList<GLGMem>>(searchPartnerClinic, HttpStatus.CREATED);
			}
			
			//---------------- getSalesClinicReport -----------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/getSalesClincReport",method=RequestMethod.GET)
			public ResponseEntity<?> getSalesClincReport(String selectedMonth,String reportName,String name){
				System.out.println("-------- Inside getSalesClinicReport ----------");
				ArrayList<Agent> searchMonthlyClinicReport = null;
				try{
					System.out.println("---- Selected Month ----------"+selectedMonth);
					System.out.println("---- Sales Report Name ----------"+reportName);
					System.out.println("Agent Name --------->"+name);
					searchMonthlyClinicReport=new ArrayList<Agent>();
					searchMonthlyClinicReport = bo.getSalesClincReport(searchMonthlyClinicReport,selectedMonth,reportName,name);
				}catch(Exception e){
					System.out.println("[UI]SalesMonthClinicReport ------------>"+e.getMessage());
				}
				finally{
					
				}
				return new ResponseEntity<ArrayList<Agent>>(searchMonthlyClinicReport, HttpStatus.CREATED); 
			}
			
			//---------------- getSalesPartnerReport -----------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/getSalesPartnerReport",method=RequestMethod.GET)
			public ResponseEntity<?> getSalesPartnerReport(String selectedMonth,String reportName,String memberName){
				System.out.println("-------- Inside getSalesPartnerReport ----------");
				ArrayList<Member> searchMonthlyPartnerReport = null;
				try{
					System.out.println("---- Selected Month ----------"+selectedMonth);
					System.out.println("------ Report Name ----------"+reportName);
					System.out.println("Partner Name ---->"+memberName);
					searchMonthlyPartnerReport=new ArrayList<Member>();
					searchMonthlyPartnerReport = bo.getSalesPartnerReport(searchMonthlyPartnerReport,selectedMonth,memberName);
				}catch(Exception e){
					System.out.println("[UI]SalesMonthPartnerReport ------------>"+e.getMessage());
				}
				finally{
					
				}
				return new ResponseEntity<ArrayList<Member>>(searchMonthlyPartnerReport, HttpStatus.CREATED);
			}
			
			//------------- Sales EmployeeManager Report ----------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/getSalesEmployeeReport",method=RequestMethod.GET)
			public ResponseEntity<?> getSalesEmployeeReport(String selectedMonth,String reportName,String empName){
				System.out.println("-------- Inside getSalesEmployeeReport ----------");
				ArrayList<Employee> searchMonthlyEmployeeReport = null;
				try{
					System.out.println("---- Selected Month ----------"+selectedMonth);
					System.out.println("-------- Report Name ----------"+reportName);
					System.out.println("Employee Name ------>"+empName);
					searchMonthlyEmployeeReport=new ArrayList<Employee>();
					searchMonthlyEmployeeReport = bo.getSalesEmployeeReport(searchMonthlyEmployeeReport,selectedMonth,reportName,empName);
				}catch(Exception e){
					System.out.println("[UI]SalesMonth EmployeeReport ------------>"+e.getMessage());
				}
				finally{
					
				}
				return new ResponseEntity<ArrayList<Employee>>(searchMonthlyEmployeeReport, HttpStatus.CREATED);
			}
			
			
			//--------- My Clinic View for Sales ClinicAgent -------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/MyClinicView",method=RequestMethod.GET)
			public ResponseEntity<?> MyClinicView(String agentCode,int primaryKey){
				System.out.println("-------- Inside MyClinicView ----------");
				ArrayList<Clinic> myClinicReport = null;
				Clinic clinic = null;
				try{
					System.out.println("---- Agent Code ----------"+agentCode);
					System.out.println("---- Primary Key ----------"+primaryKey);
					myClinicReport=new ArrayList<Clinic>();
					clinic = new Clinic();
					clinic.setPrimaryKey(primaryKey);
					clinic.setAgentCode(agentCode);
					myClinicReport = bo.getSalesMyClinicReport(myClinicReport,clinic);
				}catch(Exception e){
					System.out.println("[UI]SalesMonth EmployeeReport ------------>"+e.getMessage());
				}
				finally{
					
				}
				return new ResponseEntity<ArrayList<Clinic>>(myClinicReport, HttpStatus.CREATED);
			}
			
			
			//--------- My Employee View for Sales ClinicAgent -------
			@CrossOrigin(origins="http://52.24.234.95:80")
			@RequestMapping(value="/MyEmployeeView",method=RequestMethod.GET)
			public ResponseEntity<?> MyEmployeeView(String refEmploy){
				System.out.println("-------- Inside MyClinicView ----------");
				ArrayList<Employee> myEmpReport = null;
				try{
					System.out.println("---- Reference Employ ----------"+refEmploy);
					myEmpReport=new ArrayList<Employee>();
					myEmpReport = bo.getSalesMyEmployeeView(myEmpReport,refEmploy);
				}catch(Exception e){
					System.out.println("[UI]SalesMonth EmployeeReport ------------>"+e.getMessage());
				}
				finally{
					
				}
				return new ResponseEntity<ArrayList<Employee>>(myEmpReport, HttpStatus.CREATED);
			}
			
		//--------------- ClinicName View on Partner SalesReport -----------  	
		 @CrossOrigin(origins = "http://52.24.234.95:80")
		 @RequestMapping(value="/MyClinicNameView",method=RequestMethod.GET)
		 public ResponseEntity<?> MyClinicNameView(int primaryKey)
		 {
			 Member member = null;
			 try{
				 	member = new Member();
				 	member.setUserLoginPrimaryKey(primaryKey); 			 	
				 	member = bo.MyClinicNameView(member);
					logger.info("-------------- MyClinicNameView Called--------------");
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		 }	
		 
		 
		//--------- My Clinic View for Sales Employee -------
		@CrossOrigin(origins="http://52.24.234.95:80")
		@RequestMapping(value="/MyEmpClinicView",method=RequestMethod.GET)
		public ResponseEntity<?> MyEmpClinicView(String employeeCode,int primaryKey){
			System.out.println("-------- Inside MyEmpClinicView ----------");
			ArrayList<Clinic> myClinicReport = null;
			Clinic clinic = null;
			try{
				System.out.println("---- Employee Code ----------"+employeeCode);
				System.out.println("---- Primary Key ----------"+primaryKey);
				myClinicReport=new ArrayList<Clinic>();
				clinic = new Clinic();
				clinic.setPrimaryKey(primaryKey);
				clinic.setEmployeeCode(employeeCode);
				myClinicReport = bo.getSalesMyEmpClinicReport(myClinicReport,clinic);
			}catch(Exception e){
				System.out.println("[UI]SalesMonth EmployeeReport ------------>"+e.getMessage());
			}
			finally{
				
			}
			return new ResponseEntity<ArrayList<Clinic>>(myClinicReport, HttpStatus.CREATED);
		}
		
		//------------ Get EmployeeAgentView SalesReport------------ 
		@CrossOrigin(origins="http://52.24.234.95:80")
		@RequestMapping(value="/MyEmpAgentView",method=RequestMethod.GET)
		public ResponseEntity<?> MyEmpAgentView(String employeeCode,int primaryKey){
			System.out.println("-------- Inside MyEmpAgentView ----------");
			ArrayList<Agent> myAgentReport = null;
			Agent agent = null;
			try{
				System.out.println("---- Employee Code ----------"+employeeCode);
				System.out.println("---- Primary Key ----------"+primaryKey);
				myAgentReport=new ArrayList<Agent>();
				agent = new Agent();
				agent.setPrimaryKey(primaryKey);
				agent.setRefEmploy(employeeCode); 
				myAgentReport = bo.getSalesMyEmpAgentReport(myAgentReport,agent);
			}catch(Exception e){
				System.out.println("[UI]SalesMonth EmployeeReport ------------>"+e.getMessage());
			}
			finally{
				
			}
			return new ResponseEntity<ArrayList<Agent>>(myAgentReport, HttpStatus.CREATED);
		}
		
			
	 	//--------------  Edit Clinic Name for Drop down ------------
		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/getEditClinicName",method=RequestMethod.GET)
		public ResponseEntity<?> getEditClinicName(String trioNumber)
		{
			logger.info("--------------------- Get Clinic Info ----------------------------");
			clinicNameList=null;
		   try {
			   clinicMap = new HashMap<String,Integer>();

			   logger.info("----------- Server side Clinic List Called ------------");
			   clinicNameList =  new ArrayList<String>();
			   ArrayList<Clinic> clinicList = new ArrayList<Clinic>();
			   clinicList = bo.getEditClinicName(clinicList,trioNumber);
			   for(Clinic c: clinicList){
				   clinicMap.put(c.getClinicCode(),c.getPrimaryKey());
	    	       clinicNameList.add(c.getClinicName() + "-" +c.getClinicCode());
			    }
			    	logger.info("Clinic List ---------->"+clinicNameList.size()); 
		   }catch(Exception e) {
			   
		   }finally {
			   
		   }
		   return new ResponseEntity<ArrayList<String>>(clinicNameList, HttpStatus.CREATED);
		}
		
		//------------- Set Update PertnerClinic on Admin
		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/setPartnerClinicUpdate",method=RequestMethod.POST)
		public ResponseEntity<Member> setPartnerClinicUpdate(@RequestBody Member member)
		{
			logger.info("[UI Application] Member ID --------->"+member.getMemberID()); 
			logger.info("[UI Application] New Clinic Name --------->"+member.getClinicName());
			logger.info("[UI Application] New Clinic Name2 --------->"+member.getClinicName2());
			logger.info("[UI Application] New Clinic Name3 --------->"+member.getClinicName3()); 
			logger.info("[UI Application] New Clinic Name4 --------->"+member.getClinicName4());
			logger.info("[UI Application] New Clinic Name5 --------->"+member.getClinicName5());
			logger.info("[UI Application] New Clinic Name6 --------->"+member.getClinicName6());
			logger.info("[UI Application] New Clinic Name7 --------->"+member.getClinicName7());
			logger.info("[UI Application] New Clinic Name8 --------->"+member.getClinicName8());
			logger.info("[UI Application] New Clinic Name9 --------->"+member.getClinicName9());
			logger.info("[UI Application] New Clinic Name10 --------->"+member.getClinicName10());
			logger.info("[UI Application] No.of Clinics  ---->"+member.getNoofclinics());
			logger.info("---------------Before calling EditClinic() BO------------------");
			try {
				List<String> clinicList = new ArrayList<String>();
				clinicList.add(member.getClinicName());
				clinicList.add(member.getClinicName2());
				clinicList.add(member.getClinicName3());
				clinicList.add(member.getClinicName4());
				clinicList.add(member.getClinicName5());
				clinicList.add(member.getClinicName6());
				clinicList.add(member.getClinicName7());
				clinicList.add(member.getClinicName8());
				clinicList.add(member.getClinicName9());
				clinicList.add(member.getClinicName10());
				clinicList.removeAll(Collections.singleton(null));
				clinicList.removeAll(Collections.singleton(""));
				
				member.clinicPKs = new int[10];
				for(int i=0;i<member.getNoofclinics();i++) {
					String clinicCode[] = clinicList.get(i).split("-");				
					logger.info("After Split Clinic Code ---->"+clinicCode[1]);
					int temp = clinicMap.get(clinicCode[1]);
					member.clinicPKs[i] = temp;
					logger.info("Clinic Primary Key ----------->"+temp);
				}
				logger.info("Before Calling BO addClinic");
				member = bo.setPartnerClinicUpdate(member);  
		   }catch(Exception e){
				logger.info("Exception -->"+e.getMessage());
				member.setStatus("failure");
			}finally{
				
			}
		   return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
		
	// File Upload //
		
		@PostMapping("/paymentUplaod")
		@CrossOrigin(origins = "http://52.24.234.95:80")
	//	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,String ) {
			//String memberID=null;
		public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file , @RequestParam("memberID") String memberID ) {
			logger.info("-------- TRIO File upload-------");
			logger.info("-------- TRIO Member Number -------"+memberID);

			String message = "";
			try {
				String status = storageService.store(file,memberID);	
				if(status.equalsIgnoreCase("Success")){
					bo.UpdatePayment(memberID);
				}else if(status.equalsIgnoreCase("failure")){
					logger.info("---- Failed to Upload File ------");
				}
				files.add(file.getOriginalFilename());
				message = "You successfully uploaded " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				message = "FAIL to upload " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}
		
		@GetMapping("/getallfiles")
		@CrossOrigin(origins = "http://52.24.234.95:80")
		public ResponseEntity<List<String>> getListFiles(Model model,String memberID) {
			logger.info("------- Inside getListFiles Method ---------");
			logger.info("-- MemberID --->"+memberID); 
			String fileName1 = memberID + ".jpg";
			
			List<String> fileNames = storageService.loadAll().map(fileName -> MvcUriComponentsBuilder
	                .fromMethodName(UiApplication.class, "getFile", fileName1).build().encode().toString())
	                .collect(Collectors.toList());
			
			return ResponseEntity.ok().body(fileNames); 
		}

		@GetMapping("/files/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> getFile(@PathVariable String filename) {
			logger.info("------- Inside getFile Method ---------");
			Resource file = storageService.loadFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		}
		
		//------------- getPartner Name ---------
		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/getPartnerName",method=RequestMethod.GET)
		public ResponseEntity<?> getPartnerName()
		{
		   try {
			   	partnerMap = new HashMap<String,Integer>();
				logger.info("------ Server side Partner Called -------");
				partnerList = new ArrayList<String>();
				ArrayList<Member> parList = new ArrayList<Member>();
				parList = bo.getPartnerName(parList);
			    for(Member b: parList){
			    	partnerMap.put(b.getMemberID(),b.getUserLoginPrimaryKey());
			    	partnerList.add(b.getFirstName() + "-" + b.getMemberID());
	
			    }
			}catch(Exception e){
					logger.info("Exception -->"+e.getMessage());
			}finally{
				
			}
			return new ResponseEntity<ArrayList<String>>(partnerList, HttpStatus.CREATED);

		} 
		
		//------------- MemberId Validation for Payment Upload ---------
		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/getMemberIDValidate",method=RequestMethod.GET)
		public ResponseEntity<?>  getMemberIDValidate(@RequestParam String memberID) {
			boolean memberIdResponse = false;
			try {
				user = new User();
				logger.info("Member ID  -->"+memberID);
				logger.info("Before Calling BO");
				memberIdResponse = storageService.getMemberIDValidate(memberID);
				logger.info("Response ------------------>"+memberIdResponse);
				logger.info("Successfully Called BO -->");
				if(memberIdResponse==true) {
					user.setStatus("Valid");
				} 
				if(memberIdResponse==false) {
					user.setStatus("InValid");
				}

			}catch(Exception e) {
				user.setStatus("Network Error Please try again");
			}finally {
				
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		  }
		
		//---------- Search All Partner Module -----------
		@CrossOrigin(origins = "http://52.24.234.95:80")
		@RequestMapping(value="/searchAllPartner",method=RequestMethod.GET)
		public ResponseEntity<?> searchAllPartner(String selectedCountry,String memberName,String payStatus,String agentCode){
			GLGMem member = null;
			ArrayList<GLGMem> searchpartnerList = null;
			try{
				logger.info("Selected Country ----------->"+selectedCountry);
				logger.info("Member Name ----------->"+memberName);
				logger.info("Payment Status ----------->"+payStatus);
				logger.info("Agent Code ----------->"+agentCode);
				member = new GLGMem();
				searchpartnerList= new ArrayList<GLGMem>();
				
				if(memberName.equalsIgnoreCase("All") && agentCode.equalsIgnoreCase("All")){			
					member.setMemberID(memberName);
					member.setAgentCode(agentCode);
				}else if(memberName.equalsIgnoreCase("All")){
					member.setMemberID(memberName);
					String agentId[] = agentCode.split("-");
					logger.info("Agent Code ----------->"+agentId[1]);					
					member.setAgentCode(agentId[1]);
				}else if(agentCode.equalsIgnoreCase("All")){
					member.setAgentCode(agentCode);
					String memberId[] = memberName.split("-");
					logger.info("Memebr Code ----------->"+memberId[1]);					
					member.setMemberID(memberId[1]);
				}else{
					String memberId[] = memberName.split("-");
					logger.info("Memebr Code ----------->"+memberId[1]);					
					member.setMemberID(memberId[1]);
					String agentId[] = agentCode.split("-");
					logger.info("Agent Code ----------->"+agentId[1]);					
					member.setAgentCode(agentId[1]);
				}	

				member.setSelectedCountry(selectedCountry);
				member.setStatus(payStatus);
				searchpartnerList = bo.searchAllPartner(searchpartnerList,member);
				
			}catch(Exception e){
				System.out.println("[UI]SearchAgent Exception -------->"+e.getMessage()); 
			}
			finally{
				
			}			
			return new ResponseEntity<ArrayList<GLGMem>>(searchpartnerList, HttpStatus.CREATED);
		}
		
		//---------- search Not Registered Clinic On Partner --------
		@CrossOrigin(origins="http://52.24.234.95:80")
		@RequestMapping(value="/searchPartnerNonClinic",method=RequestMethod.GET)
		public ResponseEntity<?> searchPartnerNonClinic(String fromDate,String toDate,String agentCode){
			GLGMem glgmember = null;
			ArrayList<GLGMem> searchPartnerNonClinic = null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try{
				java.util.Date fdt = df.parse(fromDate);
				java.sql.Date startDate = new java.sql.Date(fdt.getTime());
				java.util.Date tdt = df.parse(toDate);
				java.sql.Date endDate = new java.sql.Date(tdt.getTime());					
				glgmember = new GLGMem();
				searchPartnerNonClinic = new ArrayList<GLGMem>();
				glgmember.setFromDate(startDate);
				glgmember.setToDate(endDate);
				if(agentCode.equalsIgnoreCase("All")){
					System.out.println("-------[UI] AgentCode All -----------");
					glgmember.setAgentCode(agentCode);
					System.out.println("[UI]Agent Code ------>"+glgmember.getAgentCode());
				}else{
					System.out.println("-------[UI] Both AgentCode and EmployeeName Not All -----------");
					String agent[] = agentCode.split("-");
					glgmember.setAgentCode(agent[1]);
					System.out.println("[UI]Agent PK ------>"+glgmember.getAgentCode());
				}				
				searchPartnerNonClinic = bo.searchPartnerNonClinic(searchPartnerNonClinic,glgmember);			
			}catch(Exception e){
				System.out.println("[UI]searchPartnerClinic Exception -------->"+e.getMessage()); 
			}
			finally{
				
			}			
			return new ResponseEntity<ArrayList<GLGMem>>(searchPartnerNonClinic, HttpStatus.CREATED);
		}
		
}

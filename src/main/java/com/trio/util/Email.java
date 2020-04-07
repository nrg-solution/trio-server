package com.trio.util;

/*import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trio.dto.Agent;
import com.trio.dto.Employee;
import com.trio.dto.Member;
import com.trio.dto.User;




public class Email {
	public static final Logger logger = LoggerFactory.getLogger(Email.class);

	/*static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL_EMAIL = "jdbc:mysql://localhost:3306/PollerDB";//local Linux , It is for only email not sms	
	static final String USER = "root"; //database.getString("USER");
	static final String PASS = "root";//database.getString("PASSWORD");
	public static Connection con = null; 
	public static Statement stmt=null;
	public static PreparedStatement preparedStatement=null; */

	public static String adminMailID="trioinovasiteknologi@gmail.com";
	
	/*
	public static Connection pushEmail() {
		//boolean status = false;
		try {
			Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			con.createStatement();

		return con;
		} catch(Exception e) {
			return con;
		}finally {
			
		}
	}
	*/
public static void optMailsend(User user) {
		
		/*String otpEmailBody ="<html> <head> <style> </style> </head>"
			+"<body lang=EN-US style='tab-interval:.5in'> <div class=Section1> <p class=MsoNormal align=center style='text-align:center'><b><u><span"
			+"style='font-size:26.0pt;line-height:115%;color:black'>GLG OTP Notification<p></p></span></u></b></p>"
			+"<p> Your One-Time-Password (OTP) is "+user.getOtp()+" . Please enter this password online to complete the pass word rest with your Trio Technology Account. Validity 5 min.</p><p> For more information on your accounts, please login to Trio Technology Personal Internet Account by visiting trio-i.com or the Trio Technology Mobile App from your Smartphone.</p> <p> ----------------------------------------------------------------------------------------------------------------------------------------------------"
			+"</p>"
			
			+"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>"
			+"<p>For and on behalf of<p></p></span></b></p>" 
			+"<p>Trio Technology Innovation</span></b></p>"
			+"</div> </body> </html>";*/
			
		String otpEmailBody ="<html> <head> <style> </style> </head>"
				+"<body lang=EN-US style='tab-interval:.5in'><a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNormal align=center style='text-align:center'><b><u><span"
				+"style='font-size:26.0pt;line-height:115%;color:black'>GLG OTP Notification<p></p></span></u></b></p>"
				+"<p> Your One-Time-Password (OTP) is "+user.getOtp()+" . Please enter this password online to complete the pass word rest with your Trio Technology Account. Validity 5 min.</p><p> For more information on your accounts, please login to Trio Technology Personal Internet Account by visiting hims-c.com or the Trio Technology Mobile App from your Smartphone.</p> <p> ----------------------------------------------------------------------------------------------------------------------------------------------------"
				+"</p>"		
				
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
				+"TRIO Management  <span style='mso-spacerun:yes'></span></span>"
				+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
				+"115%'><o:p></o:p></span></p>"
				
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
				+"Please feel free to touch with us at &nbsp;<span style='mso-spacerun:yes'></span><a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
				+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
				+"115%'><o:p></o:p></span></p>"
				
				+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
				+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
				+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
				+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
				+"115%'><o:p></o:p></span></p>"
				
				+"</div><br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n"  
				+"<br/><p><font size=\"2\">\r\n" 
				+"You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
				+"</font></p></body> </html>";
		
		try {
				/*con=pushEmail();
				preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
				preparedStatement.setString(1, user.getEmail_ID());
				preparedStatement.setString(2, "Trio OPT Request"); 
				preparedStatement.setString(3, otpEmailBody);// Message
				preparedStatement.setString(4, "TRIO");
				preparedStatement.executeUpdate();	*/
				
				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(user.getEmail_ID(),"TRIO OTP Request",otpEmailBody);
				logger.info("Successfully Called ------------");
				
				logger.info("Successfully Saved data.");
			}catch(Exception e) {
				logger.error("Exception -->"+e.getMessage());
			}
		
		}	
	
	// ----------------- Registration Member Notification --------------
	public static boolean RegisterMail(Member member,String newCodeFinal){
		logger.info("Inside RegisterMail() Method()----------------------------");
		boolean status =false;
		logger.info("Sender Email -->"+member.getEmailID());
		logger.info("Payment amount -->"+20000000*member.getNoofclinics());
		logger.info("No.of Clinic -->"+member.getNoofclinics());
		logger.info("Member type -->"+member.getActType());
		logger.info("Member user name -->"+member.getUsername());
		logger.info("Member password -->"+member.getPassword());
		
		
		String emailTemplate1="<html> <head> <style> div {  background-color: white; }</style> </head>\r\n" + 
				"<body lang=EN-US style='tab-interval:.5in'> \r\n" + 
				"<a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a>\r\n" + 
				"<br/><div><br/><p class=MsoNormal align=center style='text-align:center'><b><span\r\n" + 
				"style='font-size:20.0pt;line-height:115%;color:blue'>TRIO REGISTRATION ACKNOWLEDGEMENT EMAIL <p></p></span></b></p>\r\n" + 
				"<p> Dear TRIO Member,</p> <p> Thanks for register with TRIO Website and your details are in below </p>\r\n" + 
				"\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\">Member ID:</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+newCodeFinal+"</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Investment Date: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+"</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\">Member Status: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getStatus()+"</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> No.of Clinic:</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  "+member.getNoofclinics()+"</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				"	\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Amount in IDR:</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+20000000*member.getNoofclinics()+"</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				"	\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\">Login user name: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getUsername()+"</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Login password: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getPassword()+"</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"<br/><br/>	\r\n" + 
				"<p class=MsoNoSpacing align=center style='text-align:center';\r\n" + 
				"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'>PAY ACCOUNT DETAILS ARE IN BELOW</span></u><b style='mso-bidi-font-weight:normal'><u><span\r\n" + 
				"style='font-size:12.0pt;mso-fareast-font-family:\\\"Times New Roman\\\";mso-fareast-theme-font:\"\r\n" + 
				"minor-fareast;mso-fareast-language:ZH-CN'></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'></p>\r\n" + 
				"\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Account holder Name: </label> &nbsp;&nbsp;&nbsp;&nbsp; PT Trio Inovasi Teknologi</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Bank Name: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bank Central Asia (BCA)</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Account Number: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0353555330</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
								
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Branch Name: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; chase plaza</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Address: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Jl.Jendral Sudirman kav 21.Jakarta 12920</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"<label style=\"color:#5D6D7E;\"> Swift code: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; CENAIDJA</span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"\r\n" + 
				
				
				
				"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
				"Please use the Payment Link to upload the Payment Link &nbsp;&nbsp;&nbsp;&nbsp; <a href=\"http://trio.neotural.com/paymentupload\" style='color:blue;'>PAYMENT UPLOAD</a></span>\r\n" + 
				"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
				"<br/><br/>			\r\n" + 
				"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>\r\n" + 
				"<p>Thanks and regards,<p></p></span></b></p><p>TRIO Management Alert</span></b></p><p>Please feel free to touch with us at 	customer.service@trio-i.com</span></b></p><p>Please visit our website <a href=\"http://hims-c.com/\">http://hims-c.com/</a>  \r\n" + 
				"</span></b></p><br/><br/></div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" + 
				"<br/><p><font size=\"2\">\r\n" + 
				"You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n" + 
				"</font></p></body> </html>";
		try {
			/*Class.forName(JDBC_DRIVER);
			con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
			stmt=con.createStatement();
			preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
			preparedStatement.setString(1, member.getEmailID());
			preparedStatement.setString(2, "FOR TRIO MEMBER REGISTRATION ACKNOWLEDGEMENT"); 
			preparedStatement.setString(3, emailTemplate1);// Message
			preparedStatement.setString(4, "TRIO");
			preparedStatement.execute();	*/
			
			logger.info("Calling Email Service -------------");
			PushEmail.sendMail(member.getEmailID(),"FOR TRIO MEMBER REGISTRATION ACKNOWLEDGEMENT",emailTemplate1);
			logger.info("Successfully Called ------------");
			
			logger.info("Successfully Saved data.");
			status=true;
		return status;
		}

		 catch(Exception e){
			// return status;
			 logger.info("Exception -->"+e.getMessage());
			 return status;
		}
	}
	
	// ----------------- adminalertEmail Notification --------------
		public static void adminalertEmail(Member member,String newCodeFinal){
			logger.info("Inside adminalertEmail() Method()----------------------------");

			
			String emailTemplate2="<html> <head> <style> div {  background-color: white; }</style> </head>\r\n" + 
					"<body lang=EN-US style='tab-interval:.5in'> \r\n" + 
					"<a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a>\r\n" + 
					"<br/><div><br/><p class=MsoNormal align=center style='text-align:center'><b><span\r\n" + 
					"style='font-size:20.0pt;line-height:115%;color:blue'>TRIO NEW MEMBER REGISTRATION ALERT <p></p></span></b></p>\r\n" + 
					"<p> Dear TRIO Admin,</p> <p> You have alert for New Member registration and Member details in below </p>\r\n" + 
					"\r\n" + 
					"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
					"<label style=\"color:#5D6D7E;\">Member ID:</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+newCodeFinal+"</span>\r\n" + 
					"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
					"\r\n" + 
					"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
					"<label style=\"color:#5D6D7E;\"> Investment Date: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+"</span>\r\n" + 
					"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
					"<label style=\"color:#5D6D7E;\"> Investment amt: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+20000000*member.getNoofclinics()+"</span>\r\n" + 
					"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
					"\r\n" + 
					"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
					"<label style=\"color:#5D6D7E;\"> No.of Clinic:</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getNoofclinics()+"</span>\r\n" + 
					"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
					"	\r\n" + 
					"<br/><br/>	\r\n" + 
					"\r\n" + 
					"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
					"Please approve &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"http://trio.neotural.com/login\" style='color:blue;'>TRIO LOGIN</a></span>\r\n" + 
					"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
					"<br/><br/>			\r\n" + 
					"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>\r\n" + 
					"<p>Thanks and regards,<p></p></span></b></p><p>TRIO Management Alert</span></b></p><p>Please feel free to touch with us at 	customer.service@trio-i.com</span></b></p><p>Please visit our website <a href=\"http://hims-c.com/\">http://hims-c.com/</a>  \r\n" + 
					"</span></b></p><br/><br/></div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" + 
					"<br/><p><font size=\"2\">\r\n" + 
					"You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n" + 
					"</font></p></body> </html>";
			try {
				/*Class.forName(JDBC_DRIVER);
				con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
				stmt=con.createStatement();
				preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");

				preparedStatement.setString(1, adminMailID); // Admin Email ID
				preparedStatement.setString(2, "TRIO NEW MEMBER REGISTRATION ALERT"); 
				preparedStatement.setString(3, emailTemplate2);// Message 
				preparedStatement.setString(4, "TRIO");
				//preparedStatement.executeUpdate();	
				preparedStatement.execute();	*/

				logger.info("Calling Email Service -------------");
				PushEmail.sendMail(adminMailID,"TRIO NEW MEMBER REGISTRATION ALERT",emailTemplate2);
				logger.info("Successfully Called ------------");
				
				logger.info("Successfully Saved data.");
			
			}

			 catch(Exception e){
				 logger.info("Exception -->"+e.getMessage());
			}
		}
		
		// ----------------- Payment Approval Notification --------------
				public static void PaymentApproveMail(User user){
					

					String emailTemplate3 ="<html>"
							+"<head></head>"
							
							+"<br/><br/>"

							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'><a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO PAYMENT UPLOAD APPROVAL EMAIL</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Member, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Admin have successfully approved the Payment and Approval details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+user.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Approved Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+user.getAcct_Approved_date()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"We will send the Clinic registration Notification to your registered Email address Please check your email </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+" </div>"
							+ "<br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";		

					try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");

						preparedStatement.setString(1, user.getEmail_ID());
						preparedStatement.setString(2, "TRIO ADMIN IS APPROVED YOUR PAYMENT"); 
						preparedStatement.setString(3, emailTemplate3);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();	*/
						
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(user.getEmail_ID(),"TRIO ADMIN IS APPROVED YOUR PAYMENT",emailTemplate3);
						logger.info("Successfully Called ------------");
						
						
						logger.info("Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.info("Exception -->"+e.getMessage());
					}
				}

				// ----------------- Payment Rejection Notification --------------
				public static void PaymentRejectMail(User user){
					logger.info("Member ID -------->"+user.getMemberID());
					logger.info("Rejection Date -------->"+user.getAcct_Approved_date());
					logger.info("Email ID -------->"+user.getEmail_ID());					

					String emailTemplate4 ="<html>"
							+"<head></head>"
							
							+"<br/><br/>"

							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'><a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO PAYMENT UPLOAD REJECT EMAIL</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Member, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Admin have rejected the Payment and Approval details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+user.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Rejected Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+user.getAcct_Approved_date()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"We will send the Rejected Reason Notification to your registered Email address Please check your email </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+" </div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";		

					try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");

						preparedStatement.setString(1, user.getEmail_ID());
						preparedStatement.setString(2, "TRIO ADMIN IS REJECTED YOUR PAYMENT"); 
						preparedStatement.setString(3, emailTemplate4);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();	*/
						
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(user.getEmail_ID(),"TRIO ADMIN IS REJECTED YOUR PAYMENT",emailTemplate4);
						logger.info("Successfully Called ------------");
						
						logger.info("Email Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.info("Exception -->"+e.getMessage());
					}
				}
				
				// ----------------- Add Clinic Registration --------------
				public static void AddClinicMail(Member member){
					logger.info("Member ID -------->"+member.getMemberID());
					logger.info("Enrollment  Date -------->"+member.getBookingdate());
					logger.info("Email ID -------->"+member.getEmailID());
					
		
					String emailTemplate5 ="<html>"
							+"<head></head>"
							
							+"<br/><br/>"

							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'><a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO CLINIC REGISTER CONFIRMATION EMAIL</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Member, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Your Account is successfully activated and please check details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Enrollment Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getBookingdate()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please login to system, we have shown the Ledger Information and Withdraw details </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+" </div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";

					try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");

						preparedStatement.setString(1, member.getEmailID());
						preparedStatement.setString(2, "TRIO ADMIN IS REGISTERED CLINIC SUCCESSFULLY"); 
						preparedStatement.setString(3, emailTemplate5);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();*/	
						
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(member.getEmailID(),"TRIO ADMIN IS REGISTERED CLINIC SUCCESSFULLY",emailTemplate5);
						logger.info("Successfully Called ------------");
						
						
						logger.info("Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.info("Exception -->"+e.getMessage());
						//e.printStackTrace();
					}
				}
				
				// ----------------- Withdraw Request for member --------------
				public static void WithdrawrequestMail(Member member){
					logger.info("Member ID -------->"+member.getMemberID());
					logger.info("Withdraw  Date -------->"+member.getWithdrawDate());
					logger.info("Withdraw  Amount -------->"+member.getWithdrawAmount());
					logger.info("Email ID -------->"+member.getEmailID());
					
					
					String emailTemplate6 ="<html>"
							+"<head></head>"
							
							+"<br/><br/>"

							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'> <a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO WITHDRAW REQUEST ACKNOWLEDGEMENT</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Member, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"You have successfully requested for Withdraw and please check details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Withdraw Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+member.getWithdrawDate()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Withdraw Amount &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+member.getWithdrawAmount()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'></span></p>"			
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please login to system, we have shown the Ledger Information and Withdraw details </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'></span></p>"
							
							+" </div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";
						try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");

						preparedStatement.setString(1, member.getEmailID());
						preparedStatement.setString(2, "TRIO ACKNOWLEDGEMENT FOR WITHDRAWAL REQUEST"); 
						preparedStatement.setString(3, emailTemplate6);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();	*/
						
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(member.getEmailID(),"TRIO ACKNOWLEDGEMENT FOR WITHDRAWAL REQUEST",emailTemplate6);
						logger.info("Successfully Called ------------");
						
						logger.info("Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.info("Exception -->"+e.getMessage());
					}
				}
				
				// -----------------Withdraw Alert for Admin  --------------
				public static void WithdrawalertMail(Member member){
					logger.info("Member ID -------->"+member.getMemberID());
					logger.info("Withdraw  Date -------->"+member.getWithdrawDate());
					logger.info("Withdraw  Amount -------->"+member.getWithdrawAmount());
					logger.info("Email ID -------->"+member.getEmailID());
					
				
					String emailTemplate7 ="<html>"
							+"<head></head>"
							
							+"<br/><br/>"

							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'> <a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO WITHDRAW REQUEST EMAIL ALERT</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Admin,  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"You have one Withdraw request in the system Please find the details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Withdraw Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getWithdrawDate()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Withdraw Amount &nbsp;&nbsp;&nbsp; "+member.getWithdrawAmount()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"			
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Kindly Approve </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+" </div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";
					try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
						preparedStatement.setString(1, adminMailID);
						preparedStatement.setString(2, "TRIO NOTIFICATION FOR WITHDRAWAL REQUEST"); 
						preparedStatement.setString(3, emailTemplate7);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();	*/
						
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(adminMailID,"TRIO NOTIFICATION FOR WITHDRAWAL REQUEST",emailTemplate7);
						logger.info("Successfully Email Called ------------");
						
						
						logger.info("Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.error("Exception -->"+e.getMessage());
					}
				}
				
				//------------ Withdraw Approve for Member  ---------
				public static void WithdrawapproveMail(User user){
					logger.info("Member ID -------->"+user.getMemberID());
					logger.info("Withdraw  Date -------->"+user.getAcct_Approved_date());
					logger.info("Withdraw  Amount -------->"+user.getWithdrawAmount());
					logger.info("Email ID -------->"+user.getEmail_ID());
					

					String emailTemplate8 ="<html>"
							+"<head></head>"
							
							+"<br/><br/>"

							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'> <a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO WITHDRAW APPROVAL EMAIL</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Member, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Admin have successfully approved the Withdraw and Approval details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+user.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Approval Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+user.getAcct_Approved_date()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"	
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Withdrawal Amount &nbsp;&nbsp;&nbsp;&nbsp;"+user.getWithdrawAmount()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"	
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Money credited to your account another 7 working days </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+" </div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";	

					try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
						preparedStatement.setString(1, user.getEmail_ID());
						preparedStatement.setString(2, "TRIO ADMIN IS APPROVED FOR YOUR WITHDRAWAL"); 
						preparedStatement.setString(3, emailTemplate8);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();	
						*/
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(user.getEmail_ID(),"TRIO ADMIN IS APPROVED FOR YOUR WITHDRAWAL",emailTemplate8);
						logger.info("Successfully Email Called ------------");
						
						logger.info("Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.error("Exception -->"+e.getMessage());
					}
				}

				//------------ Withdraw Rejection for Member  ---------
				public static void WithdrawrejectMail(User user){
					logger.info("Member ID -------->"+user.getMemberID());
					logger.info("Withdraw  Date -------->"+user.getAcct_Approved_date());
					logger.info("Email ID -------->"+user.getEmail_ID());
					
					String emailTemplate9 ="<html>"
							+"<head></head>"
							
							+"<br/><br/>"

							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'> <a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO WITHDRAW REJECT EMAIL</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Member, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Admin have rejected the Withdrawal and Approval details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+user.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Rejected Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+user.getAcct_Approved_date()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"		
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"We will send the Rejected Reason Notification to your registered Email address Please check your email </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+" </div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";
					try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");

						preparedStatement.setString(1, user.getEmail_ID());
						preparedStatement.setString(2, "TRIO ADMIN REJECTED YOUR WITHDRAWAL REQUEST"); 
						preparedStatement.setString(3, emailTemplate9);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();	*/
						
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(user.getEmail_ID(),"TRIO ADMIN REJECTED YOUR WITHDRAWAL REQUEST",emailTemplate9);
						logger.info("Successfully Email Called ------------");
						
						
						logger.info("Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.info("Exception -->"+e.getMessage());
					}
				}
				//------------ Payment Confirmation Mail for Member  ---------
				public static void PaymentConfirmationMail(Member member){
					logger.info("Member ID -------->"+member.getMemberID());
					logger.info("Email ID -------->"+member.getEmailID());
						String emailTemplate10 ="<html>"
							+"<head></head>"						
							+"<br/><br/>"
							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'> <a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO PAYMENT UPLOAD CONFIRMATION EMAIL</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Member, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"You have successfully uploaded the Payment and Payment details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Payment Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"		
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"We will send the Approval Notification to your registered Email address, Please check your email </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our Website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+" </div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";
					try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
						preparedStatement.setString(1, member.getEmailID());
						preparedStatement.setString(2, "TRIO PAYMENT CONFIRMATION"); 
						preparedStatement.setString(3, emailTemplate10);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();*/	
						
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(member.getEmailID(),"TRIO PAYMENT CONFIRMATION",emailTemplate10);
						logger.info("Successfully Email Called ------------");
						
						logger.info("Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.error("Exception -->"+e.getMessage());
					}
				}
				
				//------------ Payment Alert for Member  ---------
				public static void Paymentadminalert(Member member){
					logger.info("Member ID -------->"+member.getMemberID());
					logger.info("Payment Date -------->"+Custom.getCurrentDate());
					

					String emailTemplate11 ="<html>"
							+"<head></head>"
							
							+"<br/><br/>"

							+"<body lang=EN-US link=\"#0563C1\"  vlink=\"#954F72\" style='tab-interval:.5in;padding-left:50px;'> <a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a> <div class=Section1> <p class=MsoNoSpacing align=center style='text-align:center'><b"
							+"style='mso-bidi-font-weight:normal'><u><span style='font-size:12.0pt'><b>TRIO PAYMENT ALERT EMAIL</b></span></u></b><b style='mso-bidi-font-weight:normal'><u><span"
							+"style='font-size:12.0pt;mso-fareast-font-family:\"Times New Roman\";mso-fareast-theme-font:"
							+"minor-fareast;mso-fareast-language:ZH-CN'><o:p></o:p></span></u></b></p> <p class=MsoNoSpacing style='text-align:justify;text-justify:inter-ideograph'><o:p>&nbsp;</o:p></p>"
							
							+"<br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Dear Admin, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"You have alert for New Member Payment Uploaded and Payment details in below </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Member ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getMemberID()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Payment Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"	
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Paid Amount  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getPayAmt()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"	
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"No.of Clinic &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getNoofclinics()+"</span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<br/><br/>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Kindly approve </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"

							+"<br/><br/><br/><br/><br/><br/>"

							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Thanks and regards, </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"TRIO Management Alert  </span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please feel free to touch with us at &nbsp; <a href=\"http://trio-i.com/\" style='color:blue;'>customer.service@trio-i.com</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span"
							+"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>"
							+"Please visit our website &nbsp; <a href=\"http://hims-c.com/\" style='color:blue;'>http://hims-c.com/</a></span>"
							+"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:"
							+"115%'><o:p></o:p></span></p>"
							
							+" </div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" 
							+ "<br/><p><font size=\"2\">\r\n"  
							+ "You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n"  
							+ "</font></p></body> </html>";

					try {
						/*Class.forName(JDBC_DRIVER);
						con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
						stmt=con.createStatement();
						preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
						preparedStatement.setString(1, adminMailID);
						preparedStatement.setString(2, "TRIO MEMBER PAID NOTIFICATION"); 
						preparedStatement.setString(3, emailTemplate11);// Message
						preparedStatement.setString(4, "TRIO");
						preparedStatement.executeUpdate();	*/
						
						logger.info("Calling Email Service -------------");
						PushEmail.sendMail(adminMailID,"TRIO MEMBER PAID NOTIFICATION",emailTemplate11);
						logger.info("Successfully Email Called ------------");
						
						logger.info("Successfully Saved data.");
					
					}

					 catch(Exception e){
						 logger.error("Exception -->"+e.getMessage());
					}
				}
				
				// ----------------- Registration Agent Notification --------------
				public static boolean RegisterAgentMail(Agent agent){
					logger.info("------------- Inside RegisterAgentMail Method() ----------------");
					boolean status =false;
					logger.info("Sender Email -->"+agent.getEmailID());
					logger.info("Agent Code -->"+agent.getAgentCode());
					
					String emailTemplate12="<html> \r\n" + 
							"<body style='tab-interval:.5in'> \r\n" + 
							"<a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a>\r\n" + 
							"<br/><div><br/><p class=MsoNormal align=center style='text-align:center'><b><span\r\n" + 
							"style='font-size:20.0pt;line-height:115%;color:blue'>TRIO AGENT REGISTRATION ACKNOWLEDGEMENT EMAIL <p></p></span></b></p>\r\n" + 
							"<p> Dear TRIO Agent,</p> <p> Your profile has been created in trio system and and please find the  details are in below </p>\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label>Agent Code:</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'> </span>"+agent.getAgentCode()+"</span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label> Agent UserName: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'> </span>"+agent.getEmailID()+"</span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label> Agent Password: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'> </span>"+ "test" +"</span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"<br/><br/>			\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label> If you need kindly reset the password </label> </span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"<br/><br/>			\r\n" + 
							"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>\r\n" + 
							"<p>Thanks and regards,<p></p></span></b></p><p>TRIO Management Alert</span></b></p><p>Please feel free to touch with us at 	customer.service@trio-i.com</span></b></p><p>Please visit our website <a href=\"http://trio-i.com/\">http://trio-i.com/</a>  \r\n" + 
							"</span></b></p><br/><br/></div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" + 
							"<br/><p><font size=\"2\">\r\n" + 
							"You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n" + 
							"</font></p></body> </html>";
							try {
								/*Class.forName(JDBC_DRIVER);
								con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
								stmt=con.createStatement();
								preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
								preparedStatement.setString(1, agent.getEmailID());
								preparedStatement.setString(2, "FOR TRIO AGENT REGISTRATION ACKNOWLEDGEMENT"); 
								preparedStatement.setString(3, emailTemplate12);
								preparedStatement.setString(4, "TRIO");
								preparedStatement.execute();*/	
								
								logger.info("Calling Email Service -------------");
								PushEmail.sendMail(agent.getEmailID(),"FOR TRIO AGENT REGISTRATION ACKNOWLEDGEMENT",emailTemplate12);
								logger.info("Successfully Email Called ------------");
								
								
								logger.info("Successfully Saved data.");
								status=true;
							return status;
							}

							 catch(Exception e){
								 status=true;
								 logger.error("Exception -->"+e.getMessage());
								 return status;
							}
						}
				
				// ----------------- Registration Employee Notification --------------
				public static boolean RegisterEmployeeMail(Employee emp){
					logger.info("----------- Inside RegisterEmployeeMail Method() --------------");
					boolean status =false;
					logger.info("Sender Email -->"+emp.getEmailID());
					logger.info("Employee Code -->"+emp.getEmployeeCode());
					
					String emailTemplate13="<html> " + 
							"<body  style='tab-interval:.5in'> \r\n" + 
							"<a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a>\r\n" + 
							"<br/><div><br/><p class=MsoNormal align=center style='text-align:center'><b><span\r\n" + 
							"style='font-size:20.0pt;line-height:115%;color:blue'>TRIO EMPLOYEE REGISTRATION ACKNOWLEDGEMENT EMAIL <p></p></span></b></p>\r\n" + 
							"<p> Dear Sir/Madam,</p> <p> Your profile has been created in trio system and and please find the  details are in below </p>\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label>Employee Code:</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'> </span>"+emp.getEmployeeCode()+"</span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label> Employee UserName: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'> </span>"+emp.getEmailID()+"</span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label> Employee Password: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style='mso-spacerun:yes'> </span>"+ "test" +"</span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"<br/><br/>			\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label> If you need kindly reset the password </label> </span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"<br/><br/>			\r\n" + 
							"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>\r\n" + 
							"<p>Thanks and regards,<p></p></span></b></p><p>TRIO Management Alert</span></b></p><p>Please feel free to touch with us at 	customer.service@trio-i.com</span></b></p><p>Please visit our website <a href=\"http://trio-i.com/\">http://trio-i.com/</a>  \r\n" + 
							"</span></b></p><br/><br/></div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" + 
							"<br/><p><font size=\"2\">\r\n" + 
							"You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n" + 
							"</font></p></body> </html>";
							try {
								/*Class.forName(JDBC_DRIVER);
								con=DriverManager.getConnection(DB_URL_EMAIL, USER, PASS);
								stmt=con.createStatement();
								preparedStatement=con.prepareStatement("insert into email_temp (toaddress,subject,message,status) values(?,?,?,?)");
								preparedStatement.setString(1, emp.getEmailID());
								preparedStatement.setString(2, "FOR TRIO EMPLOYEE REGISTRATION ACKNOWLEDGEMENT"); 
								preparedStatement.setString(3, emailTemplate13);
								preparedStatement.setString(4, "TRIO");
								preparedStatement.execute();	*/
								
								logger.info("Calling Email Service -------------");
								PushEmail.sendMail(emp.getEmailID(),"FOR TRIO EMPLOYEE REGISTRATION ACKNOWLEDGEMENT",emailTemplate13);
								logger.info("Successfully Email Called ------------");
								
								logger.info("Successfully Saved data.");
								status=true;
							return status;
							}

							 catch(Exception e){
								 status=true;
								 logger.error("Exception -->"+e.getMessage());
								 return status;
							}
				}
				
				//-------------- Payment Confirmation Email --------------
				public static boolean paymentConfirmation(Member member){
					System.out.println("----------------- Inside payment Confirmation -------------------"+member.getEmailID());
					boolean status =false;
					
					String emailTemplate14="<html> <head> <style> div {  background-color: white; }</style> </head>\r\n" + 
							"<body lang=EN-US style='tab-interval:.5in'> \r\n" + 
							"<a href=\"http://hims-c.com/\"> <img src=\"http://35.166.255.46:7006/upload/logo.png\" alt=\"Italian Trulli\"> </a>\r\n" + 
							"<br/><div><br/><p class=MsoNormal align=center style='text-align:center'><b><span\r\n" + 
							"style='font-size:20.0pt;line-height:115%;color:blue'>TRIO PAYMENT UPLOADED ACKNOWLEDGEMENT EMAIL <p></p></span></b></p>\r\n" + 
							"<p> Dear TRIO Admin,</p> <p> You have alert for member upload payment and Payment details in below </p>\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label style=\"color:#5D6D7E;\">Member ID:</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+member.getMemberNumber()+"</span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"<label style=\"color:#5D6D7E;\"> Upload Payment Date: </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+Custom.getCurrentDate()+"</span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"\r\n" + 
							
							"<br/><br/>	\r\n" + 
							"\r\n" + 
							"<p class=MsoNormal style='mso-bidi-font-weight:normal'><span\"style='font-size:3;line-height:115%;background:yellow;mso-highlight:yellow'>\r\n" + 
							"Please approve &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"http://trio.neotural.com/login\" style='color:blue;'>TRIO LOGIN</a></span>\r\n" + 
							"<p style='mso-bidi-font-weight:normal'><span style='font-size:3;line-height:115%'></span></p>\r\n" + 
							"<br/><br/>			\r\n" + 
							"<p class=MsoNormal><b><span style='font-size:26.0pt;line-height:115%; color:black'><p><span style='text-decoration:none'>&nbsp;</span></p></span></b></p>\r\n" + 
							"<p>Thanks and regards,<p></p></span></b></p><p>TRIO Management Alert</span></b></p><p>Please feel free to touch with us at 	customer.service@trio-i.com</span></b></p><p>Please visit our website <a href=\"http://hims-c.com/\">http://hims-c.com/</a>  \r\n" + 
							"</span></b></p><br/><br/></div> <br/><br/> <a href=\"http://trio-i.com/\"> <img src=\"http://35.166.255.46:7006/upload/trioPlayStore.png\" alt=\"Italian Trulli\"></a>\r\n" + 
							"<br/><p><font size=\"2\">\r\n" + 
							"You have received this mail because your e-mail ID is registered with trio-i.com. This is a system-generated e-mail regarding your trio-i account preferences, please don't reply to this message. The Message sent in this mail have been posted by the clients of trio-i.com. And we have enabled auto-login for your convenience, you are strongly advised not to forward this email to protect your account from unauthorized access. IEIL has taken all reasonable steps to ensure that the information in this mailer is authentic. Users are advised to research bonafides of advertisers independently. Please do not pay any money to anyone who promises to find you a job. IEIL shall not have any responsibility in this regard. We recommend that you visit our Terms & Conditions and the Security Advice for more comprehensive information.\r\n" + 
							"</font></p></body> </html>";
							
							try {
								
								logger.info("Calling Email Service -------------");
								PushEmail.sendMail(member.getEmailID(),"FOR TRIO PAYMENT UPLOADED ACKNOWLEDGEMENT",emailTemplate14);
								logger.info("Successfully Email Called ------------");
								
								logger.info("Successfully Saved data.");
								status=true;
							return status;
							}

							 catch(Exception e){
								 status=true;
								 logger.error("Exception -->"+e.getMessage());
								 return status;
							}
				}
				
	}


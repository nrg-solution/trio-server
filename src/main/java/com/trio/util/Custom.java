package com.trio.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trio.dto.Dropbox;
//import com.trio.service.UiApplication;

public class Custom {
	public static final Logger logger = LoggerFactory.getLogger(Custom.class);

	//private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
   // private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateFormat sdff = new SimpleDateFormat("HH:mm:ss");
   
    public static String getCurrentDateandTime() {
    	
    	Date today = new Date();
    	String currentTime = sdff.format(today);
    	System.out.println("Current time ---------->"+currentTime);
        return currentTime;
    }
    
	private static final DateFormat formateDate = new SimpleDateFormat("dd/MM/yyyy");
 
 public static String getFormatedDate(Date inputDate) {

    	//Date today = new Date();
    	String formate = formateDate.format(inputDate);
    	System.out.println("Formated Date ---------->"+formate);
        return formate;
    }
 
    
    
 
 public static java.sql.Date getCurrentDate() {
     java.util.Date today = new java.util.Date();
     return new java.sql.Date(today.getTime());
 }
 
 public static String getCurrentStringDate() {
	// String currentDate = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
	 String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	 
     return currentDate;
 }
    
/*    mapadult.put("adult0", 0);
	mapadult.put("adult1", 1);
	mapadult.put("adult2", 2);
	mapadult.put("adult3", 3);
	mapadult.put("adult4", 4);
	mapadult.put("adult5", 5); */  	
    static Map<String,Integer> mapadult = null;//new HashMap<String,Integer>();
    static Map<String,Integer> mapchild = null;//new HashMap<String,Integer>();
    static Map<String,Integer> maprooms = null;//new HashMap<String,Integer>();
    static Map<String,Integer> mappax = null;
    
 public static int getAudult(String key){
	 mapadult = new HashMap<String,Integer>();
		mapadult.put("adult0", 0);
    	mapadult.put("adult1", 1);
    	mapadult.put("adult2", 2);
    	mapadult.put("adult3", 3);
    	mapadult.put("adult4", 4);
    	mapadult.put("adult5", 5); 
    	mapadult.put("", 6);  
    	mapadult.put(null, 7);  


     int val=mapadult.get(key);
     return val;

   }
   
public static int getRooms(String key){
	maprooms = new HashMap<String,Integer>();
	maprooms.put("room0", 0);
	maprooms.put("room1", 1);
	maprooms.put("room2", 2);
	maprooms.put("room3", 3);
	maprooms.put("room4", 4);
	maprooms.put("room5", 5);
	maprooms.put("", 6);  
	maprooms.put(null, 7);  

	 int val=maprooms.get(key);
     return val; 
   }

	public static int getChild(String key){
		mapchild = new HashMap<String,Integer>();
		mapchild.put("child0", 0);
		mapchild.put("child1", 1);
		mapchild.put("child2", 2);
		mapchild.put("child3", 3);
		mapchild.put("child4", 4);
		mapchild.put("child5", 5);
		mapchild.put("", 6);  
		mapchild.put(null, 7);  

		 int val=mapchild.get(key);
	     return val;  
	}

	public static int getPax(String key){
		mappax = new HashMap<String,Integer>();
		mappax.put("pax0", 0);
		mappax.put("pax1", 1);
		mappax.put("pax2", 2);
		mappax.put("pax3", 3);
	   	mappax.put("pax4", 4);
	   	mappax.put("pax5", 5); 
	   	mappax.put("", 6);  
	   	mappax.put(null, 7);  

	    int val=mappax.get(key);
	    return val;
	}
  // Future date calculation 
	/*public static Dropbox getFutureDate(Date date) {
		Dropbox dropbox=null;
		try {
			dropbox = new Dropbox();
			// New 
			//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			//SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

			Calendar now = Calendar.getInstance();
		    System.out.println("Current date : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));

			now = Calendar.getInstance();
		    now.add(Calendar.MONTH, 3);
		   // System.out.println("date after 3 months : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));
		    String test1= (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR);
		    Date date1 = formatter.parse(test1);
		    System.out.println("First Date ---------->"+formatter.format(date1));
		    dropbox.setDate1(formatter.format(date1));
		    //formatter.format(date1);
		    now = Calendar.getInstance();
		    now.add(Calendar.MONTH, 6);
		   // System.out.println("date after 6 months : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));
		    String test2= (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR);
		    Date date2 = formatter.parse(test2);
		    System.out.println("Second Date ---------->"+formatter.format(date2));    
		    dropbox.setDate2(formatter.format(date2));

		    now = Calendar.getInstance();
		    now.add(Calendar.MONTH, 9);
		   // System.out.println("date after 9 months : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));
		    String test3= (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR);
		    Date date3 = formatter.parse(test3);
		    System.out.println("Third Date ---------->"+formatter.format(date3));
		    dropbox.setDate3(formatter.format(date3));

		    now = Calendar.getInstance();
		    now.add(Calendar.MONTH, 12);
		    //System.out.println("date after 12 months : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));
		    String test4= (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR);
		    Date date4 = formatter.parse(test4);
		    System.out.println("Fourth Date ---------->"+formatter.format(date4));
		    dropbox.setDate4(formatter.format(date4));

			} catch(Exception e) {
			e.printStackTrace();
			//System.out.println("Exception Dropbox -->"+);
		}
		
		return dropbox;
		
	}*/
	public static Dropbox getFutureDate() {
		Dropbox dropbox=null;
		try {
			dropbox = new Dropbox();
	       // SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Calendar now = Calendar.getInstance();
		    System.out.println("Current date : " + now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1)  + "-" + now.get(Calendar.DATE) );
			now = Calendar.getInstance();
		    now.add(Calendar.MONTH, 3);
		    String test1=  now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);
		    Date date1 = formatter.parse(test1);
		    System.out.println("First Date ---------->"+formatter.format(date1));
		    dropbox.setDate1(formatter.format(date1));
		    now = Calendar.getInstance();
		    now.add(Calendar.MONTH, 6);
		    String test2=  now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);
		    Date date2 = formatter.parse(test2);
		    System.out.println("Second Date ---------->"+formatter.format(date2));    
		    dropbox.setDate2(formatter.format(date2));
		    now = Calendar.getInstance();
		    now.add(Calendar.MONTH, 9);
		    String test3= now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);
		    Date date3 = formatter.parse(test3);
		    System.out.println("Third Date ---------->"+formatter.format(date3));
		    dropbox.setDate3(formatter.format(date3));
		    now = Calendar.getInstance();
		    now.add(Calendar.MONTH, 12);
		    String test4= now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);
		    Date date4 = formatter.parse(test4);
		    System.out.println("Fourth Date ---------->"+formatter.format(date4));
		    dropbox.setDate4(formatter.format(date4));

			} catch(Exception e) {
			e.printStackTrace();
			//System.out.println("Exception Dropbox -->"+);
		}
		
		return dropbox;
		
	}
}

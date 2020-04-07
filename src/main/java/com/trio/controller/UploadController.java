package com.trio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trio.bo.TrioBo;
//import com.trio.dao.GglDao;
//import com.trio.dao.TrioDaoImpl;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {
	public static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	
	@Autowired
	TrioBo bo;
    //Save the uploaded file to this folder
    //private static String UPLOADED_FOLDER = "E://temp//TRIOPayment//";
	private static String UPLOADED_FOLDER = "/home/ec2-user/TRIO/trio-ui/src/assets/TRIOPayment";
	
    String gglnumber;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("gglnumber") String gglnumber,
                                   RedirectAttributes redirectAttributes) {
    	logger.info("----------------singleFileUpload ---------");
    	logger.info("GGL Number ----------->"+gglnumber);
    	//String basename = FilenameUtils.getBaseName(file.toString());
    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());


        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:invalidFile";
        }
        

        if (gglnumber.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please Enter Valid GGL Numbr");
            return "redirect:/invalidGGL";
        }

        try {
        	logger.info("File Name --------------->"+gglnumber+"." + extension);        	
        	String status= bo.gglNumberCheck(gglnumber);
        	logger.info("TRIO Number Validate status ---->"+status); 
        	if(status.equalsIgnoreCase("success")) {
                 byte[] bytes = file.getBytes();
                 Path path = Paths.get(UPLOADED_FOLDER + gglnumber+"."+extension);
                 Files.write(path, bytes);
                 boolean paymentStatus = bo.UpdatePayment(gglnumber);
                 redirectAttributes.addFlashAttribute("message",
                         "You successfully uploaded '" + file.getOriginalFilename() + "'");
              if(paymentStatus==true) {
                 return "redirect:/success";
              }
              else {
             	 return "redirect:/otherIssue";

              }

             }
        	 else {
            	 return "redirect:/invalidGGL";

        	 }
            // Get the file and save it somewhere

        } catch (Exception e) {
        	 return "redirect:/otherIssue";
           // e.printStackTrace();
        }

       // return "redirect:/success";
    }

    
   
    
   @GetMapping("/success")
    public String uploadStatus() {
        return "success";
    }
   
   @GetMapping("/invalidGGL")
   public String Invalid() {
       return "invalidGGL";
   }
   
   @GetMapping("/otherIssue")
   public String otherIssue() {
       return "otherIssue";
   }
   @GetMapping("/invalidFile")
   public String uploadissue() {
       return "invalidFile";
   }

   

}
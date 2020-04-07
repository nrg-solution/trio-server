package com.trio.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.File;

public class ImagingTest {

	 public static void main(String[] args) throws IOException {
	        try {
	        	System.out.println("Main 0");
		    String url = "http://localhost:8090/user-portal/images/silver.jpg";
		 //   String goldurl = "http://localhost:8090/user-portal/images/gold.jpg";
		  //  String platinumurl = "http://localhost:8090/user-portal/images/platinum.jpg";

		    //String url = "http://www.mkyong.com/image/mypic.jpg";

		  //  String url = "E:\\temp\\final\\gold.jpg";
		    //file://e/
		    System.out.println("Main 1");
	        String text = "Alex Ubalton";
	        System.out.println("Main 2");
	 //       byte[] b = mergeImageAndText(url, text, new Point(200, 200));
	        byte[] b = mergeImageAndText(url, text, new Point(240,200));

	        System.out.println("Main 3");
	        FileOutputStream fos = new FileOutputStream("output.png");
	        System.out.println("Main 4");
	        fos.write(b);
	        System.out.println("Main 5");
	        fos.close();
	        System.out.println("Main 6");
	        }catch(Exception e) {
	        	System.out.println("Main Error");
	        	e.printStackTrace();
	        }
	        
	    }

	    public static byte[] mergeImageAndText(String imageFilePath,String text, Point textPosition) throws IOException {
	    	ByteArrayOutputStream baos =null;
	    	try {
	    	System.out.println("File Path--->"+imageFilePath);
	    	BufferedImage im = ImageIO.read(new URL(imageFilePath));
	    	System.out.println("2");
	        Graphics2D g2 = im.createGraphics();
	        g2.setColor(Color.BLUE);
	        g2.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 

	        System.out.println("3");
	        g2.drawString(text, textPosition.x, textPosition.y);
	        System.out.println("4");
	        baos = new ByteArrayOutputStream();
	        System.out.println("5");
	       // ImageIO.write(im, "png", baos);
	        ImageIO.write(im, "jpg", new File("E:\\temp\\out.jpg"));
	        System.out.println("6");
	        return baos.toByteArray();
	        }catch(Exception e) {
	        	System.out.println("Error");
	        	//return baos.toByteArray();
	        	e.printStackTrace();
	        	//return baos.toByteArray();
	        }
	        return baos.toByteArray();
	    }
}

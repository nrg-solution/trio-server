package com.trio.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class WriteImage 
{	
    public static void main( String[] args )
    {
    	BufferedImage image = null;
        try {
          
            URL url = new URL("E:\\temp\\certificate.jpg");
            image = ImageIO.read(url);
            
            ImageIO.write(image, "jpg",new File("E:\\out.jpg"));
            ImageIO.write(image, "gif",new File("E:\\out.gif"));
            ImageIO.write(image, "png",new File("E:\\out.png"));
            
        } catch (IOException e) {
        	e.printStackTrace();
        }
        System.out.println("Done");
    }
}
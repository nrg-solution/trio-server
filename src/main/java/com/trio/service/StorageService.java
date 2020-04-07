package com.trio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.trio.model.UserDetail;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
//import javax.inject.Singleton;
//import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class StorageService {

	public static final Logger logger = LoggerFactory.getLogger(StorageService.class);
	
	@PersistenceContext(unitName="trio-pu")
	private EntityManager entityManager;

	//Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("/home/ec2-user/paymentFiles/");
	//private final Path rootLocation = Paths.get("/home/ec2-user/GGL/PaymentFiles/"); server payment Upload save page
	//private final Path rootLocation = Paths.get("/home/ec2-user/TRIO/PaymentFiles/"); //Paths.get("upload-dir");

	public String store(MultipartFile file , String memberID) {
		String fileName=null;
		String status = "Fail";
		try {
			logger.info("Path ---->"+rootLocation);
			if(memberID != null) {
				fileName = memberID + ".jpg";
			}
			else {
				fileName = file.getOriginalFilename();
			}
			Files.deleteIfExists(this.rootLocation.resolve(fileName));
			Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
			status="Success";
		} catch (Exception e) {
			status="failure";
			logger.error("Exception -->"+e.getMessage());
			throw new RuntimeException("FAIL!");
		}
		return status;
	}
	
	public Stream<Path> loadAll() {
		logger.info("------ Inside LoadAll Method --------");
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
			logger.error("Exception -->"+e.getMessage());
            throw new RuntimeException("Failed to read stored files", e);
        }

    }

	public Resource loadFile(String filename) {
		logger.info("------- Inside loadfile Method ---------");
		try {
			logger.info("------- root Path ---------"+rootLocation);
			logger.info("------- File Name ---------"+filename);	
			Path file = rootLocation.resolve(filename);
			logger.info("------- After Path Called ---------"+file);
			Resource resource = new UrlResource(file.toUri());
			logger.info("------- After Resources ---------"+resource);
			if (resource.exists() || resource.isReadable()) {
				logger.info("------- Resources is exsist or isreadable---------"+resource.exists());
				return resource;
			} else {
				logger.info("------- Resources is not exsist or is not readable---------");
				Path file1 = rootLocation.resolve("NoImage.jpg"); 
				Resource status = new UrlResource(file1.toUri());
				return status;
				//throw new RuntimeException("FAIL!"); 
			}
		} catch (MalformedURLException e) {
			logger.error("Exception -->"+e.getMessage());
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			logger.error("Exception -->"+e.getMessage());
			throw new RuntimeException("Could not initialize storage!");
			
		}
	}
	
	//------------ MemberID Validation ---------
	@Transactional(value="transactionManager")
	@SuppressWarnings (value="unchecked")
	public boolean getMemberIDValidate(String MemberID){
		logger.info("--------------- Inside getMemberIDValidate (Storage Service) -----------------");
		boolean Response=false;
		Query query=null;
		List<UserDetail> result;
		try {
			query=entityManager.createQuery("from UserDetail where memberID=?");
			query.setParameter(1, MemberID);
			result=(List<UserDetail>)query.getResultList();
			logger.info("Result size ------------>"+result.size());
			if(result.size()>0){				
				for(UserDetail memberID : result) {
					logger.info("Database referance Member ID ----------------->"+memberID.getMemberID());
					if(memberID.getMemberID().equalsIgnoreCase(MemberID)) {
						Response=true;
					}
					else {
						Response=false;
					}
				}
			}
			return Response;
		}catch(Exception e) {
			e.printStackTrace();
			return Response;
		}finally {
			
		}
	}


}

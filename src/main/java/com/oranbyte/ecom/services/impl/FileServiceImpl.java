package com.oranbyte.ecom.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.oranbyte.ecom.services.FileService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Implementation of the {@link FileService} interface for managing file-related operations.
 */
@Service
public class FileServiceImpl implements FileService{

	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	
	private String uploadDirectory = System.getProperty("user.dir") + "/uploads/";
	
	/**
	 * Converts the string representation of file size into bytes size
	 * <br>
	 * <b>Example :</b> 1MB = 1048576<br>
	 * formula => 1 x 1024 x 1024 Bytes
	 */	
	@Override
	public long convertIntoBytesSize(String sizeString) throws NumberFormatException, Exception{

		int size;
	    String unit;
	    
	    try {
	        if (sizeString.contains(" ")) {
	            String[] split = sizeString.split(" ");
	            size = Integer.parseInt(split[0]);
	            unit = split[1].toUpperCase();
	        } else {
	            unit = sizeString.substring(sizeString.length() - 2).toUpperCase();
	            size = Integer.parseInt(sizeString.substring(0, sizeString.length() - 2).trim());
	        }
	    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
	        return 0;
	    }
	    
	    switch (unit) {
	        case "KB": return size * 1024L;
	        case "MB": return size * 1024L * 1024L;
	        case "GB": return size * 1024L * 1024L * 1024L;
	        default: 
	            LOGGER.warn("Unknown unit: {}", unit);
	            return 0;
	    }
	}

	@Override
	public String uploadFile(MultipartFile file) throws IOException {
		if (file != null && !file.isEmpty()) {

			String originalFilename = file.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
			String uniqueName = UUID.randomUUID().toString() + extension;

			Path path = Paths.get(uploadDirectory + uniqueName);
			Files.createDirectories(Paths.get(uploadDirectory));
			
			Files.write(path, file.getBytes());

			return uniqueName;
		}
		return null;
	}
	
	@Override
	public String uploadFile(MultipartFile file, String directory) throws IOException {
		
		if(Objects.isNull(directory)) {
			directory = "";
		}else {
			directory = directory + "/";
		}
		
		if (file != null && !file.isEmpty()) {
			
			String originalFilename = file.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
			String uniqueName = UUID.randomUUID().toString() + extension;
			
			Path path = Paths.get(uploadDirectory + directory + uniqueName);
			Files.createDirectories(Paths.get(uploadDirectory + directory));
			
			Files.write(path, file.getBytes());
			
			return directory + uniqueName;
		}
		return null;
	}

	@Override
	public String getFullPath(String fileName) {
		 ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        if (requestAttributes != null) {
	            HttpServletRequest request = requestAttributes.getRequest();
	            String appURL = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
	            
	            if(new File((uploadDirectory + fileName.trim())).exists()) {
	            	return appURL + "/uploads/" + fileName;
	            }else {
	            	return null;
	            }
	            
	     } 
	     return null;
	}

	@Override
	public boolean deleteIfExists(String filePath) {
		try {
			 Path path = Paths.get(uploadDirectory + filePath);
		     return Files.deleteIfExists(path);
		}catch(IOException e) {
			 e.printStackTrace();
		     return false;
		}
	}
	
	
}

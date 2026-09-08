package com.oranbyte.ecom.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for file-related operations.
 */
public interface FileService {
	
	/**
     * Converts a string representation of file size into bytes.
     *
     * @param size The string representation of file size (e.g., "10MB", "1GB").
     * @return The size in bytes.
     * @throws NumberFormatException If the size string is not in a valid format.
     * @throws Exception             If an error occurs during the conversion.
     */
	long convertIntoBytesSize(String size) throws NumberFormatException, Exception;
	
	String uploadFile(MultipartFile multipartFile) throws IOException;
	
	String uploadFile(MultipartFile multipartFile, String directory) throws IOException;

	String getFullPath(String fileName);
	
	boolean deleteIfExists(String filePath);
	
}

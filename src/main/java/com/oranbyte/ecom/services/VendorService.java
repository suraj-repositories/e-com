package com.oranbyte.ecom.services;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.request.VendorRequest;

public interface VendorService {

	VendorDto getVendor(Long id);
	
	VendorDto createVendor(VendorRequest vendor) throws IOException;
	
	VendorDto updateVendor(Long id, VendorRequest vendor) throws IOException;

	ResponseEntity<?> updateLogo(Long id, MultipartFile logo) throws IOException;
	
	Page<VendorDto> getVendors(String search, Pageable pageable);
}


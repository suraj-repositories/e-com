package com.oranbyte.ecom.rest.impl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.request.VendorRequest;
import com.oranbyte.ecom.rest.VendorRest;
import com.oranbyte.ecom.services.VendorService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.Language;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VendorRestImpl implements VendorRest {

	private final VendorService vendorService;
	private final Language lang;

	@Override
	public ResponseEntity<?> createVendor(@Valid @ModelAttribute VendorRequest request) throws IOException {
		VendorDto vendor = vendorService.createVendor(request);
		return AppUtils.getApiResponse(HttpStatus.CREATED, true, lang.getValue("vendor-created"), vendor);
	}
	
	@Override
	public ResponseEntity<?> getVendor(Long id) {
		VendorDto vendor = vendorService.getVendor(id);
		return AppUtils.getApiResponse(true, lang.getValue("vendor-fetched"), vendor);
	}

	@Override
	public ResponseEntity<?> updateVendor(Long id, @Valid VendorRequest request) throws IOException {
		VendorDto vendor = vendorService.updateVendor(id, request);
		return AppUtils.getApiResponse(true, lang.getValue("vendor-updated"), vendor);
	}

	@Override
	public ResponseEntity<?> updateLogo(Long id, MultipartFile logo) throws IOException {
		return vendorService.updateLogo(id, logo);
	}

	
}

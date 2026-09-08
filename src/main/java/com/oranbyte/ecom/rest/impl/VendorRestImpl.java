package com.oranbyte.ecom.rest.impl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.request.VendorRequest;
import com.oranbyte.ecom.rest.VendorRest;
import com.oranbyte.ecom.services.VendorService;
import com.oranbyte.ecom.util.AppUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VendorRestImpl implements VendorRest {

	private final VendorService vendorService;

	@Override
	public ResponseEntity<?> createVendor(@Valid @RequestBody VendorRequest request) {

		try {
			VendorDto vendor = vendorService.createVendor(request);

			return AppUtils.getApiResponse(HttpStatus.CREATED, true, "Vendor created successfully", vendor);

		} catch (IOException e) {
			e.printStackTrace();
			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false,
					"Failed to create vendor: " + e.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<?> updateVendor(Long id, @Valid VendorRequest request) {

		try {
			VendorDto vendor = vendorService.updateVendor(id, request);

			return AppUtils.getApiResponse(true, "Vendor updated successfully", vendor);

		} catch (IOException e) {
			e.printStackTrace();
			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false,
					"Failed to update vendor: " + e.getMessage(), null);

		}
	}
}

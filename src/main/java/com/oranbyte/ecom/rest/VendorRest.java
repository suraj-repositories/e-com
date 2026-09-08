package com.oranbyte.ecom.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.ecom.request.VendorRequest;

import jakarta.validation.Valid;

@RequestMapping(path = "/vendor")
public interface VendorRest {

	@PostMapping("/create")
	public ResponseEntity<?> createVendor(@Valid @RequestBody VendorRequest request);

	@PutMapping("/{id}")
	public ResponseEntity<?> updateVendor(@PathVariable("id") Long id, @Valid @RequestBody VendorRequest request);
	
}

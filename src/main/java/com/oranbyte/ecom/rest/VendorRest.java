package com.oranbyte.ecom.rest;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.request.VendorRequest;

import jakarta.validation.Valid;

@RequestMapping(path = "/vendor")
public interface VendorRest {

	@PostMapping(path="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<?> createVendor(@Valid @ModelAttribute VendorRequest request) throws IOException;

	@GetMapping("/{id}")
	ResponseEntity<?> getVendor(@PathVariable("id") Long id);
	
	@PutMapping("/{id}")
	ResponseEntity<?> updateVendor(@PathVariable("id") Long id, @Valid @RequestBody VendorRequest request) throws IOException;
	
	@PutMapping("/{id}/logo")
	ResponseEntity<?> updateLogo(@PathVariable("id") Long id, @RequestParam("logo") MultipartFile logo) throws IOException;

	@GetMapping
	ResponseEntity<?> getVendors(@RequestParam(required = false) String search,
			@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable);
	
}

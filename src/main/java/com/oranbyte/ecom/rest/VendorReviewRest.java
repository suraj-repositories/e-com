package com.oranbyte.ecom.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oranbyte.ecom.request.VendorReviewRequest;

import jakarta.validation.Valid;

@RequestMapping(path = "/vendor")
public interface VendorReviewRest {

	@PostMapping("/{vendorId}/review")
	ResponseEntity<?> createVendorReview(@PathVariable Long vendorId, @Valid @RequestBody VendorReviewRequest request);
	
	@PutMapping("/review/{id}")
	ResponseEntity<?> updateVendorReview(@PathVariable Long id, @Valid @RequestBody VendorReviewRequest request);
	
	@GetMapping("/review/{id}")
	ResponseEntity<?> getVendorReview(@PathVariable("id") Long id);
	
	@GetMapping("/{vendorId}/reviews")
	ResponseEntity<?> getVendorReviews(@PathVariable("vendorId") Long vendorId, @RequestParam(required = false) String search,
			@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable);
	
	@DeleteMapping("/review/{id}")
	ResponseEntity<?> deleteVendorReview(@PathVariable("id") Long id);
}

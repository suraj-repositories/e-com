package com.oranbyte.ecom.rest.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oranbyte.ecom.dto.VendorReviewDto;
import com.oranbyte.ecom.request.VendorReviewRequest;
import com.oranbyte.ecom.rest.VendorReviewRest;
import com.oranbyte.ecom.services.VendorReviewService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.Language;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VendorReviewRestImpl implements VendorReviewRest{
	
	private final VendorReviewService vendorReviewService;
	private final Language lang;
	
	
	@Override
	public ResponseEntity<?> createVendorReview(Long vendorId,@Valid @RequestBody VendorReviewRequest request) {
		log.info("request : {}", vendorId);
		
		VendorReviewDto vendorReview = vendorReviewService.createVendorReview(vendorId, request);
		return AppUtils.getApiResponse(HttpStatus.CREATED, true, lang.getValue("vendor-review-created"), vendorReview);
	}
	
	@Override
	public ResponseEntity<?> updateVendorReview(Long id, @Valid VendorReviewRequest request) {
		VendorReviewDto vendorReview = vendorReviewService.updateVendorReview(id, request);
		return AppUtils.getApiResponse(true, lang.getValue("vendor-review-updated"), vendorReview); 
	}

	@Override
	public ResponseEntity<?> getVendorReview(Long id) {
		VendorReviewDto vendorReview = vendorReviewService.getVendorReview(id); 
		return AppUtils.getApiResponse(true, lang.getValue("vendor-review-fetched"), vendorReview);
	}

	@Override
	public ResponseEntity<?> getVendorReviews(Long vendorId, String search, Pageable pageable) {
		Page<VendorReviewDto> vendorReviewsPage = vendorReviewService.getVendorReviews(vendorId, search, pageable);
		return AppUtils.getApiResponse(true, lang.getValue("vendor-reviews-fetched"), vendorReviewsPage);
	}

	@Override
	public ResponseEntity<?> deleteVendorReview(Long id) {
		vendorReviewService.deleteVendorReview(id);
		return AppUtils.getApiResponse(true, lang.getValue("vendor-review-deleted"), null);
	}
 

}

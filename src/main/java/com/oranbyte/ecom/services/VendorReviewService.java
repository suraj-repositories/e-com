package com.oranbyte.ecom.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oranbyte.ecom.dto.VendorReviewDto;
import com.oranbyte.ecom.request.VendorReviewRequest;

public interface VendorReviewService {

	VendorReviewDto createVendorReview(Long vendorId, VendorReviewRequest request);
	
	VendorReviewDto updateVendorReview(Long id, VendorReviewRequest request);
	
	VendorReviewDto getVendorReview(Long id);
	 
	Page<VendorReviewDto> getVendorReviews(Long vendorId, String search, Pageable pageable);
	
	void deleteVendorReview(Long id);
	
}

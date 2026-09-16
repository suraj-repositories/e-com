package com.oranbyte.ecom.services.impl;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.dto.VendorReviewDto;
import com.oranbyte.ecom.entity.User;
import com.oranbyte.ecom.entity.Vendor;
import com.oranbyte.ecom.entity.VendorReview;
import com.oranbyte.ecom.mapper.VendorReviewMapper;
import com.oranbyte.ecom.repository.VendorRepository;
import com.oranbyte.ecom.repository.VendorReviewRepository;
import com.oranbyte.ecom.request.VendorReviewRequest;
import com.oranbyte.ecom.services.FileService;
import com.oranbyte.ecom.services.UserService;
import com.oranbyte.ecom.services.VendorReviewService;
import com.oranbyte.ecom.specification.VendorReviewSpecification;
import com.oranbyte.ecom.util.Language;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorReviewServiceImpl implements VendorReviewService {

	private final VendorRepository vendorRepository;
	private final VendorReviewRepository vendorReviewRepository;
	private final VendorReviewMapper vendorReviewMapper;
	private final Language lang;
	private final FileService fileService;
	private final UserService userService;

	@Override
	public VendorReviewDto createVendorReview(Long id, VendorReviewRequest request) {
		User user = userService.getCurrentUser();
		Vendor vendor = vendorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-not-found")));

		VendorReview review = new VendorReview();

		review.setRating(request.getRating());
		review.setComment(request.getComment());
		review.setVendor(vendor);
		review.setUser(user);

		VendorReview savedVendorReview = vendorReviewRepository.save(review);
		return toVendorReviewDto(savedVendorReview);
	}
	
	@Override
	public VendorReviewDto updateVendorReview(Long id, VendorReviewRequest request) {
		VendorReview vendorReview = vendorReviewRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-review-not-found")));

		vendorReview.setRating(request.getRating());
		vendorReview.setComment(request.getComment());
		
		VendorReview updatedVendorReview = vendorReviewRepository.save(vendorReview);
		return toVendorReviewDto(updatedVendorReview);
	}

	@Override
	@Transactional(readOnly = true)
	public VendorReviewDto getVendorReview(Long id) {
		VendorReview vendorReview = vendorReviewRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-review-not-found")));

		return toVendorReviewDto(vendorReview);
	}

	@Override
	public Page<VendorReviewDto> getVendorReviews(Long vendorId, String search, Pageable pageable) {
//		filterManager.enableActiveFilter();
		Specification<VendorReview> specification = Specification.where(VendorReviewSpecification.search(search));

		return vendorReviewRepository.findAll(specification, pageable).map((entity) -> {
			VendorReviewDto dto = toVendorReviewDto(entity);
			return dto;
		});
	}

	private VendorReviewDto toVendorReviewDto(VendorReview entity) {
		VendorReviewDto dto = vendorReviewMapper.toDto(entity);

		VendorDto vendorDto = dto.getVendorDto();
		vendorDto.setLogo(fileService.getFullPath(vendorDto.getLogo()));
		dto.setVendorDto(vendorDto);
		return dto;
	}

	@Override
	public void deleteVendorReview(Long id) {
		VendorReview vendorReview = vendorReviewRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-review-not-found")));
		vendorReview.setDeletedAt(new Date());
		vendorReviewRepository.save(vendorReview);
	}

	

}

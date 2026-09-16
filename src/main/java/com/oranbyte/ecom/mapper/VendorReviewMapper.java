package com.oranbyte.ecom.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oranbyte.ecom.dto.VendorReviewDto;
import com.oranbyte.ecom.entity.VendorReview;

@Component
public class VendorReviewMapper {
	
	@Autowired
	private VendorMapper vendorMapper;

	@Autowired
	private UserMapper userMapper;
	
	public VendorReview toEntity(VendorReviewDto dto) {
		if (dto == null) {
            return null;
        }
		VendorReview review = new VendorReview();
		
		review.setId(dto.getId());
		review.setRating(dto.getRating());
		review.setComment(dto.getComment());
//		review.setUser(userMapper.toentity);
//		review.setVendor(dto.getVendor());
		
		return review;
	}
	
	public VendorReviewDto toDto(VendorReview entity) {
		if (entity == null) {
            return null;
        }
		VendorReviewDto dto = new VendorReviewDto();
		dto.setId(entity.getId());
		dto.setRating(entity.getRating());
		dto.setComment(entity.getComment());
		dto.setUserDto(userMapper.toDto(entity.getUser()));
		dto.setVendorDto(vendorMapper.toDto(entity.getVendor()));
		
		return dto;
	}
	
	 
	
}

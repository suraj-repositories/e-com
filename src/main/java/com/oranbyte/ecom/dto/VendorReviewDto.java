package com.oranbyte.ecom.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendorReviewDto {
	private Long id;
	
	private VendorDto vendorDto;
	
	private UserDto userDto;
	
	private Short rating;
	
	private String comment;
	
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public VendorReviewDto(Long id, VendorDto vendorDto, UserDto userDto, Short rating, String comment) {
		super();
		this.id = id;
		this.vendorDto = vendorDto;
		this.userDto = userDto;
		this.rating = rating;
		this.comment = comment;
	}
	
	
	
	
}

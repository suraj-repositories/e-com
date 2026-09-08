package com.oranbyte.ecom.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendorDto {
	
	private Long id;

	private String storeName;
	
	private String description;
	
	private String address;
	
	private String logo;
	
	private BigDecimal latitude;
	
	private BigDecimal longitude;

	public VendorDto(Long id, String storeName, String description, String address, String logo, BigDecimal latitude,
			BigDecimal longitude) {
		super();
		this.id = id;
		this.storeName = storeName;
		this.description = description;
		this.address = address;
		this.logo = logo;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
}

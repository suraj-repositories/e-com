package com.oranbyte.ecom.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ProductDto {

	private Long id;
	
	private String name;
	
	private String description;
	
	private VendorDto vendorDto;
	
	private CategoryDto categoryDto;
	
	private BigDecimal price;
	
	private Integer stock;
	
	private Boolean isActive;
	
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductDto(Long id, String name, String description, VendorDto vendorDto, CategoryDto categoryDto,
			BigDecimal price, Integer stock, Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.vendorDto = vendorDto;
		this.categoryDto = categoryDto;
		this.price = price;
		this.stock = stock;
		this.isActive = isActive;
	}
	
	
	
}

package com.oranbyte.ecom.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequest {

	private String name;

	private String description;
	
	private BigDecimal price;
	
	private Integer stock;  
	
	private Long categoryId;
	
	private Long vendorId;
}

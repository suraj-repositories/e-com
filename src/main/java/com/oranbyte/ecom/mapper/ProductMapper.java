package com.oranbyte.ecom.mapper;

import org.springframework.stereotype.Component;

import com.oranbyte.ecom.dto.ProductDto;
import com.oranbyte.ecom.entity.Product;

@Component
public class ProductMapper {

	public ProductDto toDto(Product product) {
		ProductDto dto = new ProductDto();
		
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		dto.setIsActive(product.getIsActive());
		dto.setCreatedAt(product.getCreatedAt());
		dto.setUpdatedAt(product.getUpdatedAt());
		return dto;
	}
	
}

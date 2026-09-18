package com.oranbyte.ecom.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oranbyte.ecom.dto.ProductDto;
import com.oranbyte.ecom.request.ProductRequest;

public interface ProductService {

	ProductDto createProduct(ProductRequest request);

	ProductDto getProduct(Long id);

	ProductDto updateProduct(Long id, ProductRequest reqeust);

	Page<ProductDto> getProducts(String search, Pageable pageable);
	
}

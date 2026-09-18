package com.oranbyte.ecom.rest.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.oranbyte.ecom.dto.ProductDto;
import com.oranbyte.ecom.request.ProductRequest;
import com.oranbyte.ecom.rest.ProductRest;
import com.oranbyte.ecom.services.ProductService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.Language;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductRestImpl implements ProductRest{

	private final ProductService productService;
	private final Language lang;
	

	@Override
	public ResponseEntity<?> createProduct(@Valid ProductRequest request) {
		ProductDto dto = productService.createProduct(request);
		return AppUtils.getApiResponse(HttpStatus.CREATED, true, lang.getValue("product-created"), dto);
	}


	@Override
	public ResponseEntity<?> getProduct(Long id) {
		ProductDto dto = productService.getProduct(id);
		return AppUtils.getApiResponse(HttpStatus.OK, true, lang.getValue("product-fetched"), dto);
	}
 
	@Override
	public ResponseEntity<?> updateProduct(Long id, @Valid ProductRequest request) {
		ProductDto dto = productService.updateProduct(id, request);
		return AppUtils.getApiResponse(HttpStatus.OK, true, lang.getValue("product-updated"), dto);
	}


	@Override
	public ResponseEntity<?> getProducts(String search, Pageable pageable) {
		Page<ProductDto> page = productService.getProducts(search, pageable);
		return AppUtils.getApiResponse(true, lang.getValue("products-fetched"), page);
	}
	
	
	
}

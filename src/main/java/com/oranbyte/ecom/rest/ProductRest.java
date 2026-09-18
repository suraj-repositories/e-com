package com.oranbyte.ecom.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oranbyte.ecom.request.ProductRequest;

import jakarta.validation.Valid;

@RequestMapping(path = "/products")
public interface ProductRest {

	@PostMapping("/create")
	ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request);

	@GetMapping("/{id}")
	ResponseEntity<?> getProduct(@PathVariable Long id);

	@PutMapping("/{id}")
	ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request);

	@GetMapping
	ResponseEntity<?> getProducts(@RequestParam(required = false) String search,
			@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable);
}

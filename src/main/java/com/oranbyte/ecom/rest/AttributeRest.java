package com.oranbyte.ecom.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.ecom.request.AttributeRequest;

import jakarta.validation.Valid;

@RequestMapping(path = "/attributes")
public interface AttributeRest {
	
	@PostMapping("/create")
	ResponseEntity<?> createAttribute(@Valid @RequestBody AttributeRequest request);
	
	@PutMapping("/{id}")
	ResponseEntity<?> updateAttribute(@PathVariable Long id, @Valid @RequestBody AttributeRequest request);
	
	@GetMapping("/{id}")
	ResponseEntity<?> getAttribute(@PathVariable Long id);
	
}

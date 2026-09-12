package com.oranbyte.ecom.rest;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.ecom.request.CategoryRequest;

import jakarta.validation.Valid;

@RequestMapping(path = "/category")
public interface CategoryRest {

	@PostMapping(path="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<?> createCategory(@Valid @ModelAttribute CategoryRequest request) throws IOException;

	
}

package com.oranbyte.ecom.rest;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.request.CategoryRequest;

import jakarta.validation.Valid;

@RequestMapping(path = "/category")
public interface CategoryRest {

	@PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<?> createCategory(@Valid @ModelAttribute CategoryRequest request) throws IOException;

	@PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @ModelAttribute CategoryRequest request)
			throws IOException;

	@GetMapping("/{id}")
	ResponseEntity<?> getCategory(@PathVariable("id") Long id);

	@GetMapping("/all")
	ResponseEntity<?> getCategories(@RequestParam(required = false) String search,
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable);

	@PutMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<?> updateCategoryImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile image) throws IOException;

	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteCategory(@PathVariable("id") Long id);
	
}

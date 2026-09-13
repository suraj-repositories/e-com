package com.oranbyte.ecom.rest.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.dto.CategoryDto;
import com.oranbyte.ecom.request.CategoryRequest;
import com.oranbyte.ecom.rest.CategoryRest;
import com.oranbyte.ecom.services.CategoryService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.Language;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryRestImpl implements CategoryRest {

	private final CategoryService categoryService;

	private final Language lang;

	@Override
	public ResponseEntity<?> createCategory(@Valid @ModelAttribute CategoryRequest request) throws IOException {
		CategoryDto dto = categoryService.createCategory(request);
		return AppUtils.getApiResponse(HttpStatus.CREATED, true, lang.getValue("category-created"), dto);
	}
	
	@Override
	public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @ModelAttribute CategoryRequest request) throws IOException {
		CategoryDto dto = categoryService.updateCategory(id, request);
		return AppUtils.getApiResponse(HttpStatus.CREATED, true, lang.getValue("category-updated"), dto);
	}

	@Override
	public ResponseEntity<?> getCategory(Long id) {
		CategoryDto dto = categoryService.getCategory(id);
		return AppUtils.getApiResponse(true, lang.getValue("category-fetched"), dto);
	}

	@Override
	public ResponseEntity<?> getCategories(String search, Pageable pageable) {
		Page<CategoryDto> page = categoryService.getTopCategories(search, pageable); 
		return AppUtils.getApiResponse(true, lang.getValue("categories-fetched"), page);
	}

	@Override
	public ResponseEntity<?> updateCategoryImage(Long id, MultipartFile file) throws IOException {
		String image = categoryService.updateImage(id, file);
		return AppUtils.getApiResponse(true, lang.getValue("category-image-updated"), Map.of("image", image));
	}

	@Override
	public ResponseEntity<?> deleteCategory(Long id) {
		categoryService.deleteCategory(id);
		return AppUtils.getApiResponse(true, lang.getValue("category-deleted"), null);
	}

}

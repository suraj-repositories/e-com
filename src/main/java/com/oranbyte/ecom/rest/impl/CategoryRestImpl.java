package com.oranbyte.ecom.rest.impl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

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
public class CategoryRestImpl implements CategoryRest{

	private final CategoryService categoryService;
	
	private final Language lang;
	
	@Override
	public ResponseEntity<?> createCategory(@Valid @ModelAttribute CategoryRequest request) throws IOException {
		CategoryDto dto = categoryService.createCategory(request);
		return AppUtils.getApiResponse(HttpStatus.CREATED, true, lang.getValue("category-created"), dto);
	}

}

package com.oranbyte.ecom.services;

import java.io.IOException;

import com.oranbyte.ecom.dto.CategoryDto;
import com.oranbyte.ecom.request.CategoryRequest;

public interface CategoryService {

	CategoryDto createCategory(CategoryRequest request) throws IOException;
	
}

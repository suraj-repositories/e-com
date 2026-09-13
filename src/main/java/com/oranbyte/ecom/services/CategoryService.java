package com.oranbyte.ecom.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.dto.CategoryDto;
import com.oranbyte.ecom.request.CategoryRequest;

public interface CategoryService {

	CategoryDto createCategory(CategoryRequest request) throws IOException;

	CategoryDto updateCategory(Long id, CategoryRequest request) throws IOException;
	
	CategoryDto getCategory(Long id);

	List<CategoryDto> getChildren(Long parentId);

	Page<CategoryDto> getTopCategories(String search, Pageable pageable);
	
	String updateImage(Long id, MultipartFile file) throws IOException;
	
	void deleteCategory(Long id);
}

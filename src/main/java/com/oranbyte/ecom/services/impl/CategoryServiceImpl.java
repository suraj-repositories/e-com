package com.oranbyte.ecom.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.oranbyte.ecom.dto.CategoryDto;
import com.oranbyte.ecom.entity.Category;
import com.oranbyte.ecom.exception.ResourceAlreadyExistsException;
import com.oranbyte.ecom.mapper.CategoryMapper;
import com.oranbyte.ecom.repository.CategoryRepository;
import com.oranbyte.ecom.request.CategoryRequest;
import com.oranbyte.ecom.services.CategoryService;
import com.oranbyte.ecom.services.FileService;
import com.oranbyte.ecom.util.SlugUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final FileService fileService;

	@Override
	public CategoryDto createCategory(CategoryRequest request) throws IOException {

		String slug = SlugUtils.toSlug(request.getName());

		if (categoryRepository.existsBySlug(slug)) {
			throw new ResourceAlreadyExistsException("Category with name '" + request.getName() + "' already exists");
		}

		String logo = fileService.uploadFile(request.getImage(), "categories");

		Category category = categoryMapper.toEntity(request);
		category.setImage(logo);
		category.setSlug(slug);

		Category savedCategory = categoryRepository.save(category);

		CategoryDto dto = categoryMapper.toDto(savedCategory);
		dto.setImage(fileService.getFullPath(dto.getImage()));

		return dto;
	}

}

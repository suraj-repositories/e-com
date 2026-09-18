package com.oranbyte.ecom.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.config.HibernateFilterManager;
import com.oranbyte.ecom.dto.CategoryDto;
import com.oranbyte.ecom.entity.Category;
import com.oranbyte.ecom.exception.AppException;
import com.oranbyte.ecom.exception.ResourceAlreadyExistsException;
import com.oranbyte.ecom.mapper.CategoryMapper;
import com.oranbyte.ecom.repository.CategoryRepository;
import com.oranbyte.ecom.request.CategoryRequest;
import com.oranbyte.ecom.services.CategoryService;
import com.oranbyte.ecom.services.FileService;
import com.oranbyte.ecom.specification.CategorySpecification;
import com.oranbyte.ecom.util.Language;
import com.oranbyte.ecom.util.SlugUtils;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final FileService fileService;
	private final Language lang;
	private final HibernateFilterManager filterManager;

	private static final String UPLOAD_DIR = "categories";

	@Override
	public CategoryDto createCategory(CategoryRequest request) throws IOException {

		String slug = SlugUtils.toSlug(request.getName());

		if (categoryRepository.existsBySlug(slug)) {
			throw new ResourceAlreadyExistsException("Category with name '" + request.getName() + "' already exists");
		}

		String logo = fileService.uploadFile(request.getImage(), UPLOAD_DIR);

		Category category = categoryMapper.toEntity(request);
		category.setImage(logo);
		category.setSlug(slug);
		category.setIsActive(true);

		Category savedCategory = categoryRepository.save(category);

		CategoryDto dto = categoryMapper.toDto(savedCategory);
		dto.setImage(fileService.getFullPath(dto.getImage()));

		return dto;
	}

	public CategoryDto updateCategory(Long id, CategoryRequest request) throws IOException {

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new AppException(lang.getValue("category-not-found"), HttpStatus.NOT_FOUND));

		String slug = SlugUtils.toSlug(request.getName());

		if (categoryRepository.existsBySlugAndIdNot(slug, id)) {
			throw new ResourceAlreadyExistsException("Category with name '" + request.getName() + "' already exists");
		}

		if (request.getImage() != null) {
			String oldImage = category.getImage();
			String image = fileService.uploadFile(request.getImage(), UPLOAD_DIR);
			category.setImage(image);

			if (oldImage != null) {
				fileService.deleteIfExists(oldImage);
			}
		}

		category.setName(request.getName());
		category.setParentId(request.getParentId());
		category.setSlug(slug);

		Category savedCategory = categoryRepository.save(category);

		CategoryDto dto = categoryMapper.toDto(savedCategory);
		dto.setImage(fileService.getFullPath(dto.getImage()));

		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryDto getCategory(Long id) {
		  filterManager.enableActiveFilter();
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("category-not-found")));
		CategoryDto dto = categoryMapper.toDto(category);

		dto.setImage(fileService.getFullPath(dto.getImage()));
		dto.setChilds(getChildren(id));
		return dto;

	}

	@Override
	public List<CategoryDto> getChildren(Long parentId) { 
		List<CategoryDto> children = categoryRepository.getChildren(parentId);
		children = children.stream().map((dto) -> {
			dto.setImage(fileService.getFullPath(dto.getImage()));
			return dto;
		}).toList();
		return children;
	}

	@Override
	public Page<CategoryDto> getTopCategories(String search, Pageable pageable) {
		filterManager.enableActiveFilter();
		Specification<Category> specification = Specification.where(CategorySpecification.isTopLevel())
				.and(CategorySpecification.search(search));

		return categoryRepository.findAll(specification, pageable).map((category) -> {
			CategoryDto dto = categoryMapper.toDto(category);
			dto.setImage(fileService.getFullPath(dto.getImage()));
			return dto;
		});
	}

	@Override
	public String updateImage(Long id, MultipartFile file) throws IOException {

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new AppException(lang.getValue("category-not-found"), HttpStatus.NOT_FOUND));

		String image = fileService.uploadFile(file, UPLOAD_DIR);

		if (image != null) {
			if (category.getImage() != null) {
				fileService.deleteIfExists(category.getImage());
			}

			category.setImage(image);
		}

		categoryRepository.save(category);
		return fileService.getFullPath(image);
	}

	@Override
	public void deleteCategory(Long id) {

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new AppException(lang.getValue("category-not-found"), HttpStatus.NOT_FOUND)); 
		category.setDeletedAt(new Date()); 
		categoryRepository.save(category);
	}

}

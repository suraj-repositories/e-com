package com.oranbyte.ecom.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.oranbyte.ecom.dto.CategoryDto;
import com.oranbyte.ecom.dto.ProductDto;
import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.entity.Category;
import com.oranbyte.ecom.entity.Product;
import com.oranbyte.ecom.entity.Vendor;
import com.oranbyte.ecom.mapper.CategoryMapper;
import com.oranbyte.ecom.mapper.ProductMapper;
import com.oranbyte.ecom.mapper.VendorMapper;
import com.oranbyte.ecom.repository.CategoryRepository;
import com.oranbyte.ecom.repository.ProductRepository;
import com.oranbyte.ecom.repository.ProductVariantRepository;
import com.oranbyte.ecom.repository.VendorRepository;
import com.oranbyte.ecom.request.ProductRequest;
import com.oranbyte.ecom.services.FileService;
import com.oranbyte.ecom.services.ProductService;
import com.oranbyte.ecom.specification.ProductSpecification;
import com.oranbyte.ecom.util.Language;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductVariantRepository productVariantRepository;
	private final ProductMapper productMapper;
	private final CategoryMapper categoryMapper;
	private final VendorMapper vendorMapper;
	private final CategoryRepository categoryRepository;
	private final VendorRepository vendorRepository;
	private final FileService fileService;
	private final Language lang;

	@Override
	public ProductDto createProduct(ProductRequest request) {

		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new RuntimeException(lang.getValue("category-not-found")));

		Vendor vendor = vendorRepository.findById(request.getVendorId())
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-not-found")));

		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setIsActive(true);
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		product.setCategory(category);
		product.setVendor(vendor);

		Product savedProduct = productRepository.save(product);
		return toDto(savedProduct);
	}

	private ProductDto toDto(Product product) {
		ProductDto dto = productMapper.toDto(product);

		CategoryDto cDto = categoryMapper.toDto(product.getCategory());
		cDto.setImage(fileService.getFullPath(cDto.getImage()));
		dto.setCategoryDto(cDto);

		VendorDto vDto = vendorMapper.toDto(product.getVendor());
		vDto.setLogo(fileService.getFullPath(vDto.getLogo()));
		dto.setVendorDto(vDto);

		return dto;
	}

	@Override
	public ProductDto getProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("product-not-found")));
		return toDto(product);
	}

	@Override
	public ProductDto updateProduct(Long id, ProductRequest request) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("product-not-found")));

		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new RuntimeException(lang.getValue("category-not-found")));

		Vendor vendor = vendorRepository.findById(request.getVendorId())
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-not-found")));

		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setIsActive(true);
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		product.setCategory(category);
		product.setVendor(vendor);

		Product updatedProduct = productRepository.save(product);
		return toDto(updatedProduct);
	}

	@Override
	public Page<ProductDto> getProducts(String search, Pageable pageable) {
		Specification<Product> specification = Specification.where(ProductSpecification.search(search));
		return productRepository.findAll(specification, pageable).map(this::toDto);
	}

}

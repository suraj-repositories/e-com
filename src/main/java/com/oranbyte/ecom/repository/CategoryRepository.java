package com.oranbyte.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oranbyte.ecom.dto.CategoryDto;
import com.oranbyte.ecom.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>{

	boolean existsBySlug(String slug);
	
	boolean existsBySlugAndIdNot(String slug, Long id);

	@Query(name = "Category.getChildren")
	List<CategoryDto> getChildren(@Param("parentId") Long parentId);
	
	@Query(name = "Category.getTopCategories")
	List<CategoryDto> getTopCategories();

}

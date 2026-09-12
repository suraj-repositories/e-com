package com.oranbyte.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oranbyte.ecom.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	  boolean existsBySlug(String slug);
	
}

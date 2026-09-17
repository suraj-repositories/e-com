package com.oranbyte.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oranbyte.ecom.entity.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Long>{

	boolean existsByName(String name);
	
	boolean existsByNameAndIdNot(String name, Long id);
	
}

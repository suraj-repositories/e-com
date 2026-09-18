package com.oranbyte.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oranbyte.ecom.entity.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Long>, JpaSpecificationExecutor<Attribute>{

	boolean existsByName(String name);
	
	boolean existsByNameAndIdNot(String name, Long id);
	
}

package com.oranbyte.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oranbyte.ecom.entity.AttributeValue;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
	
}

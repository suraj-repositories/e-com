package com.oranbyte.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oranbyte.ecom.entity.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long>{

}

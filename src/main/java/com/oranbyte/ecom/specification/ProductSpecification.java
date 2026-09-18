package com.oranbyte.ecom.specification;

import org.springframework.data.jpa.domain.Specification;

import com.oranbyte.ecom.entity.Product;

public class ProductSpecification {

	public static Specification<Product> search(String search) {

        return (root, query, criteriaBuilder) -> {

            if (search == null || search.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + search.trim().toLowerCase() + "%"
            );
        };
    }
	
}

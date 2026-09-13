package com.oranbyte.ecom.specification;

import org.springframework.data.jpa.domain.Specification;

import com.oranbyte.ecom.entity.Category;

public class CategorySpecification {

	public static Specification<Category> isTopLevel() {
		return (root, query, cb) -> cb.isNull(root.get("parentId"));
	}

	public static Specification<Category> search(String search) {
		return (root, query, cb) -> {

			if (search == null || search.isBlank()) {
				return cb.conjunction();
			}

			String keyword = "%" + search.trim().toLowerCase() + "%";

			return cb.or(cb.like(cb.lower(root.get("name")), keyword), cb.like(cb.lower(root.get("slug")), keyword));
		};
	}
}
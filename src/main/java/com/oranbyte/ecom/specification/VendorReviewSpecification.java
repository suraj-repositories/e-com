package com.oranbyte.ecom.specification;

import org.springframework.data.jpa.domain.Specification;

import com.oranbyte.ecom.entity.VendorReview;

public class VendorReviewSpecification {

	public static Specification<VendorReview> search(String search) {
		return (root, query, cb) -> {

			if (search == null || search.isBlank()) {
				return cb.conjunction();
			}

			String keyword = "%" + search.trim().toLowerCase() + "%";

			return cb.or(cb.like(cb.lower(root.get("comment")), keyword), cb.like(cb.lower(root.get("rating")), keyword));
		};
	}
	
}

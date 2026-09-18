package com.oranbyte.ecom.specification;

import org.springframework.data.jpa.domain.Specification;

import com.oranbyte.ecom.entity.Vendor;

public class VendorSpecification {

    public static Specification<Vendor> search(String search) {
        return (root, query, cb) -> {

            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }

            String keyword = "%" + search.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("storeName")), keyword),
                    cb.like(cb.lower(root.get("address")), keyword)
            );
        };
    }
}
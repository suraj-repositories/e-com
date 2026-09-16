package com.oranbyte.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oranbyte.ecom.entity.VendorReview;

public interface VendorReviewRepository extends JpaRepository<VendorReview, Long> , JpaSpecificationExecutor<VendorReview>{

}

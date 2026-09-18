package com.oranbyte.ecom.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VariantAttributeValueDto {

    private Long id;

    private Long variantId;

    private Long productAttributeValueId;
    
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
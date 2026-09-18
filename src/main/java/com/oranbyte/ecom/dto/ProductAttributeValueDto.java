package com.oranbyte.ecom.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductAttributeValueDto {

    private Long id;

    private Long productAttributeId;

    private AttributeValueDto attributeValue;

    private List<VariantAttributeValueDto> variantAttributeValues = new ArrayList<>();

    private List<ProductImageAttributeDto> imageAttributes = new ArrayList<>();
    
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
package com.oranbyte.ecom.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductAttributeDto {

	private Long id;

	private Long productId;

	private AttributeDto attribute;

	private Boolean isVariantAttribute;

	private List<ProductAttributeValueDto> values = new ArrayList<>();

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
package com.oranbyte.ecom.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductImageAttributeDto {

    private Long id;

    private Long productImageId;

    private Long productAttributeValueId;
    
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
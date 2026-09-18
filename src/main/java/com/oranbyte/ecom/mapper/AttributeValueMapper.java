package com.oranbyte.ecom.mapper;

import org.springframework.stereotype.Component;

import com.oranbyte.ecom.dto.AttributeValueDto;
import com.oranbyte.ecom.entity.AttributeValue;

@Component
public class AttributeValueMapper {

	public AttributeValueDto toDto(AttributeValue value) {
		
		AttributeValueDto dto = new AttributeValueDto();
		dto.setId(value.getId());
		dto.setValue(value.getValue());
		dto.setCreatedAt(value.getCreatedAt());
		dto.setUpdatedAt(value.getUpdatedAt());
		return dto;
		
	}
	
}

package com.oranbyte.ecom.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oranbyte.ecom.dto.AttributeDto;
import com.oranbyte.ecom.entity.Attribute;

@Component
public class AttributeMapper {

	@Autowired
	private AttributeValueMapper attributeValueMapper;

	public AttributeDto toDto(Attribute attribute) {
		AttributeDto dto = new AttributeDto();
		dto.setId(attribute.getId());
		dto.setName(attribute.getName());
		dto.setAttributeValues(attribute.getValues().stream().map((value) -> {
			return attributeValueMapper.toDto(value);
		}).toList());

		return dto;
	}

}

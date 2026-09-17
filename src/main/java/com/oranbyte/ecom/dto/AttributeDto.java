package com.oranbyte.ecom.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AttributeDto {

	private Long id;

	private String name;

	private List<AttributeValueDto> attributeValues;

	public AttributeDto(Long id, String name, List<AttributeValueDto> attributeValues) {
		super();
		this.id = id;
		this.name = name;
		this.attributeValues = attributeValues;
	}

	
	
}

package com.oranbyte.ecom.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor 
@ToString
public class AttributeValueDto {

	private Long id;
	private String value;
	
	
	public AttributeValueDto(Long id, String value) {
		super();
		this.id = id;
		this.value = value;
	}


	
}

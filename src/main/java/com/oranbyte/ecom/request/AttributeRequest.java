package com.oranbyte.ecom.request;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttributeRequest {

	private String name;
	
	private List<String> values;
	
}

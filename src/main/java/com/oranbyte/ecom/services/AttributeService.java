package com.oranbyte.ecom.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oranbyte.ecom.dto.AttributeDto;
import com.oranbyte.ecom.request.AttributeRequest;

public interface AttributeService {

	AttributeDto createAttribute(AttributeRequest request);
	
	AttributeDto updateAttribute(Long id, AttributeRequest request);
	
	AttributeDto getAttribute(Long id);
	
	Page<AttributeDto> getAttributes(String search, Pageable pageable);
	
}

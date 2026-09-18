package com.oranbyte.ecom.rest.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.oranbyte.ecom.dto.AttributeDto;
import com.oranbyte.ecom.request.AttributeRequest;
import com.oranbyte.ecom.rest.AttributeRest;
import com.oranbyte.ecom.services.AttributeService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.Language;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AttributeRestImpl implements AttributeRest{

	private final AttributeService attributeService;
	private final Language lang;

	@Override
	public ResponseEntity<?> createAttribute(@Valid AttributeRequest request) {
		AttributeDto dto = attributeService.createAttribute(request); 
		return AppUtils.getApiResponse(HttpStatus.CREATED, true, lang.getValue("attribute-created"), dto);
	}

	@Override
	public ResponseEntity<?> updateAttribute(Long id, @Valid AttributeRequest request) {
		AttributeDto dto = attributeService.updateAttribute(id, request);
		return AppUtils.getApiResponse(HttpStatus.OK, true, lang.getValue("attribute-updated"), dto);
	}

	@Override
	public ResponseEntity<?> getAttribute(Long id) {
		AttributeDto dto = attributeService.getAttribute(id);
		return AppUtils.getApiResponse(HttpStatus.OK, true, lang.getValue("attribute-fetched"), dto);
	}

	@Override
	public ResponseEntity<?> getAttributes(String search, Pageable pageable) { 
		Page<AttributeDto> page = attributeService.getAttributes(search, pageable);
		return AppUtils.getApiResponse(true, lang.getValue("attributes-fetched"), page);

	}
	
	
	
}

package com.oranbyte.ecom.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.oranbyte.ecom.dto.AttributeDto;
import com.oranbyte.ecom.entity.Attribute;
import com.oranbyte.ecom.entity.AttributeValue;
import com.oranbyte.ecom.exception.AppException;
import com.oranbyte.ecom.exception.ResourceAlreadyExistsException;
import com.oranbyte.ecom.mapper.AttributeMapper;
import com.oranbyte.ecom.repository.AttributeRepository;
import com.oranbyte.ecom.repository.AttributeValueRepository;
import com.oranbyte.ecom.request.AttributeRequest;
import com.oranbyte.ecom.services.AttributeService;
import com.oranbyte.ecom.util.Language;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {

	private final AttributeRepository attributeRepository;
	private final AttributeValueRepository attributeValueRepository;
	private final AttributeMapper attributeMapper;
	private final Language lang;

	@Override
	public AttributeDto createAttribute(AttributeRequest request) {

		if (attributeRepository.existsByName(request.getName())) {
			throw new ResourceAlreadyExistsException(lang.getValue("attribute-already-exists"));
		}

		List<String> values = request.getValues().stream().map(String::trim).toList();

		if (new HashSet<>(values).size() != values.size()) {
			throw new ResourceAlreadyExistsException(lang.getValue("attribute-value-already-exists"));
		}

		Attribute attribute = new Attribute();
		attribute.setName(request.getName());

		List<AttributeValue> attrValues = values.stream().map(value -> {
			AttributeValue attributeValue = new AttributeValue();
			attributeValue.setValue(value);
			attributeValue.setAttribute(attribute);
			return attributeValue;
		}).collect(Collectors.toCollection(ArrayList::new));

		attribute.setValues(attrValues);

		Attribute savedAttribute = attributeRepository.save(attribute);

		return attributeMapper.toDto(savedAttribute);
	}

	@Override
	public AttributeDto updateAttribute(Long id, AttributeRequest request) {

		Attribute attribute = attributeRepository.findById(id)
				.orElseThrow(() -> new AppException(lang.getValue("attribute-not-found"), HttpStatus.NOT_FOUND));

		if (attributeRepository.existsByNameAndIdNot(request.getName(), id)) {
			throw new ResourceAlreadyExistsException(lang.getValue("attribute-already-exists"));
		}

		List<String> values = request.getValues().stream().map(String::trim).toList();

		if (new HashSet<>(values).size() != values.size()) {
			throw new ResourceAlreadyExistsException(lang.getValue("attribute-value-already-exists"));
		}

		attribute.setName(request.getName());

		attribute.getValues().clear();

		for (String value : values) {

			AttributeValue attributeValue = new AttributeValue();
			attributeValue.setValue(value);
			attributeValue.setAttribute(attribute);

			attribute.getValues().add(attributeValue);
		}

		Attribute updatedAttribute = attributeRepository.save(attribute);

		return attributeMapper.toDto(updatedAttribute);
	}

	@Override
	public AttributeDto getAttribute(Long id) {
		Attribute attribute = attributeRepository.findById(id)
				.orElseThrow(() -> new AppException(lang.getValue("attribute-not-found"), HttpStatus.NOT_FOUND));
		return attributeMapper.toDto(attribute);
	}

}

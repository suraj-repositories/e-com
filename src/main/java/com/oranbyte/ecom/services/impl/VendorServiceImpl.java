package com.oranbyte.ecom.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.entity.Vendor;
import com.oranbyte.ecom.mapper.VendorMapper;
import com.oranbyte.ecom.repository.VendorRepository;
import com.oranbyte.ecom.request.VendorRequest;
import com.oranbyte.ecom.services.FileService;
import com.oranbyte.ecom.services.VendorService;
import com.oranbyte.ecom.util.Language;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

	private final VendorRepository vendorRepository;
	private final VendorMapper vendorMapper;
	private final Language lang;
	private final FileService fileService;

	@Override
	public VendorDto createVendor(VendorRequest request) throws IOException {

		fileService.uploadFile(request.getLogo(), "vendors");
		
		Vendor vendor = vendorMapper.toEntity(request);
		Vendor savedVendor = vendorRepository.save(vendor);
		return vendorMapper.toDto(savedVendor);
	}

	@Override
	public VendorDto updateVendor(Long id, VendorRequest request) {

		Vendor vendor = vendorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-not-found")));

		vendorMapper.updateEntity(request, vendor);
		Vendor updatedVendor = vendorRepository.save(vendor);
		return vendorMapper.toDto(updatedVendor);
	}

}

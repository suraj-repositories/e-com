package com.oranbyte.ecom.services.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.entity.User;
import com.oranbyte.ecom.entity.Vendor;
import com.oranbyte.ecom.exception.AppException;
import com.oranbyte.ecom.mapper.VendorMapper;
import com.oranbyte.ecom.repository.VendorRepository;
import com.oranbyte.ecom.request.VendorRequest;
import com.oranbyte.ecom.services.FileService;
import com.oranbyte.ecom.services.UserService;
import com.oranbyte.ecom.services.VendorService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.Language;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

	private final VendorRepository vendorRepository;
	private final VendorMapper vendorMapper;
	private final Language lang;
	private final FileService fileService;
	private final UserService userService;

	@Override
	public VendorDto getVendor(Long id) {
		Vendor vendor = vendorRepository.findById(id).orElseThrow(()->new RuntimeException(lang.getValue("vendor-not-found")));
		VendorDto dto = vendorMapper.toDto(vendor);

		dto.setLogo(fileService.getFullPath(dto.getLogo()));
		return dto;
	}
	
	@Override
	public VendorDto createVendor(VendorRequest request) throws IOException {

		User user = userService.getCurrentUser();

		if (vendorRepository.existsByUserId(user.getId())) {
			throw new AppException(lang.getValue("vendor-already-exists"), HttpStatus.CONFLICT);
		}

		String logo = fileService.uploadFile(request.getLogo(), "vendors");

		Vendor vendor = vendorMapper.toEntity(request);
		vendor.setUser(user);
		vendor.setLogo(logo);

		Vendor savedVendor = vendorRepository.save(vendor);
		VendorDto dto = vendorMapper.toDto(savedVendor);
		dto.setLogo(fileService.getFullPath(dto.getLogo()));

		return dto;
	}

	@Override
	public VendorDto updateVendor(Long id, VendorRequest request) {

		Vendor vendor = vendorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-not-found")));

		vendorMapper.updateEntity(request, vendor);
		Vendor updatedVendor = vendorRepository.save(vendor);
		VendorDto dto = vendorMapper.toDto(updatedVendor);
		dto.setLogo(fileService.getFullPath(dto.getLogo()));
		return dto;
	}

	@Override
	public ResponseEntity<?> updateLogo(Long id, MultipartFile logo) throws IOException {

		Vendor vendor = vendorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(lang.getValue("vendor-not-found")));
		if (vendor != null) {
			fileService.deleteIfExists(vendor.getLogo());
		}

		String uploadFile = fileService.uploadFile(logo, "vendors");
		vendor.setLogo(uploadFile);
		vendorRepository.save(vendor);
		return AppUtils.getApiResponse(true, lang.getValue("logo-updated"),
				Map.of("logo", fileService.getFullPath(uploadFile)));

	}

	

}

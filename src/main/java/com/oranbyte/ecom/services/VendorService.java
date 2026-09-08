package com.oranbyte.ecom.services;

import java.io.IOException;

import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.request.VendorRequest;

public interface VendorService {

	VendorDto createVendor(VendorRequest vendor) throws IOException;
	
	VendorDto updateVendor(Long id, VendorRequest vendor) throws IOException;
	
}

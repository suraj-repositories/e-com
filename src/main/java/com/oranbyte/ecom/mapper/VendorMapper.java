package com.oranbyte.ecom.mapper;

import org.springframework.stereotype.Component;

import com.oranbyte.ecom.dto.VendorDto;
import com.oranbyte.ecom.entity.Vendor;
import com.oranbyte.ecom.request.VendorRequest;

@Component
public class VendorMapper {

    public Vendor toEntity(VendorDto dto) {
        if (dto == null) {
            return null;
        }

        Vendor vendor = new Vendor();

        vendor.setId(dto.getId());
        vendor.setStoreName(dto.getStoreName());
        vendor.setDescription(dto.getDescription());
        vendor.setAddress(dto.getAddress());
        vendor.setLogo(dto.getLogo());
        vendor.setLatitude(dto.getLatitude());
        vendor.setLongitude(dto.getLongitude());

        return vendor;
    }
    
    public Vendor toEntity(VendorRequest request) {

        Vendor vendor = new Vendor();

        vendor.setStoreName(request.getStoreName());
        vendor.setDescription(request.getDescription());
        vendor.setAddress(request.getAddress());
        vendor.setLatitude(request.getLatitude());
        vendor.setLongitude(request.getLongitude());

        return vendor;
    }

    public VendorDto toDto(Vendor vendor) {
        if (vendor == null) {
            return null;
        }

        return new VendorDto(
                vendor.getId(),
                vendor.getStoreName(),
                vendor.getDescription(),
                vendor.getAddress(),
                vendor.getLogo(),
                vendor.getLatitude(),
                vendor.getLongitude()
        );
    }

    public VendorDto toDto(VendorRequest request) {

        if (request == null) {
            return null;
        }

        return new VendorDto(
                null,
                request.getStoreName(),
                request.getDescription(),
                request.getAddress(),
                null,
                request.getLatitude(),
                request.getLongitude()
        );
    }
    
    public void updateEntity(VendorDto dto, Vendor vendor) {

        vendor.setStoreName(dto.getStoreName());
        vendor.setDescription(dto.getDescription());
        vendor.setAddress(dto.getAddress());
        vendor.setLogo(dto.getLogo());
        vendor.setLatitude(dto.getLatitude());
        vendor.setLongitude(dto.getLongitude());
    }
    
    public void updateEntity(VendorRequest request, Vendor vendor) {

        vendor.setStoreName(request.getStoreName());
        vendor.setDescription(request.getDescription());
        vendor.setAddress(request.getAddress());
        vendor.setLatitude(request.getLatitude());
        vendor.setLongitude(request.getLongitude());
    }
}
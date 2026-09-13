package com.oranbyte.ecom.mapper;

import org.springframework.stereotype.Component;

import com.oranbyte.ecom.dto.CategoryDto;
import com.oranbyte.ecom.entity.Category;
import com.oranbyte.ecom.request.CategoryRequest;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category entity) {
        if (entity == null) {
            return null;
        }

        CategoryDto dto = new CategoryDto();

        dto.setId(entity.getId());
        dto.setParentId(entity.getParentId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setSlug(entity.getSlug());
        dto.setIsActive(entity.getIsActive());

        if (entity.getChilds() != null) {
            dto.setChilds(
                entity.getChilds()
                    .stream()
                    .map(this::toDto)
                    .toList()
            );
        }

        return dto;
    }

    public Category toEntity(CategoryRequest request) {
        Category entity = new Category();

        entity.setParentId(request.getParentId());
        entity.setName(request.getName());
//        entity.setImage(request.getImage());

        return entity;
    }
 }
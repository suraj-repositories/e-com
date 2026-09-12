package com.oranbyte.ecom.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    private Long parentId;

    private String name;

    private String image;

    private String slug;

    private List<CategoryDto> childs = new ArrayList<>();
    
    
}
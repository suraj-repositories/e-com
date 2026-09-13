package com.oranbyte.ecom.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {

	private Long id;

	private Long parentId;

	private String name;

	private String image;

	private String slug;
	
	private Boolean isActive;

	private List<CategoryDto> childs = new ArrayList<>();

	public CategoryDto(Long id, String name, String image, String slug, Long parentId, Boolean isActive) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.slug = slug;
		this.parentId = parentId;
		this.isActive = isActive;
	}
}
package com.oranbyte.ecom.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@NamedQuery(name = "Category.getChildren", query = "SELECT new com.oranbyte.ecom.dto.CategoryDto( c.id, c.name, c.image, c.slug, c.parentId, c.isActive) FROM Category c WHERE c.parentId = :parentId ORDER BY c.name")

@NamedQuery(name = "Category.getTopCategories", query = "SELECT new com.oranbyte.ecom.dto.CategoryDto( c.id, c.name, c.image, c.slug, c.parentId, c.isActive) FROM Category c WHERE c.parentId IS NULL ORDER BY c.name")

@Entity
@DynamicInsert
@Getter
@Setter
@DynamicUpdate
@Table(name = "categories")
@FilterDef(name = "activeFilter")
@Filter(name = "activeFilter", condition = "deleted_at IS NULL")
public class Category extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private Long parentId;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private String slug;

	@Column(nullable = false)
	private Boolean isActive = false;

	@Transient
	private List<Category> childs = new ArrayList<>();
}

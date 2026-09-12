package com.oranbyte.ecom.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@DynamicInsert
@Getter
@Setter
@DynamicUpdate
@Table(name = "categories")
public class Category extends BaseEntity{

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
	 
	@Transient
	private List<Category> childs = new ArrayList<>();
}

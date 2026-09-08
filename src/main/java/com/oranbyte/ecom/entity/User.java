package com.oranbyte.ecom.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@NamedQuery(name = "User.findByEmailId", query = "SELECT u FROM User u WHERE u.email = :email")

@NamedQuery(name = "User.getAllUser", query = "SELECT new com.oranbyte.ecom.dto.UserDto("
		+ "u.id, u.name, u.username, u.email, u.contactNumber, u.isActive" + ") FROM User u WHERE u.role = 'user'")

@NamedQuery(name = "User.updateIsActive", query = "UPDATE User u SET u.isActive = :isActive WHERE u.id = :id")

@NamedQuery(name = "User.getAllAdmin", query = "SELECT u.email FROM User u WHERE u.role = 'admin'")
 
@Entity
@DynamicInsert
@Getter
@Setter
@DynamicUpdate
@Table(name = "users")
public class User extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "contactNumber")
	private String contactNumber;

	@Column(name = "isActive")
	private Boolean isActive;

	@Column(name = "role")
	private String role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;

}

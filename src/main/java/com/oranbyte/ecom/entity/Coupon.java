package com.oranbyte.ecom.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "coupons")
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column
	private String code;
	
	@Column
	private String type; // enum('fixed', 'percent')
	
	@Column
	private BigDecimal value;
	
	@Column
	private BigDecimal minOrderAmount;
	
	@Column
	private Integer usageLimit;
	
	@Column
	private Integer used;
	
	@Column
	private LocalDateTime validFrom;
	
	@Column
	private LocalDateTime validUntil; 
	
}

package com.oranbyte.ecom.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class VendorReviewRequest { 
	
	private Short rating;
	
	@NotBlank(message = "Comment is required")
	@Size(max = 500, message = "Description must not exceed 500 characters")
	private String comment;

}

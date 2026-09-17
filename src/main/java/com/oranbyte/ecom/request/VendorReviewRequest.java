package com.oranbyte.ecom.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class VendorReviewRequest { 
	
	@NotNull(message = "Rating is required")
	@Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
	private Short rating;
	
	@NotBlank(message = "Comment is required")
	@Size(max = 500, message = "Description must not exceed 500 characters")
	private String comment;

}

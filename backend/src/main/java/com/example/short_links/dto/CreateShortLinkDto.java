package com.example.short_links.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShortLinkDto {
	
	@NotBlank(message = "Original URL cannot be blank")
	@Size(max = 2048, message = "Original URL cannot exceed 2048 characters")
	@Pattern(
			regexp = "^(https?|ftp|file):\\/\\/.+$",
			message = "Invalid URL format"
	)
	private String originalUrl;
}

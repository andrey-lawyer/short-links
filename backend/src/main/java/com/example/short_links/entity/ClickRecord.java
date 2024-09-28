package com.example.short_links.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClickRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "short_link_id", nullable = false)
	private ShortLink shortLink;
	
	private LocalDateTime clickedAt;
	private String ipAddress;
	private String userAgent;
	private String country;
	private String city;
	private String operatingSystem;
	private String deviceType;
}

package com.example.short_links.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ShortLink {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String originalUrl;
	
	@Column(unique = true)
	private String shortUrl;
	
	@OneToMany(mappedBy = "shortLink", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClickRecord> clickRecords = new ArrayList<>();
}

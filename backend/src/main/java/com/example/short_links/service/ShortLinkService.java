package com.example.short_links.service;

import com.example.short_links.dto.CreateShortLinkDto;
import com.example.short_links.dto.StatisticsResponseDto;
import com.example.short_links.entity.ClickRecord;
import com.example.short_links.entity.ShortLink;
import com.example.short_links.exception.ShortLinkNotFoundException;
import com.example.short_links.model.ClickRecordModel;
import com.example.short_links.model.ShortLinkModel;
import com.example.short_links.util.GeoLocationService;
import com.example.short_links.util.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.short_links.repository.ShortLinkRepository;
import com.example.short_links.util.Base62Converter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShortLinkService {
	
	@Autowired
	private ShortLinkRepository shortLinkRepository;
	
	@Autowired
	private GeoLocationService geoLocationService;
	
	
	public ShortLinkModel generateShortLink(CreateShortLinkDto createShortLinkDto) {
		// Check if the link exists
		Optional<ShortLink> existingLink = shortLinkRepository.findByOriginalUrl(createShortLinkDto.getOriginalUrl());
		if (existingLink.isPresent()) {
			return ShortLinkModel.toModel(existingLink.get());
		}
		
		// Create a new ShortLink object
		ShortLink shortLink = new ShortLink();
		shortLink.setOriginalUrl(createShortLinkDto.getOriginalUrl());
		
		// Save the object to the database to get the ID
		shortLink = shortLinkRepository.save(shortLink);
		// Generate short URL
		String shortUrl = Base62Converter.encodeBase62(shortLink.getId());
		shortLink.setShortUrl(shortUrl);
		
		// Update the object with a short URL and save it again
		shortLinkRepository.save(shortLink);
		
		return ShortLinkModel.toModel(shortLink);
	}
	
	
	public Optional<String> getOriginalUrl(String shortUrl) {
		return shortLinkRepository.findByShortUrl(shortUrl)
				.map(ShortLink::getOriginalUrl)
				.orElseThrow(() -> new ShortLinkNotFoundException("Short link not found for URL: " + shortUrl)).describeConstable();
	}
	
	
	public void recordClick(String shortUrl, String userAgent, String ipAddress) {
		Optional<ShortLink> shortLinkOptional = Optional.ofNullable(shortLinkRepository.findByShortUrl(shortUrl).orElseThrow(() -> new ShortLinkNotFoundException("Short link not found for URL: " + shortUrl)));
		
		if (shortLinkOptional.isPresent()) {
			ShortLink shortLink = shortLinkOptional.get();
			
			// Create a click record
			ClickRecord clickRecord = new ClickRecord();
			clickRecord.setShortLink(shortLink);
			clickRecord.setClickedAt(LocalDateTime.now());
			clickRecord.setIpAddress(ipAddress);
			clickRecord.setUserAgent(userAgent);
			clickRecord.setOperatingSystem(UserAgent.getOperatingSystem(userAgent));
			clickRecord.setDeviceType(UserAgent.getDeviceType(userAgent));
			
			// Get geolocation
			GeoLocationService.Location location = geoLocationService.getLocation(ipAddress);
			if (location != null) {
				clickRecord.setCountry(location.getCountry() != null ? location.getCountry() : "Unknown");
				clickRecord.setCity(location.getCity() != null ? location.getCity() : "Unknown");
			} else {
				clickRecord.setCountry("Unknown");
				clickRecord.setCity("Unknown");
			}
			
			// Save the record
			shortLink.getClickRecords().add(clickRecord);
			shortLinkRepository.save(shortLink);
		}
	}
	

	
	public StatisticsResponseDto getClickStatistics(String shortUrl, String deviceType, String operatingSystem, int page, int size) {
		Optional<ShortLink> shortLinkOptional = Optional.ofNullable(shortLinkRepository.findByShortUrl(shortUrl).orElseThrow(() -> new ShortLinkNotFoundException("Short link not found for URL: " + shortUrl)));
		
		return shortLinkOptional.map(shortLink -> {
			List<ClickRecordModel> filteredRecords = shortLink.getClickRecords().stream()
					.map(ClickRecordModel::fromEntity)
					.filter(record -> (deviceType == null || record.getDeviceType().equals(deviceType)) &&
							(operatingSystem == null || record.getOperatingSystem().equals(operatingSystem)))
					.toList();
			
			long totalCount = filteredRecords.size(); // Total number of records
			
			// Apply pagination
			List<ClickRecordModel> pagedRecords = filteredRecords.stream()
					.skip((long) page * size)
					.limit(size)
					.collect(Collectors.toList());
			
			return new StatisticsResponseDto(pagedRecords, totalCount);
		}).orElse(new StatisticsResponseDto(Collections.emptyList(), 0)); // If not found, return an empty DTO
	}
}
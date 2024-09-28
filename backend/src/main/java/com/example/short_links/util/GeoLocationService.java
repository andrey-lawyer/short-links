package com.example.short_links.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoLocationService {
	private final RestTemplate restTemplate = new RestTemplate();
	
	public Location getLocation(String ipAddress) {
		String url = "http://ip-api.com/json/" + ipAddress;
		return restTemplate.getForObject(url, Location.class);
	}
	
	@Getter
	@Setter
	public static class Location {
		private String country;
		private String city;
	}
}

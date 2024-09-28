package com.example.short_links.model;

import com.example.short_links.entity.ClickRecord;

import java.time.LocalDateTime;

import lombok.Getter;

import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ClickRecordModel {
	private Long id;
	private LocalDateTime clickedAt;
	private String ipAddress;
	private String userAgent;
	private String country;
	private String city;
	private String operatingSystem;
	private String deviceType;
	
	public static ClickRecordModel fromEntity(ClickRecord clickRecord) {
		ClickRecordModel model = new ClickRecordModel();
		model.setId(clickRecord.getId());
		model.setClickedAt(clickRecord.getClickedAt());
		model.setIpAddress(clickRecord.getIpAddress());
		model.setUserAgent(clickRecord.getUserAgent());
		model.setCountry(clickRecord.getCountry());
		model.setCity(clickRecord.getCity());
		model.setOperatingSystem(clickRecord.getOperatingSystem());
		model.setDeviceType(clickRecord.getDeviceType());
		return model;
	}
}
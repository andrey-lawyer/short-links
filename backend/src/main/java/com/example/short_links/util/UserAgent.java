package com.example.short_links.util;

public class UserAgent { public static String getOperatingSystem(String userAgent) {
	if (userAgent.toLowerCase().contains("windows")) {
		return "Windows";
	} else if (userAgent.toLowerCase().contains("mac")) {
		return "MacOS";
	} else if (userAgent.toLowerCase().contains("linux")) {
		return "Linux";
	}
	return "Unknown";
}
	
	public static String getDeviceType(String userAgent) {
		if (userAgent.toLowerCase().contains("mobile")) {
			return "Mobile";
		} else {
			return "PC";
		}
	}}

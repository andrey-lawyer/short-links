package com.example.short_links.util;

public class Base62Converter {
	
	private static final String BASE62_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int BASE = BASE62_ALPHABET.length();
	
	// Convert the numeric ID to a base62 string
	public static String encodeBase62(long num) {
		StringBuilder sb = new StringBuilder();
		while (num > 0) {
			sb.append(BASE62_ALPHABET.charAt((int)(num % BASE)));
			num /= BASE;
		}
		return sb.reverse().toString();
	}
	
}

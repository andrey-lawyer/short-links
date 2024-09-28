package com.example.short_links.model;

import com.example.short_links.entity.ShortLink;
import lombok.Getter;

import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ShortLinkModel {
	
	private String shortUrl;
	
	
	public static ShortLinkModel toModel(ShortLink shortLink) {
		ShortLinkModel shortLinkModel = new ShortLinkModel();
		shortLinkModel.setShortUrl(shortLink.getShortUrl());
		return shortLinkModel;
	}
}

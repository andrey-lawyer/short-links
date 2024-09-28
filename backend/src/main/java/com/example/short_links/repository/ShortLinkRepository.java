package com.example.short_links.repository;

import com.example.short_links.entity.ShortLink;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ShortLinkRepository extends CrudRepository<ShortLink, Long> {
	Optional<ShortLink> findByShortUrl(String shortUrl);
	Optional<ShortLink> findByOriginalUrl(String originalUrl);
}

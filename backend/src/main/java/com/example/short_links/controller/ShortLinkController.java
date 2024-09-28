package com.example.short_links.controller;

import com.example.short_links.dto.CreateShortLinkDto;
import com.example.short_links.dto.StatisticsResponseDto;
import com.example.short_links.model.ShortLinkModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.short_links.service.ShortLinkService;

@RestController
@RequestMapping("/l")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class ShortLinkController {

  @Autowired private ShortLinkService shortLinkService;

  @PostMapping("/shorten")
  public ResponseEntity<ShortLinkModel> shortenUrl(
          @Valid @RequestBody CreateShortLinkDto createShortLinkDto) {
    ShortLinkModel shortLinkModel = shortLinkService.generateShortLink(createShortLinkDto);
    // Return the full URL of the short link
    shortLinkModel.setShortUrl(shortLinkModel.getShortUrl());
    return ResponseEntity.ok(shortLinkModel);
  }

  @GetMapping("/{shortUrl}")
  public ResponseEntity<Object> redirectToOriginalUrl(
      @PathVariable String shortUrl, HttpServletRequest request) {
    String userAgent = request.getHeader("User-Agent");
    String ipAddress = request.getRemoteAddr();

    // Save the click information
    shortLinkService.recordClick(shortUrl, userAgent, ipAddress);

    return shortLinkService
        .getOriginalUrl(shortUrl)
        .map(originalUrl -> ResponseEntity.status(302).header("Location", originalUrl).build())
        .orElse(ResponseEntity.notFound().build());
  }


@GetMapping("/stats/{shortUrl}")
public ResponseEntity<StatisticsResponseDto> getStatistics(
        @PathVariable String shortUrl,
        @RequestParam(required = false) String deviceType,
        @RequestParam(required = false) String operatingSystem,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
  
  StatisticsResponseDto statisticsResponseDto = shortLinkService.getClickStatistics(shortUrl, deviceType, operatingSystem, page, size);
  return ResponseEntity.ok(statisticsResponseDto);
}
	}

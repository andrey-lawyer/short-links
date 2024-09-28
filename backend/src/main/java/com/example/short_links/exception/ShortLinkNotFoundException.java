package com.example.short_links.exception;

public class ShortLinkNotFoundException extends RuntimeException {
  public ShortLinkNotFoundException(String message) {
    super(message);
  }
}
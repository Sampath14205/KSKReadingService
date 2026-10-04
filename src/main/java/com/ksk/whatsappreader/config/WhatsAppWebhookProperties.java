package com.ksk.whatsappreader.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix="whatsapp.webhook")
public record WhatsAppWebhookProperties(String verifyToken, String appSecret) {}
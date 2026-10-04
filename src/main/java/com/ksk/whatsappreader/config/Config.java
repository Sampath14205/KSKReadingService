package com.ksk.whatsappreader.config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableConfigurationProperties(WhatsAppWebhookProperties.class)
public class Config {}
package com.ksk.whatsappreader.model;

import java.time.Instant;

public record WhatsAppMessage(String messageId, String from, String contactName, String type, String text,
                              String timestamp, Instant receivedAt) {
}
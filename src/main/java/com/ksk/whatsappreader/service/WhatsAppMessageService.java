package com.ksk.whatsappreader.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksk.whatsappreader.model.WhatsAppMessage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class WhatsAppMessageService {
    private final ObjectMapper mapper;
    private final List<WhatsAppMessage> messages = Collections.synchronizedList(new ArrayList<>());

    public WhatsAppMessageService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<WhatsAppMessage> processWebhook(String payload) {
        List<WhatsAppMessage> out = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(payload);
            for (JsonNode entry : root.path("entry")) {
                for (JsonNode change : entry.path("changes")) {
                    JsonNode value = change.path("value");
                    JsonNode contacts = value.path("contacts");
                    String name = contacts.isArray() && contacts.size() > 0 ? contacts.get(0).path("profile").path("name").asText(null) : null;
                    for (JsonNode m : value.path("messages")) {
                        String type = m.path("type").asText(null);
                        String text = "text".equals(type) ? m.path("text").path("body").asText(null) : null;
                        WhatsAppMessage item = new WhatsAppMessage(m.path("id").asText(null), m.path("from").asText(null), name, type, text, m.path("timestamp").asText(null), Instant.now());
                        messages.add(item);
                        out.add(item);
                        System.out.printf("WhatsApp message: from=%s, name=%s, type=%s, text=%s%n", item.from(), item.contactName(), item.type(), item.text());
                    }
                }
            }
            return out;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid WhatsApp webhook JSON", e);
        }
    }

    public List<WhatsAppMessage> getMessages() {
        synchronized (messages) {
            return List.copyOf(messages);
        }
    }
}
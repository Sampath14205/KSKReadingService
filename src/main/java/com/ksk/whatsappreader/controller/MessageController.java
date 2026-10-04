package com.ksk.whatsappreader.controller;

import com.ksk.whatsappreader.model.WhatsAppMessage;
import com.ksk.whatsappreader.service.WhatsAppMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final WhatsAppMessageService service;

    public MessageController(WhatsAppMessageService service) {
        this.service = service;
    }

    @GetMapping
    public List<WhatsAppMessage> getMessages() {
        return service.getMessages();
    }
}
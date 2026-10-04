package com.ksk.whatsappreader.controller;

import com.ksk.whatsappreader.config.WhatsAppWebhookProperties;
import com.ksk.whatsappreader.service.WhatsAppMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/whatsapp")
public class WhatsAppWebhookController {
    private final WhatsAppWebhookProperties props;
    private final WhatsAppMessageService service;

    public WhatsAppWebhookController(WhatsAppWebhookProperties props, WhatsAppMessageService service) {
        this.props = props;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> verify(@RequestParam(name = "hub.mode", required = false) String mode,
                                         @RequestParam(name = "hub.verify_token", required = false) String token,
                                         @RequestParam(name = "hub.challenge", required = false) String challenge) {
        if ("subscribe".equals(mode) && props.verifyToken().equals(token)) return ResponseEntity.ok(challenge);
        return ResponseEntity.status(403).body("Forbidden");
    }

    @PostMapping
    public ResponseEntity<String> receive(@RequestBody String payload) {
        service.processWebhook(payload);
        return ResponseEntity.ok("EVENT_RECEIVED");
    }
}
package org.ratelimit.ratelimitservice.controller;

import org.ratelimit.ratelimitservice.dto.RegisterClientRequest;
import org.ratelimit.ratelimitservice.dto.RegisterClientResponse;
import org.ratelimit.ratelimitservice.handler.RateLimitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/limit/api/v1")
public class RateLimitController {

    @Autowired
    private RateLimitHandler handler;

    @PostMapping("/client")
    public void registerClient(@RequestBody RegisterClientRequest request) {
        handler.handleRegisterClient(request.getClientId(), request.getLimitPerMinute());
    }

    @GetMapping("/allow/{clientId}")
    public RegisterClientResponse clientAllowed(@PathVariable("clientId") String clientId) {

        RegisterClientResponse response = null;
        try {
            response = new RegisterClientResponse(handler.isClientAllowed(clientId));
        }
        catch (Exception e) {
            response = new RegisterClientResponse(false, e.getMessage());
        }

        return response;
    }
}

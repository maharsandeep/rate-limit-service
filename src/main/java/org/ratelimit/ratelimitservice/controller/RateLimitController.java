package org.ratelimit.ratelimitservice.controller;

import org.ratelimit.ratelimitservice.dto.RegisterClientRequest;
import org.ratelimit.ratelimitservice.dto.RegisterClientResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/limit/api/v1")
public class RateLimitController {

    @PostMapping("/client")
    public RegisterClientResponse registerClient(@RequestBody RegisterClientRequest request) {
        RegisterClientResponse response = new RegisterClientResponse(true);

        return response;
    }

    @GetMapping("/allow/{clientId}")
    public void clientAllowed(@PathVariable("clientId") String clientId) {

    }
}

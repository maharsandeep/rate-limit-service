package org.ratelimit.ratelimitservice.handler;

import org.ratelimit.ratelimitservice.cache.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateLimitHandler {

    @Autowired
    private TokenCache tokenCache;

    public void handleRegisterClient(String clientId, Long limitPerMinute)
    {
        tokenCache.registerClient(clientId, limitPerMinute);
    }

    public boolean isClientAllowed(String clientId)
    {
        return tokenCache.isClientAllowed(clientId);
    }

}

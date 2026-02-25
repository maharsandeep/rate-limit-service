package org.ratelimit.ratelimitservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.ratelimit.ratelimitservice.model.ClientTokenBucket;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TokenCache {

//    private final ConcurrentHashMap<String, Long> clientTokens = new ConcurrentHashMap<>();
    // Using Caffeine cache with size 1M and entry expires after 10 mins
    private final Cache<String, ClientTokenBucket> clientTokensCache =
        Caffeine.newBuilder()
                .maximumSize(1_000_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();

    public boolean isClientAllowed(String clientId) {
        ClientTokenBucket tokenBucket = clientTokensCache.getIfPresent(clientId);

        if (tokenBucket == null) {
            throw new RuntimeException("Client not found or not registered. Please register first.");
        }

        if (!tokenBucket.isAllowed()) {
            throw new RuntimeException("Rate limit exceeded. Please try after some time.");
        }

        return true;
    }

    public void registerClient(String clientId, Long rateLimitValue) {
        ClientTokenBucket tokenBucket = clientTokensCache.getIfPresent(clientId);
        if (tokenBucket == null) {
            tokenBucket = new ClientTokenBucket(rateLimitValue);
            clientTokensCache.put(clientId, tokenBucket);
        }
        else {
            tokenBucket.refreshLimit(rateLimitValue);
        }
    }

    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void refillAllTokens() {
        clientTokensCache.asMap().values().forEach(ClientTokenBucket::refillTokens);
    }

}

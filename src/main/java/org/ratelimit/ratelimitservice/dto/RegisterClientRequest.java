package org.ratelimit.ratelimitservice.dto;

public class RegisterClientRequest {
    private String clientId;
    private Long limitPerMinute;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getLimitPerMinute() {
        return limitPerMinute;
    }

    public void setLimitPerMinute(Long limitPerMinute) {
        this.limitPerMinute = limitPerMinute;
    }
}

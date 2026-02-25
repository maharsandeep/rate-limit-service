package org.ratelimit.ratelimitservice.dto;

public class RegisterClientResponse {
    private boolean allowed;

    public RegisterClientResponse(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean getAllowed() {
        return allowed;
    }
    public void setAllowed(boolean allowed) {}
}

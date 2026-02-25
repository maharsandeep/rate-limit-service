package org.ratelimit.ratelimitservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterClientResponse {
    private boolean allowed;
    private String error;

    public RegisterClientResponse(boolean allowed) {
        this.allowed = allowed;
    }

    public RegisterClientResponse(boolean allowed, String error) {
        this.allowed = allowed;
        this.error = error;
    }

    public boolean getAllowed() {
        return allowed;
    }
    public void setAllowed(boolean allowed) {}

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

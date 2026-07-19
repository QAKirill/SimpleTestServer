package com.example.SimpleTestServer.api.context;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestContext {
    private static final List<String> DEFAULT_SCOPES = List.of("openid", "profile", "read");
    private final ThreadLocal<List<String>> customScopes = new ThreadLocal<>();

    public void setScopes(List<String> scopes) {
        this.customScopes.set(scopes);
    }

    public List<String> getScopes() {
        return (customScopes.get() != null) ? customScopes.get() : DEFAULT_SCOPES;
    }

    public void clear() {
        customScopes.remove();
    }
}

package uk.co.solong.steam4j.auth;

public class AuthorizationResult {
    private final String userId;
    private final boolean success;

    public String getUserId() {
        return userId;
    }

    public boolean isSuccess() {
        return success;
    }

    public AuthorizationResult(String userId, boolean success) {
        this.userId = userId;
        this.success = success;
    }

}

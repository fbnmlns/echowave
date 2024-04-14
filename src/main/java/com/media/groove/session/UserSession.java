package com.media.groove.session;

import com.media.groove.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserSession {
    private User authenticatedUser;

    public User getAuthenticatedUser() {
        return this.authenticatedUser;
    }

    public void setAuthenticatedUser(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public void clearSession() {
        this.authenticatedUser = null;
    }
}

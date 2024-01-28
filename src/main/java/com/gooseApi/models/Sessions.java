package com.gooseApi.models;

import org.springframework.stereotype.Component;

@Component
public class Sessions {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

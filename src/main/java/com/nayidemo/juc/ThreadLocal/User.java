package com.nayidemo.juc.ThreadLocal;

import lombok.Data;

@Data
public class User {
    private String username;
    private String userId;

    public User(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }
}

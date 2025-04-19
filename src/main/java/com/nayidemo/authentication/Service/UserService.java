package com.nayidemo.authentication.Service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String login(String username);
}

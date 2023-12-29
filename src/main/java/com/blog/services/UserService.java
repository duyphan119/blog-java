package com.blog.services;

import java.util.Optional;

import com.blog.models.User;

public interface UserService {

    Optional<User> findByEmail(String email);
}

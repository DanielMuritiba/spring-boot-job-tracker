package com.sha.translator_docs.service;

import com.sha.translator_docs.model.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> findByEmail(String email);
}

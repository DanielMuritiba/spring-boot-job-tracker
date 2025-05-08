package com.sha.translator_docs.service;

import com.sha.translator_docs.model.User;

public interface AuthenticationService {
    User signInAndReturnJWT(User signInRequest);
}

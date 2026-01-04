package com.suleyman.springboot.todos.service;

import com.suleyman.springboot.todos.request.AuthenticationRequest;
import com.suleyman.springboot.todos.request.RegisterRequest;
import com.suleyman.springboot.todos.response.AuthenticationResponse;

public interface AuthenticationService {

    void register(RegisterRequest input) throws Exception;

    AuthenticationResponse login(AuthenticationRequest request);
}

package com.suleyman.springboot.todos.service;

import com.suleyman.springboot.todos.request.PasswordUpdateRequest;
import com.suleyman.springboot.todos.response.UserResponse;

public interface UserService {

    UserResponse getUserInfo();

    void deleteUser();

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);


}

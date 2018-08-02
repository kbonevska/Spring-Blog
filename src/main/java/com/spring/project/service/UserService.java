package com.spring.project.service;

import blog.entity.User;
import com.spring.project.entity.User;

public interface UserService {
    User register(String username, String password, String email);

}

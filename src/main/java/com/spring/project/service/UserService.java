package com.spring.project.service;


import com.spring.project.bindingModel.UserBindingModel;
import com.spring.project.entity.User;

public interface UserService {
    boolean register(UserBindingModel userBindingModel);
    User findByEmail(String email);
}

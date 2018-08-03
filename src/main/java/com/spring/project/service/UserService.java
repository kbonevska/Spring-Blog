package com.spring.project.service;


import com.spring.project.entity.User;
import com.spring.project.model.UserBindingModel;

public interface UserService {
    boolean register(UserBindingModel userBindingModel);
    User findByEmail(String email);
    User getProfilePage();
}

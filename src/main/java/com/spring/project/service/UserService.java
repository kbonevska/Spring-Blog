package com.spring.project.service;


import com.spring.project.entity.Article;
import com.spring.project.entity.User;
import com.spring.project.model.UserBindingModel;

import java.util.List;

public interface UserService {
    boolean register(UserBindingModel userBindingModel);
    User findByEmail(String email);
    User getProfilePage();
    List<Article> getArticles();
}

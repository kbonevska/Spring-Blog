package com.spring.project.service;


import com.spring.project.entity.Article;
import com.spring.project.entity.Role;
import com.spring.project.entity.User;
import com.spring.project.model.UserBindingModel;
import com.spring.project.repository.ArticleRepository;
import com.spring.project.repository.RoleRepository;
import com.spring.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public boolean register(UserBindingModel userBindingModel) {
        if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
            return false;
        }
        if(findByEmail(userBindingModel.getEmail()) != null){
            return false;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(
                userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword())
        );

        Role userRole = this.roleRepository.findByName("ROLE_USER");

        user.addRole(userRole);

        this.userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public User getProfilePage() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return this.userRepository.findByEmail(principal.getUsername());
    }

    @Override
    public List<Article> getArticles() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        List<Article> articles = this.articleRepository.findAll().stream()
                .filter(article -> article.getAuthor().getEmail().equals(principal.getUsername()))
                .collect(Collectors.toList());

        return articles;

    }

}

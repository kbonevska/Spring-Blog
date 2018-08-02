package com.spring.project.controller;

import blog.entity.Article;
import blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String index(Model model) {

        List<Article> articles = this.articleRepository.findAll();

        model.addAttribute("view", "home/index");
        model.addAttribute("articles",articles);
        return "base-layout";
    }
}

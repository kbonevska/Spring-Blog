package com.spring.project.controller;


import com.spring.project.entity.Article;
import com.spring.project.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    private final ArticleRepository articleRepository;

    @Autowired
    public SearchController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping(value = "/")
    public String search(@RequestParam("title") String pSearchTerm, Model model) {
        List<Article> articles = this.articleRepository.findArticlesByTitleContains(pSearchTerm);

        model.addAttribute("searchTerm", pSearchTerm);
        model.addAttribute("view", "home/index");
        model.addAttribute("articles",articles);
        return "base-layout";

    }
}



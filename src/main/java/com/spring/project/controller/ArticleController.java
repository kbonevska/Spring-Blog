package com.spring.project.controller;

import com.spring.project.entity.Article;
import com.spring.project.entity.User;
import com.spring.project.model.ArticleBindingModel;
import com.spring.project.repository.ArticleRepository;
import com.spring.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        model.addAttribute("view", "article/create");
        return "base-layout";
    }



    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userRepository.findByEmail(userDetails.getUsername());

        Article article = new Article(
                model.getTitle(),
                model.getContent(),
                user
        );

        this.articleRepository.saveAndFlush(article);

        return "redirect:/";
    }

    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Long id) {
        if (!this.articleRepository.existsById(id)) {
            return "redirect:/";
        }

        Article article = this.articleRepository.getOne(id);

        model.addAttribute("view", "article/details");
        model.addAttribute("article", article);

        return "base-layout";
    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Long id, Model model) {
        if (!this.articleRepository.existsById(id)) {
            return "redirect:/";
        }
        Article article = this.articleRepository.getOne(id);

        model.addAttribute("view", "article/edit");
        model.addAttribute("article", article);


        return "base-layout";
    }

    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Long id, ArticleBindingModel articleBindingModel) {
        if (!this.articleRepository.existsById(id)) {
            return "redirect:/";
        }
        Article article = this.articleRepository.getOne(id);

        article.setContent(articleBindingModel.getContent());
        article.setTitle(articleBindingModel.getTitle());

        this.articleRepository.saveAndFlush(article);

        return "redirect:/article/" + article.getId();
    }
    @GetMapping("article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Long id, Model model){
        if (!this.articleRepository.existsById(id)){
            return "redirect:/";
        }
        Article article= this.articleRepository.getOne(id);

        model.addAttribute("view", "article/delete");
        model.addAttribute("article",article);

        return "base-layout";
    }
    @PostMapping("article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Long id, Model model){
        if(!this.articleRepository.existsById(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.getOne(id);

        this.articleRepository.delete(article);

        return "redirect:/";
    }


}

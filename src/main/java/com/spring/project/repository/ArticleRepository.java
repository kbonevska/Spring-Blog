package com.spring.project.repository;


import com.spring.project.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findArticlesByTitle(String pSearchTerm);
}

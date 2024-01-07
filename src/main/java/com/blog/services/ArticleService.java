package com.blog.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.blog.models.Article;

public interface ArticleService {
    Article create(Article article);

    Optional<Article> findById(String id);

    Optional<Article> findBySlug(String slug);

    Article update(Article article);

    Boolean deleteById(String id);

    Page<Article> paginate(Map<String, String> queryParams);
}

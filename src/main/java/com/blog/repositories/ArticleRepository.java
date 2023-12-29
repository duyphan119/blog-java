package com.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Article;

public interface ArticleRepository extends JpaRepository<Article, String> {
    Page<Article> findByTitleIgnoreCaseContainingOrSlugIgnoreCaseContaining(String title, String slug,
            Pageable pageable);
}

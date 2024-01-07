package com.blog.services.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.models.Article;
import com.blog.repositories.ArticleRepository;
import com.blog.services.ArticleService;
import com.blog.services.UtilService;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UtilService utilService;

    @Override
    public Article create(Article article) {
        try {
            article.setSlug(utilService.toSlug(article.getTitle()));
            return articleRepository.save(article);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<Article> findById(String id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article update(Article article) {
        try {
            article.setSlug(utilService.toSlug(article.getTitle()));
            return articleRepository.save(article);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleteById(String id) {
        try {
            articleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Page<Article> paginate(Map<String, String> queryParams) {
        Pageable pageable = utilService.getPageable(queryParams, 10);

        String q = queryParams.get("q");
        String keyword = q == null ? "" : q;
        String categorySlug = queryParams.get("cat");

        if (categorySlug != null && !categorySlug.equals("")) {
            return articleRepository.findByCategory_Slug(categorySlug, pageable);
        }

        return articleRepository.findByTitleIgnoreCaseContainingOrSlugIgnoreCaseContaining(keyword, keyword, pageable);
    }

    @Override
    public Optional<Article> findBySlug(String slug) {
        return this.articleRepository.findBySlug(slug);
    }

}

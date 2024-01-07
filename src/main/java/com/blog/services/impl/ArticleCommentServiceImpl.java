package com.blog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.models.ArticleComment;
import com.blog.repositories.ArticleCommentRepository;
import com.blog.services.ArticleCommentService;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Override
    public ArticleComment create(ArticleComment articleComment) {
        try {
            return articleCommentRepository.save(articleComment);
        } catch (Exception e) {
            return null;
        }
    }

}

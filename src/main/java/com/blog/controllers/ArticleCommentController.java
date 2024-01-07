package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blog.services.ArticleCommentService;
import com.blog.services.UtilService;

import com.blog.models.ApiResponse;
import com.blog.models.ArticleComment;

@Controller
public class ArticleCommentController {

    @Autowired
    private ArticleCommentService articleCommentService;

    @Autowired
    private UtilService utilService;

    @PostMapping("/api/article-comment")
    public ResponseEntity<String> create(@RequestBody ArticleComment articleComment) {
        ArticleComment newArticleComment = this.articleCommentService.create(articleComment);
        return ResponseEntity.ok(utilService.getApiResponseJson(new ApiResponse<>("Thành công", newArticleComment)));
    }
}

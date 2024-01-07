package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, String> {

}

package com.blog.interceptors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blog.models.Article;
import com.blog.models.Category;
import com.blog.services.ArticleService;
import com.blog.services.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SidebarArticleInterceptor implements HandlerInterceptor {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        String requestURI = request.getRequestURI();

        if (modelAndView != null && requestURI.startsWith("/article")) {
            Map<String, String> hotCategoryMap = new HashMap<>();
            hotCategoryMap.put("limit", "4");
            hotCategoryMap.put("sort_by", "name");
            hotCategoryMap.put("sort_type", "asc");
            Map<String, String> recentArticleMap = new HashMap<>();
            recentArticleMap.put("limit", "4");
            Page<Category> categoryPage = this.categoryService.paginate(recentArticleMap);
            Page<Article> articlePage = this.articleService.paginate(recentArticleMap);
            modelAndView.addObject("hotCategoryList", categoryPage.getContent());
            modelAndView.addObject("recentArticleList", articlePage.getContent());
        }
    }
}

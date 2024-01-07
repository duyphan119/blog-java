package com.blog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.models.Article;
import com.blog.models.Category;
import com.blog.services.ArticleService;
import com.blog.services.CategoryService;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Trang chủ");

        List<Category> categoryList = this.categoryService.findExciting(6);
        model.addAttribute("categoryList", categoryList);

        Map<String, String> bannerArticleMap = new HashMap<>();
        bannerArticleMap.put("limit", "2");

        Map<String, String> todayArticleMap = new HashMap<>();
        todayArticleMap.put("limit", "4");

        Map<String, String> popularArticleMap = new HashMap<>();
        popularArticleMap.put("limit", "5");

        Map<String, String> recentArticleMap = new HashMap<>();
        recentArticleMap.put("limit", "5");

        Page<Article> bannerActivePage = this.articleService.paginate(bannerArticleMap);
        List<Article> bannerArticleList = bannerActivePage.getContent();
        model.addAttribute("bannerArticleList", bannerArticleList);

        Page<Article> todayActivePage = this.articleService.paginate(todayArticleMap);
        List<Article> todayArticleList = todayActivePage.getContent();
        model.addAttribute("todayArticleList", todayArticleList);

        Page<Article> popularActivePage = this.articleService.paginate(popularArticleMap);
        List<Article> popularArticleList = popularActivePage.getContent();
        model.addAttribute("popularArticleList", popularArticleList);

        Page<Article> recentActivePage = this.articleService.paginate(recentArticleMap);
        List<Article> recentArticleList = recentActivePage.getContent();
        model.addAttribute("recentArticleList", recentArticleList);

        return "home/index";
    }
}

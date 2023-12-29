package com.blog.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.models.Article;
import com.blog.models.Category;
import com.blog.models.CustomUserDetails;
import com.blog.services.ArticleService;
import com.blog.services.CategoryService;
import com.blog.services.CloudinaryService;

@Controller
@RequestMapping("/admin")
public class ArticleController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/article")
    public String articlePage(Model model, @RequestParam Map<String, String> queryParams) {
        Page<Article> page = this.articleService.paginate(queryParams);
        String currentPage = queryParams.get("p");
        String keyword = queryParams.get("q");
        model.addAttribute("articleList", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", currentPage == null ? 1 : Integer.parseInt(currentPage));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        model.addAttribute("title", "Quản lý bài viết");
        return "admin/article/index";
    }

    @GetMapping("/article/add")
    public String addArticlePage(Model model) {
        Sort categorySort = Sort.by("name");
        categorySort.ascending();
        List<Category> categoryList = this.categoryService.findAll(categorySort);
        model.addAttribute("categoryList", categoryList);
        Article article = new Article();
        article.setIsActive(true);
        article.setImageUrl(
                "https://res.cloudinary.com/dwhjftwvw/image/upload/v1703773960/blog/tvkkqasgsg2lggdcnojw.jpg");
        article.setContent("<p>Content</p>");
        article.setTitle("Mobile Apple Planning Big Mac Redesign Ander Nigh HalfMacShare");
        article.setIntroductionText("Introduction");
        model.addAttribute("article", article);
        model.addAttribute("title", "Thêm mới bài viết");
        return "admin/article/add";
    }

    @PostMapping("/article/add")
    public String addArticle(@ModelAttribute("article") Article article,
            @RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.uploadFile(file);
            article.setImageUrl(imageUrl);
        } catch (Exception e) {
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        article.setAuthor(customUserDetails.getUser());
        if (article.getAuthor() != null) {
            return "redirect:/logon";
        }
        if (this.articleService.create(article) != null) {
            return "redirect:/admin/article";
        }

        return "admin/article/add";
    }

    @GetMapping("/article/{articleId}/edit")
    public String editArticlePage(@PathVariable("articleId") String articleId, Model model) {
        Sort categorySort = Sort.by("name");
        categorySort.ascending();
        List<Category> categoryList = this.categoryService.findAll(categorySort);
        Optional<Article> resultArticle = this.articleService.findById(articleId);
        Article article = new Article();
        if (resultArticle.isPresent()) {
            article = resultArticle.get();
        }
        model.addAttribute("article", article);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("title", "Chỉnh sửa bài viết");
        return "admin/article/edit";
    }

    @PostMapping("/article/edit")
    public String editArticle(
            @ModelAttribute("article") Article article, Model model,
            @RequestParam("image") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(file);
                article.setImageUrl(imageUrl);
            }
        } catch (Exception e) {
        }
        if (this.articleService.update(article) != null)
            return "redirect:/admin/article";
        return "admin/article/edit";
    }

    @GetMapping("/article/{articleId}/delete")
    public String deleteArticle(@PathVariable("articleId") String articleId) {
        Optional<Article> resultArticle = this.articleService.findById(articleId);

        if (resultArticle.isPresent()) {
            this.cloudinaryService.deleteURL(resultArticle.get().getImageUrl());
            this.articleService.deleteById(articleId);
        }

        return "redirect:/admin/article";
    }
}

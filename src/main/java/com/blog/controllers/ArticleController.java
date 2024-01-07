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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.models.Article;
import com.blog.models.ArticleComment;
import com.blog.models.Category;
import com.blog.models.CustomUserDetails;
import com.blog.services.ArticleService;
import com.blog.services.CategoryService;
import com.blog.services.CloudinaryService;

@Controller
public class ArticleController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/admin/article")
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

    @GetMapping("/admin/article/add")
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

    @PostMapping("/admin/article/add")
    public String addArticle(@ModelAttribute("article") Article article,
            @RequestParam("image") MultipartFile file) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        article.setAuthor(customUserDetails.getUser());
        if (article.getAuthor() == null) {
            return "redirect:/logon";
        }
        try {
            String imageUrl = cloudinaryService.uploadFile(file);
            article.setImageUrl(imageUrl);
        } catch (Exception e) {
        }
        if (this.articleService.create(article) != null) {
            return "redirect:/admin/article";
        }

        return "admin/article/add";
    }

    @GetMapping("/admin/article/{articleId}/edit")
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

    @PostMapping("/admin/article/edit")
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

    @GetMapping("/admin/article/{articleId}/delete")
    public String deleteArticle(@PathVariable("articleId") String articleId, RedirectAttributes redirectAttrs) {
        Optional<Article> resultArticle = this.articleService.findById(articleId);

        if (resultArticle.isPresent()) {
            this.cloudinaryService.deleteURL(resultArticle.get().getImageUrl());
            this.articleService.deleteById(articleId);
            redirectAttrs.addFlashAttribute("toastMsg", "Xóa thành công");
            redirectAttrs.addFlashAttribute("toastType", "success");
        }

        return "redirect:/admin/article";
    }

    @GetMapping("/article/{articleSlug}")
    public String getArticleDetailPage(@PathVariable("articleSlug") String articleSlug, Model model) {
        Optional<Article> articleResult = this.articleService.findBySlug(articleSlug);

        if (articleResult.isEmpty()) {
            return "not-found";
        }

        Article article = articleResult.get();
        ArticleComment articleComment = new ArticleComment();
        articleComment.setContent("articleSlug");
        articleComment.setEmail("duychomap4657@gmail.com");
        articleComment.setFullName("Phan Khánh Duy");
        articleComment.setArticle(article);

        model.addAttribute("article", article);
        model.addAttribute("previousArticle", article);
        model.addAttribute("nextArticle", article);
        model.addAttribute("articleComment", articleComment);

        model.addAttribute("title", article.getTitle());

        return "article/article-detail";
    }

    @GetMapping("/article")
    public String articleListPage(Model model, @RequestParam Map<String, String> queryParams) {
        String categorySlug = queryParams.get("cat");
        String currentPage = queryParams.get("p");
        String keyword = queryParams.get("q");
        String title = "Tất cả bài viết";
        if (categorySlug != null) {
            Optional<Category> categoryResult = this.categoryService.findBySlug(categorySlug);

            if (categoryResult.isPresent()) {
                Category category = categoryResult.get();
                title = category.getName();
                model.addAttribute("categoryName", category.getName());
            }
        }
        Page<Article> articlePage = this.articleService.paginate(queryParams);
        model.addAttribute("title", title);
        model.addAttribute("articleList", articlePage.getContent());
        model.addAttribute("count", articlePage.getSize());
        model.addAttribute("totalPages", articlePage.getTotalPages());
        model.addAttribute("currentPage", currentPage == null ? 1 : Integer.parseInt(currentPage));
        model.addAttribute("categorySlug", categorySlug == null ? "" : categorySlug);
        model.addAttribute("keyword", keyword == null ? "" : keyword);

        return "article/index";
    }

}

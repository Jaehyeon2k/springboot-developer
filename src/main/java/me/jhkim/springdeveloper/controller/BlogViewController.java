package me.jhkim.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.jhkim.springdeveloper.dao.Article;
import me.jhkim.springdeveloper.dao.User;
import me.jhkim.springdeveloper.dto.ArticleResponse;
import me.jhkim.springdeveloper.dto.ArticleViewResponse;
import me.jhkim.springdeveloper.service.BlogService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/article")
    public String getArticles(Model model, @AuthenticationPrincipal User user) {
        List<ArticleResponse> articles =
                blogService.findAll()
                        .stream()
                        .map(ArticleResponse::new)
                        .toList();

        model.addAttribute("articles", articles);


        return "articleList";
    }

    @GetMapping("/article/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article",new ArticleViewResponse(article));
        return "article";
    }


    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        } else {
            model.addAttribute("article", new ArticleViewResponse());
        }

        return "newArticle";
    }
}

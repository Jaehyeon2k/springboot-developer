package me.jhkim.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.jhkim.springdeveloper.dao.Article;
import me.jhkim.springdeveloper.dao.ArticleResponse;
import me.jhkim.springdeveloper.dto.AddArticleRequest;
import me.jhkim.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest articleRequest) {
        Article article = blogService.save(articleRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article);
    }

    @GetMapping("api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<Article> articles = blogService.findAll();
        List<ArticleResponse> result = articles
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(result);
    }
}


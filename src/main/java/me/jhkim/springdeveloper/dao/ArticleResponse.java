package me.jhkim.springdeveloper.dao;

import lombok.Getter;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;


    public  ArticleResponse(Article article){
        this.content = article.getContent();
        this.title = article.getTitle();
    }
}

package me.jhkim.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jhkim.springdeveloper.dao.Article;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;
    public Article toEntity() {
//        return new Article{title, content};
    return Article.builder().title(title).content(content).build();
    }
}

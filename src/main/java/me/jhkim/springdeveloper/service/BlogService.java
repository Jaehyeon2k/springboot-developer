package me.jhkim.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jhkim.springdeveloper.dao.Article;
import me.jhkim.springdeveloper.dto.AddArticleRequest;
import me.jhkim.springdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest articleRequest) {
        return blogRepository.save(articleRequest.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalAccessError("not found: " +id));
    }
}

package me.jhkim.springdeveloper.repository;

import me.jhkim.springdeveloper.dao.Article;
import org.springframework.data.jpa.repository.JpaRepository;
// Jpa를 상속 받기 때문에 애노테이션 안붙임
public interface BlogRepository extends JpaRepository<Article, Long> {
}

package me.jhkim.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jhkim.springdeveloper.dao.Article;
import me.jhkim.springdeveloper.dto.AddArticleRequest;
import me.jhkim.springdeveloper.dto.UpdateArticleRequest;
import me.jhkim.springdeveloper.repository.BlogRepository;
import me.jhkim.springdeveloper.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogService blogService;

    // 추가: 매 테스트 실행 전 DB를 초기화하여 데이터 간섭을 방지합니다.
    @BeforeEach
    public void setCleanUp() {
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "테스트";
        final String content = "블로그 글 첫 번째 입니다.";
        final AddArticleRequest article = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(article);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 블로그 글 생성")
    @Test
    public void findAllAriticles() throws Exception {
        // given : 데이터를 하나 삽입
        // blogRepository.save(new Article("title", "content"));
        final String url = "/api/articles";
        blogRepository.save(Article.builder()
                .title("title")
                .content("content")
                .build());

        // when : get 방식으로 /api/article
        final ResultActions resultActions = mockMvc
                .perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then : status OK이고 읽어온 데이터와 내용이 내가 삽입한 내용과 동일하다
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("content"))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception{
        // given (데이터 준비: 블로그글 하나 생성)
        final String url = "/api/articles/{id}";
        final String title = "블로그 제목";
        final String content = "블로그 내용";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());
        // when (실행: 위에서 생성된 블로그글을 조회)
                final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId())
                .accept(MediaType.APPLICATION_JSON));

        // then (검증: status가 200이고 조회한 블로그 제목과 내용이 위에서
        //            삽입한 그것과 동일한지 확인
                resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
                // 반한된 json 객체의 content 값이 변수 content와 동일하고
                // 반환된 json 객체의 title 값이 변수 title과 동일한지 확인
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성곤한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given

        final String url = "/api/articles/{id}";
        final  String title = "4월 16일";
        final String content = "백엔드 프로그래밍(II) 수업";
        Article savedarticle = blogRepository.save(
                Article
                .builder()
                .title(title)
                .content(content)
                .build());
        // when
        mockMvc.perform(delete(url, savedarticle.getId())).andExpect(status().isOk());
        // then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }
    @DisplayName("updateArticle: 블로그 글 수정에 성공한다")
    @Test
    public void updateArticle() throws Exception {
        // given: 레코드 생성, 변경내용 작성
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article
                .builder()
                .title(title)
                .content(content)
                .build()
        );
        final String newTitle = "JUnit에서 제목 변경";
        final String newContent = "JUnit에서 내용 변경";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when: /api/articles/생성된 레코드 id -> put 방식 요청
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then: status code가 200, repository에서 변경된 내용 검증
        result.andExpect(status().isOk());
        Article updatedArticle = blogRepository.findById(savedArticle.getId()).get();
        assertThat(updatedArticle.getTitle()).isEqualTo(newTitle);
        assertThat(updatedArticle.getContent()).isEqualTo(newContent);

    }
}
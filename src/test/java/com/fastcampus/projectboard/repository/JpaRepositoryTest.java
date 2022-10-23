package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@DisplayName("JPA 연결 테스트")
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository, @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Test
    @DisplayName("select test")
    void givenTestData_whenSelecting_thenAnyDoseNotExceptions() {
        //given


        //when
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles).isNotNull().hasSize(123);
    }

    @Test
    @DisplayName("insert test")
    void givenTestData_whenInserting_thenAnyDoseNotExceptions() {
        //given
        long previousCount = articleRepository.count();
        //when
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("uno", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");

        // When
        articleRepository.save(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @Test
    @DisplayName("update test")
    void givenTestData_whenUpdating_thenAnyDoseNotExceptions() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        //when
        Article savedArticle = articleRepository.saveAndFlush(article);

        //then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @Test
    @DisplayName("delete test")
    void givenTestData_whenDeleting_thenAnyDoseNotExceptions() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();

        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousCommentCount - deletedCommentSize);
    }

}

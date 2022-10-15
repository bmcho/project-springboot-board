package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.constant.SearchType;
import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleUpdateDto;
import com.fastcampus.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;
    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글 검색 - 해당 키워드의 게시글 리스트 반환")
    @Test
    void givenSearchParams_whenSearchingArticles_theReturnsArticleList() {
        //given


        //when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search");
        //then

        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 조회 - 게시글 반환")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnArticle() {
        //given
        long id = 1L;

        //when
        ArticleDto article = sut.searchArticle(id);

        //then
        assertThat(article).isNotNull();
    }

    @DisplayName("게시글 생성 - 게시글 정보입력 시 게시글 생성")
    @Test
    void givenArticleInfo_whenSavingArticle_thenAnyDoseNotExceptions() {
        //given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "tester", "Title", "content", "#Hash");
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        sut.saveArticle(dto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 수정 - 게시글 Id, 정보 입력 시 수정")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdateArticle_thenAnyDoseNotExceptions() {
        //given
        long id = 1L;
        ArticleUpdateDto dto = ArticleUpdateDto.of("Title", "content", "#Hash");
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        sut.updateArticle(id,dto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 삭제 - 게시글 id 입력 시 게시글 삭제")
    @Test
    void givenArticleId_whenDeleteArticle_thenAnyDoseNotExceptions() {
        //given
        long id = 1L;
        willDoNothing().given(articleRepository).delete(any(Article.class));

        //when
        sut.deleteArticle(id);

        //then
        then(articleRepository).should().delete(any(Article.class));
    }
}
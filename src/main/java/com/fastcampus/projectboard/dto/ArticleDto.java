package com.fastcampus.projectboard.dto;

import java.time.LocalDateTime;

public record ArticleDto(
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createBy
) {
    public static ArticleDto of(String title, String content, String hashtag, LocalDateTime createdAt, String createBy) {
        return new ArticleDto(title, content, hashtag, createdAt, createBy);
    }
}

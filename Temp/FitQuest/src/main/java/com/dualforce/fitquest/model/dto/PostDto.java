package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    // 게시글 ID
    private Integer postId;
    // 사용자 ID
    @NonNull
    private Integer userId;
    // 유형
    @NonNull
    private String category;
    // 제목
    @NonNull
    private String title;
    // 내용
    @NonNull
    private String content;
    // 작성 시간
    private LocalDateTime createdAt;
    // 조회 수
    private Integer views;
    // 추천 수
    private Integer likes;
    // 첨부 파일 경로
    private String attachmentPath;
    // 작성자 닉네임;
    private String nickname;
}

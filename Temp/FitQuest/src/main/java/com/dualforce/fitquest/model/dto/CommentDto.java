package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    // 댓글 ID
    private Integer commentId;
    // 게시글 ID
    @NonNull
    private Integer postId;
    // 사용자 ID
    @NonNull
    private Integer userId;
    // 내용
    @NonNull
    private String content;
    // 작성 시간
    private LocalDateTime createdAt;
    // 추천 수
    private Integer likes;
    // 작성자 닉네임;
    private String nickname;
}

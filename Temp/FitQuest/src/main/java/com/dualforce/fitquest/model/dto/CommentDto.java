package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Integer commentId;
    @NonNull
    private Integer postId;
    @NonNull
    private Integer userId;
    @NonNull
    private String content;
    private LocalDateTime createdAt;
    private Integer likes;
}

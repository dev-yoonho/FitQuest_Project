package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Integer postId;
    @NonNull
    private Integer userId;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private LocalDateTime createdAt;
    private Integer views;
    private Integer likes;
    private String attachmentPath;
}

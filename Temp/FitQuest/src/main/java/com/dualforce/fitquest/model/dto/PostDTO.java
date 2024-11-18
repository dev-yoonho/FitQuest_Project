package com.dualforce.fitquest.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PostDTO {
    private Long postId;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private Long userId;
    private Integer views;
    private Integer likes;
    private String attachmentPath;
}
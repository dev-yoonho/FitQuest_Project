package com.dualforce.fitquest.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CommentDTO {
    private Long commentId;
    @NonNull
    private Long postId;
    @NonNull
    private Long userId;
    @NonNull
    private String content;
    private Integer likes;
}

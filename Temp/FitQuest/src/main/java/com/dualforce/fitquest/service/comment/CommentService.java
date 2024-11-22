package com.dualforce.fitquest.service.comment;

import com.dualforce.fitquest.model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    // 댓글 등록
    int createComment(CommentDto comment);

    // 댓글 열람
    List<CommentDto> readAllComments(int postId);

    // 댓글 수정
    int editComment(CommentDto comment);

    // 댓글 삭제
    int removeComment(int commentId);

    // 베스트 댓글 표춣
    CommentDto showBestComment(int postId);

    // 댓글 추천
    void recommendComment(int commentId);
}

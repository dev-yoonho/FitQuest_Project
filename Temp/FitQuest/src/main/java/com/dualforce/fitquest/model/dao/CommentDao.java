package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.CommentDto;

import java.util.List;

public interface CommentDao {
    // 댓글 등록
    int insertComment(CommentDto comment);

    // 게시글의 댓글 조회
    List<CommentDto> selectPostComment(int postId);

    // 특정 사용자 닉네임으로 댓글 조회(관리자 전용)
    List<CommentDto> selectUserNicknameComment(String userNickname);

    // 댓글 수정(관리자 전용)
    int updateComment(CommentDto comment);

    // 댓글 삭제
    int deleteComment(CommentDto comment);

    // 댓글 추천수 증가
    int incrementCommentLikes(int commentId);

    // 베스트 댓글 1개 표시
    CommentDto selectBestComment(int postId);
}

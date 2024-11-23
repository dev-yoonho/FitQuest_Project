package com.dualforce.fitquest.service.commentbot;

import java.util.List;

public interface CommentBotService {
    // 조건을 만족하는 게시글 Id 목록
    List<Integer> findEligiblePosts();

    // 특정 게시글 챗봇 댓글 생성
    void addChatbotCommentToPost(int postId, String comment);

    // 조건에 맞는 모든 게시글에 생성된 챗봇 댓글 등록
    void processChatbotComments();
}

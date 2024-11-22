package com.dualforce.fitquest.service.commentbot;

import com.dualforce.fitquest.model.dao.PostDao;
import com.dualforce.fitquest.model.dto.CommentDto;
import com.dualforce.fitquest.service.comment.CommentServiceImpl;

import java.util.List;

public class CommentBotServiceimpl implements CommentBotService {
    private final PostDao postDao;
    private final CommentServiceImpl commentService;

    public CommentBotServiceimpl(PostDao postDao, CommentServiceImpl commentService) {
        this.postDao = postDao;
        this.commentService = commentService;
    }

    @Override
    public List<Integer> findEligiblePosts() {
        return postDao.selectEligiblePosts();
    }

    @Override
    public void addChatbotCommentToPost(int postId) {
        String postContent = postDao.selectPostById(postId).getContent();
        // Spring AI를 사용해서 바로 응답을 받을 수 있나? API Key는 있어.

    }

    @Override
    public void processChatbotComments() {
        List<Integer> eligiblePosts = findEligiblePosts();
        for (Integer postId : eligiblePosts) {
            addChatbotCommentToPost(postId);
        }
    }
}

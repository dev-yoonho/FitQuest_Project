package com.dualforce.fitquest.service.comment;

import com.dualforce.fitquest.model.dao.CommentDao;
import com.dualforce.fitquest.model.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public int createComment(CommentDto comment) {
        commentDao.insertComment(comment);
        return comment.getCommentId();
    }

    @Override
    public List<CommentDto> readAllComments(int postId) {
        return commentDao.selectPostComment(postId);
    }

    @Override
    public int editComment(CommentDto comment) {
        CommentDto existingComment = commentDao.selectComment(comment.getCommentId());
        if (existingComment == null) {
            throw new IllegalArgumentException("Comment not found with ID: " + comment.getCommentId());
        }

        return commentDao.updateComment(comment);
    }

    @Override
    public int removeComment(int commentId) {
        CommentDto existingComment = commentDao.selectComment(commentId);
        if (existingComment == null) {
            throw new IllegalArgumentException("Comment not found with ID: " + commentId);
        }

        return commentDao.deleteComment(commentId);
    }

    @Override
    public CommentDto showBestComment(int postId) {
        return commentDao.selectBestComment(postId);
    }

    @Override
    public void recommendComment(int commentId) {
        CommentDto existingComment = commentDao.selectComment(commentId);
        if (existingComment == null) {
            throw new IllegalArgumentException("Comment not found with ID: " + commentId);
        }

        commentDao.incrementCommentLikes(commentId);
    }
}

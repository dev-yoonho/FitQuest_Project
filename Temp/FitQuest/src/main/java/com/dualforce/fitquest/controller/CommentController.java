package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.CommentDto;
import com.dualforce.fitquest.service.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 등록
    @PostMapping
    public ResponseEntity<Integer> createComment(@RequestBody CommentDto comment) {
        int commentId = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentId);
    }

    // 특정 게시글의 모든 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> readAllComments(@PathVariable int postId) {
        List<CommentDto> comments = commentService.readAllComments(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Integer> editComment(@PathVariable int commentId, @RequestBody CommentDto comment) {
        try {
            comment.setCommentId(commentId);
            int updatedId = commentService.editComment(comment);
            return ResponseEntity.ok(updatedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Integer> removeComment(@PathVariable int commentId) {
        try {
            int deletedId = commentService.removeComment(commentId);
            return ResponseEntity.ok(deletedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글의 베스트 댓글 조회
    @GetMapping("/post/{postId}/best")
    public ResponseEntity<CommentDto> showBestComment(@PathVariable int postId) {
        CommentDto bestComment = commentService.showBestComment(postId);
        return ResponseEntity.ok(bestComment);
    }

    // 댓글 추천
    @PostMapping("/{commentId}/recommend")
    public ResponseEntity<Void> recommendComment(@PathVariable int commentId) {
        try {
            commentService.recommendComment(commentId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

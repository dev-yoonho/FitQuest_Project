package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.CommentDto;
import com.dualforce.fitquest.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "신규 댓글 등록", description = "새로운 댓글을 등록합니다.")
    public ResponseEntity<Integer> createComment(@RequestBody CommentDto comment) {
        int commentId = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentId);
    }

    // 특정 게시글의 모든 댓글 조회
    @GetMapping("/post/{postId}")
    @Operation(summary = "특정 게시글 댓글 조회", description = "특정 게시글의 모든 댓글을 조회합니다.")
    public ResponseEntity<List<CommentDto>> readAllComments(@PathVariable int postId) {
        List<CommentDto> comments = commentService.readAllComments(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    @Operation(summary = "기존 댓글 수정", description = "기존 댓글을 수정합니다.")
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
    @Operation(summary = "기존 댓글 삭제", description = "기존 댓글을 삭제합니다.")
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
    @Operation(summary = "베스트 댓글 조회", description = "특정 게시글의 베스트 댓글 1개를 조회합니다.")
    public ResponseEntity<CommentDto> showBestComment(@PathVariable int postId) {
        CommentDto bestComment = commentService.showBestComment(postId);
        return ResponseEntity.ok(bestComment);
    }

    // 댓글 추천
    @PostMapping("/{commentId}/recommend")
    @Operation(summary = "기존 댓글 추천", description = "기존 댓글을 추천합니다.")
    public ResponseEntity<Void> recommendComment(@PathVariable int commentId) {
        try {
            commentService.recommendComment(commentId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

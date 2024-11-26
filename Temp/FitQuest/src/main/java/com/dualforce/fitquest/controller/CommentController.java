package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.CommentDto;

import com.dualforce.fitquest.service.admin.AdminService;
import com.dualforce.fitquest.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final AdminService adminService;

    public CommentController(CommentService commentService, AdminService adminService) {
        this.commentService = commentService;
        this.adminService = adminService;
    }

    // 댓글 등록
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 등록", description = "로그인한 사용자가 댓글을 등록합니다.")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment) {
        int commentId = commentService.createComment(comment);
        CommentDto createdComment = commentService.readAllComments(comment.getPostId())
                .stream()
                .filter(c -> c.getCommentId() == commentId)
                .findFirst()
                .orElse(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    // 특정 게시글의 모든 댓글 조회
    @GetMapping("/post/{postId}")
    @Operation(summary = "게시글 댓글 조회", description = "특정 게시글의 모든 댓글을 조회합니다.")
    public ResponseEntity<List<CommentDto>> readAllComments(@PathVariable int postId) {
        List<CommentDto> comments = commentService.readAllComments(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    @PreAuthorize("@securityService.isCommentOwner(authentication, #commentId)")
    @Operation(summary = "댓글 수정", description = "댓글 작성자가 댓글을 수정합니다.")
    public ResponseEntity<CommentDto> editComment(@PathVariable int commentId, @RequestBody CommentDto comment) {
        comment.setCommentId(commentId);
        commentService.editComment(comment);
        CommentDto updatedComment = commentService.readAllComments(comment.getPostId())
                .stream()
                .filter(c -> c.getCommentId() == commentId)
                .findFirst()
                .orElse(null);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @PreAuthorize("@securityService.isCommentOwner(authentication, #commentId)")
    @Operation(summary = "댓글 삭제", description = "댓글 작성자가 댓글을 삭제합니다.")
    public ResponseEntity<String> removeComment(@PathVariable int commentId) {
        commentService.removeComment(commentId);
        return ResponseEntity.ok("Comment with ID " + commentId + " has been deleted.");
    }

    // 게시글의 베스트 댓글 조회
    @GetMapping("/post/{postId}/best")
    @Operation(summary = "베스트 댓글 조회", description = "특정 게시글의 베스트 댓글을 조회합니다.")
    public ResponseEntity<CommentDto> showBestComment(@PathVariable int postId) {
        CommentDto bestComment = commentService.showBestComment(postId);
        return ResponseEntity.ok(bestComment);
    }

    // 댓글 추천
    @PostMapping("/{commentId}/recommend")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 추천", description = "로그인한 사용자가 댓글을 추천합니다.")
    public ResponseEntity<String> recommendComment(@PathVariable int commentId) {
        commentService.recommendComment(commentId);
        return ResponseEntity.ok("Comment with ID " + commentId + " has been recommended.");
    }

    // 내 댓글 조회 추가
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.userId")
    @Operation(summary = "사용자가 작성한 댓글 조회", description = "특정 사용자가 작성한 댓글을 조회합니다.")
    public ResponseEntity<List<CommentDto>> readCommentsByUser(@PathVariable int userId) {
        List<CommentDto> userComments = adminService.readCommentsByUserId(userId);
        return ResponseEntity.ok(userComments);
    }

}


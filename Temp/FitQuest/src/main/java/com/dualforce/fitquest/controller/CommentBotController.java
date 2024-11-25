package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.service.commentbot.CommentBotService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-bot")
public class CommentBotController {

    private final CommentBotService commentBotService;

    public CommentBotController(CommentBotService commentBotService) {
        this.commentBotService = commentBotService;
    }

    // 수동으로 챗봇 댓글 처리 트리거
    @PostMapping("/process")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "챗봇 댓글 처리 트리거", description = "관리자가 수동으로 챗봇 댓글 처리를 트리거합니다.")
    public ResponseEntity<String> processChatbotComments() {
        try {
            commentBotService.processChatbotComments();
            return ResponseEntity.ok("Chatbot comments processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process chatbot comments: " + e.getMessage());
        }
    }

    // 특정 게시글에 챗봇 댓글 추가
    @PostMapping("/{postId}/add-comment")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "게시글에 챗봇 댓글 추가", description = "관리자가 특정 게시글에 챗봇 댓글을 수동으로 추가합니다.")
    public ResponseEntity<String> addChatbotCommentToPost(
            @PathVariable int postId,
            @RequestBody String commentContent) {
        try {
            commentBotService.addChatbotCommentToPost(postId, commentContent);
            return ResponseEntity.ok("Chatbot comment added successfully to post ID " + postId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add chatbot comment: " + e.getMessage());
        }
    }
}


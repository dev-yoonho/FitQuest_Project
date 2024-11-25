package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.service.commentbot.CommentBotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addChatbotCommentToPost(
            @PathVariable int postId,
            @RequestBody String commentContent) {
        try {
            commentBotService.addChatbotCommentToPost(postId, commentContent);
            return ResponseEntity.ok("Chatbot comment added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add chatbot comment: " + e.getMessage());
        }
    }

    // 특정 게시글에 대한 챗봇 답변 생성 및 추가
    @PostMapping("/{postId}/generate-and-add")
    public ResponseEntity<String> generateAndAddChatbotCommentToPost(@PathVariable int postId) {
        try {
            String postContent = commentBotService.findEligiblePosts()
                    .stream()
                    .filter(id -> id == postId)
                    .findFirst()
                    .map(id -> "챗봇 답변을 작성 중입니다.") // 예제 데이터 대체
                    .orElse("해당 게시글은 챗봇 대상이 아닙니다.");

            if ("해당 게시글은 챗봇 대상이 아닙니다.".equals(postContent)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(postContent);
            }

            commentBotService.processChatbotComments(); // 이 부분은 적절한 내용을 추가
            return ResponseEntity.ok("Chatbot comment created and added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate and add chatbot comment: " + e.getMessage());
        }
    }
}

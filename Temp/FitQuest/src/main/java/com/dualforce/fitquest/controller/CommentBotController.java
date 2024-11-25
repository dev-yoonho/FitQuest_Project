package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.service.commentbot.CommentBotService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "조건에 맞는 게시글에 챗봇 댓글 등록", description = "조건에 맞는 게시글에 LLM으로 생성된 댓글을 등록합니다.")
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
    @Operation(summary = "특정 게시글 관련 챗봇 답변 생성", description = "게시글의 내용을 LLM에 전송해서 댓글 내용을 생성하고 댓글 객체로 변환합니다.")
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

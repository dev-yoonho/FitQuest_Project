package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.*;
import com.dualforce.fitquest.service.admin.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 전체 사용자 조회
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "전체 사용자 조회", description = "모든 사용자를 조회합니다.")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = adminService.readAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(users);
    }

    // 특정 사용자의 게시글 조회
    @GetMapping("/users/{userId}/posts")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "특정 사용자의 게시글 조회", description = "특정 사용자의 게시글을 사용자 ID를 이용해 조회합니다.")
    public ResponseEntity<Object> getPostsByUserId(@PathVariable int userId) {
        List<PostDto> posts = adminService.readPostsByUserId(userId);
        if (posts.isEmpty()) {
            return ResponseEntity.status(404).body("No posts found for user ID: " + userId);
        }
        return ResponseEntity.ok(posts);
    }

    // 특정 사용자의 댓글 조회
    @GetMapping("/users/{nickname}/comments")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "특정 사용자의 댓글 조회", description = "특정 사용자의 댓글을 사용자 닉네임을 이용해 조회합니다.")
    public ResponseEntity<Object> getCommentsByNickname(@PathVariable String nickname) {
        List<CommentDto> comments = adminService.readCommentsByNickname(nickname);
        if (comments.isEmpty()) {
            return ResponseEntity.status(404).body("No comments found for nickname: " + nickname);
        }
        return ResponseEntity.ok(comments);
    }

    // 특정 사용자의 운동 기록 조회
    @GetMapping("/users/{userId}/exercises")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "특정 사용자의 운동 기록 조회", description = "특정 사용자의 운동 기록을 사용자 ID를 이용해 조회합니다.")
    public ResponseEntity<Object> getExercisesByUserId(@PathVariable int userId) {
        List<ExerciseDto> exercises = adminService.readExercisesByUserId(userId);
        if (exercises.isEmpty()) {
            return ResponseEntity.status(404).body("No exercises found for user ID: " + userId);
        }
        return ResponseEntity.ok(exercises);
    }

    // 특정 사용자의 식단 기록 조회
    @GetMapping("/users/{userId}/diets")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "특정 사용자의 식단 기록 조회", description = "특정 사용자의 식단 기록을 사용자 ID를 이용해 조회합니다.")
    public ResponseEntity<Object> getDietsByUserId(@PathVariable int userId) {
        List<DietDto> diets = adminService.readDietsByUserId(userId);
        if (diets.isEmpty()) {
            return ResponseEntity.status(404).body("No diets found for user ID: " + userId);
        }
        return ResponseEntity.ok(diets);
    }
}

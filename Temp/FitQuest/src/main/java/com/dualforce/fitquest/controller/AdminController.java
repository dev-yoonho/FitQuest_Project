package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.*;
import com.dualforce.fitquest.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = adminService.readAllUsers();
        return ResponseEntity.ok(users);
    }

    // 특정 사용자의 게시글 조회
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable int userId) {
        List<PostDto> posts = adminService.readPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    // 특정 사용자의 댓글 조회
    @GetMapping("/users/{nickname}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByNickname(@PathVariable String nickname) {
        List<CommentDto> comments = adminService.readCommentsByNickname(nickname);
        return ResponseEntity.ok(comments);
    }

    // 특정 사용자의 운동 기록 조회
    @GetMapping("/users/{userId}/exercises")
    public ResponseEntity<List<ExerciseDto>> getExercisesByUserId(@PathVariable int userId) {
        List<ExerciseDto> exercises = adminService.readExercisesByUserId(userId);
        return ResponseEntity.ok(exercises);
    }

    // 특정 사용자의 식단 기록 조회
    @GetMapping("/users/{userId}/diets")
    public ResponseEntity<List<DietDto>> getDietsByUserId(@PathVariable int userId) {
        List<DietDto> diets = adminService.readDietsByUserId(userId);
        return ResponseEntity.ok(diets);
    }
}

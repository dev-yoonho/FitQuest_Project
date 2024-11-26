package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.PostDto;
import com.dualforce.fitquest.service.admin.AdminService;
import com.dualforce.fitquest.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final AdminService adminService;

    public PostController(PostService postService, AdminService adminService) {
        this.postService = postService;
        this.adminService = adminService;
    }

    // 게시글 등록
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "게시글 등록", description = "로그인한 사용자가 게시글을 등록합니다.")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post) {
        int postId = postService.createPost(post);
        PostDto createdPost = postService.readPost(postId); // 생성된 게시글 정보 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // 전체 게시글 조회
    @GetMapping
    @Operation(summary = "전체 게시글 조회", description = "전체 게시글을 조회합니다.")
    public ResponseEntity<List<PostDto>> readAllPosts() {
        List<PostDto> posts = postService.readAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 조건 검색 게시글 조회
    @GetMapping("/search")
    @Operation(summary = "게시글 조건 검색", description = "게시글을 조건에 따라 검색합니다.")
    public ResponseEntity<List<PostDto>> readConditionSearchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String nickname,
            @RequestParam(defaultValue = "created_at_reverse") String sortBy,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        // 기본값 적용 확인
        offset = Math.max(offset, 0);
        limit = Math.max(limit, 1);

        List<PostDto> posts = postService.readConditionSearchPosts(title, content, category, nickname, sortBy, offset, limit);

        // 총 게시글 수 계산
        int totalPosts = postService.countTotalPosts(title, content, category, nickname);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(totalPosts))
                .body(posts);
    }


    // 특정 게시글 조회 및 조회 수 증가
    @GetMapping("/{postId}")
    @Operation(summary = "게시글 조회 및 조회 수 증가", description = "특정 게시글을 조회하고 조회수를 증가시킵니다.")
    public ResponseEntity<PostDto> readPost(@PathVariable int postId) {
        PostDto post = postService.readPost(postId);
        return ResponseEntity.ok(post);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    @PreAuthorize("@securityService.isPostOwner(authentication, #postId)")
    @Operation(summary = "게시글 수정", description = "게시글 작성자가 게시글을 수정합니다.")
    public ResponseEntity<PostDto> editPost(@PathVariable int postId, @RequestBody PostDto post) {
        post.setPostId(postId);
        postService.editPost(post);
        PostDto updatedPost = postService.readPost(postId); // 수정된 게시글 정보 반환
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    @PreAuthorize("@securityService.isPostOwner(authentication, #postId)")
    @Operation(summary = "게시글 삭제", description = "게시글 작성자가 게시글을 삭제합니다.")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Post with ID " + postId + " has been deleted.");
    }

    // 게시글 추천
    @PostMapping("/{postId}/recommend")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "게시글 추천", description = "로그인한 사용자가 게시글을 추천합니다.")
    public ResponseEntity<String> recommendPost(@PathVariable int postId) {
        postService.recommendPost(postId);
        return ResponseEntity.ok("Post with ID " + postId + " has been recommended.");
    }

    // 내 작성글 조회 추가
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.userId")
    @Operation(summary = "사용자가 작성한 게시글 조회", description = "특정 사용자가 작성한 게시글을 조회합니다.")
    public ResponseEntity<List<PostDto>> readPostsByUser(@PathVariable int userId) {
        logger.info("Fetching posts for user ID: " + userId);
        List<PostDto> userPosts = adminService.readPostsByUserId(userId);
        return ResponseEntity.ok(userPosts);
    }
}

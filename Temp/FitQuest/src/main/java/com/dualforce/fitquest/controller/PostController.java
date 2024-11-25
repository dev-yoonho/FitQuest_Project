package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.model.dto.PostDto;
import com.dualforce.fitquest.service.post.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<Integer> createPost(@RequestBody PostDto post) {
        int postId = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }

    // 전체 게시글 조회
    @GetMapping
    public ResponseEntity<List<PostDto>> readAllPosts() {
        List<PostDto> posts = postService.readAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 조건 검색 게시글 조회
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> readConditionSearchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String nickname,
            @RequestParam(defaultValue = "created_at_reverse") String sortBy,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {
        List<PostDto> posts = postService.readConditionSearchPosts(title, content, category, nickname, sortBy, offset, limit);
        return ResponseEntity.ok(posts);
    }

    // 특정 게시글 조회 및 조회 수 증가
    @GetMapping("/{postid}")
    public ResponseEntity<PostDto> readPost(@PathVariable int postid) {
        try {
            PostDto post = postService.readPost(postid);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글 수정
    @PutMapping("/{postid}")
    public ResponseEntity<Integer> editPost(@PathVariable int postid, @RequestBody PostDto post) {
        try {
            post.setPostId(postid);
            int updatedId = postService.editPost(post);
            return ResponseEntity.ok(updatedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{postid}")
    public ResponseEntity<Integer> deletePost(@PathVariable int postid) {
        try {
            int deletedId = postService.deletePost(postid);
            return ResponseEntity.ok(deletedId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 게시글 추천
    @PostMapping("/{postid}/recommend")
    public ResponseEntity<Void> recommendPost(@PathVariable int postid) {
        try {
            postService.recommendPost(postid);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

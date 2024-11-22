package com.dualforce.fitquest.service.post;

import com.dualforce.fitquest.model.dto.PostDto;

import java.util.List;

public interface PostService {
    // 게시글 등록
    int createPost(PostDto post);

    // 게시글 목록 표시
    List<PostDto> readAllPosts();

    // 게시글 수정
    int editPost(PostDto post);

    // 게시글 삭제
    int deletePost(int postid);

    // 게시글 조건 검색
    List<PostDto> readConditionSearchPosts(String title, String content, String category, String nickname, String sortBy, int offset, int limit);

    // 게시글 상세 페이지
    PostDto readPost(int postid);

    // 게시글 추천
    void recommendPost(int postid);
}

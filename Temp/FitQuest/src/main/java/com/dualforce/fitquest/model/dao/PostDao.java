package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.PostDto;

import java.util.List;
import java.util.Map;

public interface PostDao {
    // 게시글 등록
    int insertPost(PostDto post);

    // 게시글 조건 검색
    List<PostDto> selectPostByConditions(String title, String content, String category, String nickname, String sortBy, int offset, int limit);

    // 게시글 Id로 개별 조회
    PostDto selectPostById(int postId);

    // 게시글 사용자 Id로 조회
    List<PostDto> selectPostsByUserId(int userId);

    // 게시글 수정
    int updatePost(PostDto post);

    // 게시글 삭제
    int deletePost(int postId);

    // 조회 수 증가
    int incrementViews(int postId);

    // 추천 수 증가
    int incrementLikes(int postId);

    // 조건을 만족하는 게시글 검색
    List<Integer> selectEligiblePosts();

    // 조건에 맞는 전체 게시글 수 계산
    int countPostsByConditions(String title, String content, String category, String nickname);
}

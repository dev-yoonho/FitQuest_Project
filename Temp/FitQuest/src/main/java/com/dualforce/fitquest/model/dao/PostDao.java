package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.PostDto;

import java.util.List;

public interface PostDao {
    // 게시글 등록
    int insertPost(PostDto post);

    // 게시글 조회(제목)
    List<PostDto> selectPostByTitle(String title);

    // 게시글 조회(내용)
    List<PostDto> selectPostByContent(String content);

    // 게시글 조회(제목 + 내용)
    List<PostDto> selectPostByTitleContent(String title, String content);

    // 게시글 조회(카테고리)
    List<PostDto> selectPostByCategory(String category);

    // 게시글 조회(사용자 닉네임)
    List<PostDto> selectPostByNickname(String nickname);

    // 게시글 전체 조회(등록순, 최신순, 추천순, 조회순)
    List<PostDto> selectAllPostWithPaging(String sortBy, int offset, int limit);

    // 게시글 수정
    int updatePost(PostDto post);

    // 게시글 삭제
    int deletePost(int postId);

    // 조회 수 증가
    int incrementViews(int postId);

    // 추천 수 증가
    int incrementLikes(int postId);
}

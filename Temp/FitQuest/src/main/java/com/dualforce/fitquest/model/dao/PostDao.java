package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.PostDto;
import org.apache.ibatis.annotations.Param;

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

    // 게시글 조회(사용자 ID)
    // 사용자는 닉네임을 입력하지만 실제 DB에서는 ID로 조회하게 됨
    List<PostDto> selectPostByUserNickname(String userNickname);

    // 게시글 전체 조회(등록순, 최신순, 추천순, 조회순)
    List<PostDto> selectAllPostWithPaging(@Param("sortBy") String sortBy, @Param("offset") int offset, @Param("limit") int limit);

    // 게시글 수정
    int updatePost(PostDto post);

    // 게시글 삭제
    int deletePost(int postId);

    // 조회수 증가
    int incrementViews(int postId);
}

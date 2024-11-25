package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.UserDto;

import java.util.List;

public interface UserDao {
    // 사용자 생성
    int insertUser(UserDto user);

    // 사용자 조회(id)
    UserDto selectUserById(int id);

    // 사용자 조회(이메일)
    UserDto selectUserByEmail(String email);

    // 사용자 조회(닉네임)
    UserDto selectUserByNickname(String nickname);

    // 사용자 전체 조회
    List<UserDto> selectAllUsers();

    // 사용자 수정
    int updateUser(UserDto user);
    
    // 사용자 삭제
    int deleteUser(int id);

    // 특정 운동 기록의 소유자 조회
    int selectExerciseOwnerById(int exerciseId);

    // 특정 식단 기록의 소유자 조회
    int selectDietOwnerById(int dietId);

    // 특정 게시글 소유자 ID 조회
    int selectPostOwnerById(int postId);

    // 특정 댓글 소유자 ID 조회
    int selectCommentOwnerById(int commentId);
}

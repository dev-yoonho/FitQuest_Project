package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.UserDto;

import java.util.List;

public interface UserDao {
    // 사용자 생성
    int insertUser(UserDto user);

    // 사용자 조회(id)
    UserDto selectUserById(int id);

    // 사용자 조회(유저 네임)
    UserDto selectUserByUsername(String username);

    // 사용자 전체 조회
    List<UserDto> selectAllUsers();

    // 사용자 수정
    int updateUser(UserDto user);
    
    // 사용자 삭제
    int deleteUser(int id);

    // 기능에 따른 추가 필요
}

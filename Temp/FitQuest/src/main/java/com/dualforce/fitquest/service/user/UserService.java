package com.dualforce.fitquest.service.user;

import com.dualforce.fitquest.model.dto.DietDto;
import com.dualforce.fitquest.model.dto.ExerciseDto;
import com.dualforce.fitquest.model.dto.UserDto;

import java.util.List;

public interface UserService {
    //회원가입
    int createUser(UserDto user);

    //회원 정보 열람
    UserDto readUser(int id);
    UserDto readUserByNickname(String nickname);
    UserDto readUserByEmail(String email);

    //회원 정보 수정
    int editUser(UserDto user);

    //본인 전체 운동 기록 조회
    List<ExerciseDto> readAllMyExercises(int userId);

    //본인 전체 식단 기록 조회
    List<DietDto> readAllMyDiets(int userId);

    //회원 탈퇴
    int removeUser(int id);

}

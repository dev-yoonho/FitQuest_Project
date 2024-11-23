package com.dualforce.fitquest.service.admin;

import com.dualforce.fitquest.model.dto.*;

import java.util.List;

public interface AdminService {
    // 전체 사용자 조회
    List<UserDto> readAllUsers();

    // 사용자별 게시글 조회
    List<PostDto> readPostsByUserId(int userId);

    // 사용자별 댓글 조회
    List<CommentDto> readCommentsByNickname(String nickname);

    // 전체 운동 기록 조회
    List<ExerciseDto> readExercisesByUserId(int userId);

    // 전체 식단 기록 조회
    List<DietDto> readDietsByUserId(int userId);
}

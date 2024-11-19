package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.ExerciseDto;

import java.util.List;

public interface ExerciseDao {
    // 운동 기록 생성
    void insertExercise(ExerciseDto exercise);

    // 운동 기록 조회(사용자 ID)
    List<ExerciseDto> selectExercisesByUserId(int userId);

    // 운동 기록 조회(운동 ID)
    ExerciseDto selectExerciseById(int exerciseId);

    // 운동 기록 조회(사용자 ID + 날짜 범위)
    List<ExerciseDto> selectExercisesByUserIdDateRange(int userId, String startDate, String endDate);

    // 운동 기록 조회(사용자 ID + 운동 유형)
    List<ExerciseDto> selectExercisesByUserIdExerciseType(int userId, String exerciseType);

    // 전체 운동 기록 조회(관리자 전용)
    List<ExerciseDto> selectAllExercises();

    // 운동 기록 수정
    void updateExercise(ExerciseDto exercise);

    // 운동 기록 삭제(운동 ID)
    void deleteExercise(int exerciseId);

    // 기능에 따른 추가 필요
}

package com.dualforce.fitquest.service.exercise;

import com.dualforce.fitquest.model.dto.ExerciseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ExerciseService {
    // 당일 운동 기록 등록
    int createExercise(ExerciseDto exerciseDto);

    // 당일 운동 기록 조회
    List<ExerciseDto> readTodayMyExercises(int userId);

    // 당일 운동 기록 수정
    int editExercise(ExerciseDto exerciseDto);

    // 당일 운동 기록 삭제
    int removeExercise(int exerciseId);

    // 당월 운동한 날짜들 반환
    List<LocalDate> getExerciseDatesForMonth(int userId, int year, int month);

    // 당일 운동 유형 계산
    Map<String, Integer> calculateExerciseTypeDistribution(int userId, LocalDate date);
}

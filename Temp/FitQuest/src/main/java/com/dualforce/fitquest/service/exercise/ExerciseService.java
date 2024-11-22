package com.dualforce.fitquest.service.exercise;

import com.dualforce.fitquest.model.dto.ExerciseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExerciseService {
    /**
     * 운동 기록을 등록합니다.
     *
     * @param exercise 등록할 운동 기록
     * @return 등록된 운동 기록의 ID
     */
    int addExercise(ExerciseDto exercise);

    /**
     * 특정 운동 기록을 조회합니다.
     *
     * @param exerciseId 조회할 운동 기록의 ID
     * @return 해당 ID의 운동 기록
     */
    ExerciseDto getExerciseById(int exerciseId);

    /**
     * 특정 사용자의 운동 기록을 모두 조회합니다.
     *
     * @param userId 사용자의 ID
     * @return 사용자의 모든 운동 기록 목록
     */
    List<ExerciseDto> getExercisesByUser(int userId);

    /**
     * 특정 사용자의 운동 기록 중 지정된 운동 유형에 해당하는 기록을 조회합니다.
     *
     * @param userId 사용자의 ID
     * @param category 운동 유형 (근력, 유산소, 코어)
     * @return 해당 운동 유형에 해당하는 운동 기록 목록
     */
    List<ExerciseDto> getExercisesByCategory(int userId, String category);

    /**
     * 특정 날짜 범위 내에서 특정 사용자의 운동 기록을 조회합니다.
     *
     * @param userId 사용자의 ID
     * @param startDate 조회할 시작 날짜
     * @param endDate 조회할 종료 날짜
     * @return 날짜 범위 내 운동 기록 목록
     */
    List<ExerciseDto> getExercisesByDateRange(int userId, LocalDate startDate, LocalDate endDate);

    /**
     * 운동 기록을 수정합니다.
     *
     * @param exercise 수정할 운동 기록
     * @return 수정된 레코드 수 (성공 시 1)
     */
    int updateExercise(ExerciseDto exercise);

    /**
     * 운동 기록을 삭제합니다.
     *
     * @param exerciseId 삭제할 운동 기록의 ID
     * @return 삭제된 레코드 수 (성공 시 1)
     */
    int deleteExercise(int exerciseId);

    /**
     * 연속 운동 일수를 계산합니다.
     *
     * @param userId 사용자의 ID
     * @return 연속적으로 운동한 일수
     */
    int getStreakDays(int userId);

    /**
     * 깃허브 잔디 스타일의 시각화 데이터를 생성합니다.
     *
     * @param userId 사용자의 ID
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @return 날짜별 운동 기록 여부를 나타내는 데이터 (날짜와 boolean 값의 Map)
     */
    Map<LocalDate, Boolean> getExerciseCalendar(int userId, LocalDate startDate, LocalDate endDate);

    /**
     * 운동 유형별 비율 분석 데이터를 생성합니다.
     *
     * @param userId 사용자의 ID
     * @return 운동 유형별 비율 데이터 (운동 유형과 해당 기록 개수의 Map)
     */
    Map<String, Long> getExerciseTypeDistribution(int userId);
}

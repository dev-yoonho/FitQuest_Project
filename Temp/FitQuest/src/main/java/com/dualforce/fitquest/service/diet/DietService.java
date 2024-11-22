package com.dualforce.fitquest.service.diet;

import com.dualforce.fitquest.model.dto.DietDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DietService {
    // 식단 기록 등록
    int createDiet(DietDto diet);

    // 당일 식단 기록 조회
    List<DietDto> readTodayMyDiets(int userId);

    // 당일 식단 기록 수정
    int editDiet(DietDto diet);

    // 당일 식단 기록 삭제
    int removeDiet(int dietId);

    // 식사 시간 유형 계산
    Map<String, Integer> calculateDietTypeDistribution(int userId, LocalDate date);
}

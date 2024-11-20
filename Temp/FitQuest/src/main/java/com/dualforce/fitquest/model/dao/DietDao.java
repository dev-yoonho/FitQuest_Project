package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.DietDto;

import java.util.List;

public interface DietDao {
    // 식단 기록 생성
    int insertDiet(DietDto diet);

    // 식단 기록 조회(식단 ID)
    DietDto selectDietById(int dietId);

    // 식단 기록 조회(사용자 ID)
    List<DietDto> selectDietByUserId(int userId);

    // 식단 기록 조회(음식 이름)
    List<DietDto> selectDietByFoodName(String foodName);

    // 식단 기록 조회(사용자 ID + 날짜 범위)
    List<DietDto> selectDietByUserIdDateRange(int userId, String startDate, String endDate);

    // 전체 식단 기록 조회(관리자 전용)
    List<DietDto> selectAllDiet();

    // 식단 기록 수정
    int updateDiet(DietDto diet);

    // 식단 기록 삭제
    int deleteDiet(int dietId);

    // 기능에 따른 추가 필요
}

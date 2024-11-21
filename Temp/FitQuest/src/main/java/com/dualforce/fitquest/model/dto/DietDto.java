package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietDto {
    // 식단 ID
    private Integer dietId;
    // 사용자 ID
    @NonNull
    private Integer userId;
    // 음식 이름
    @NonNull
    private String foodName;
    // 칼로리
    @NonNull
    private Integer calories;
    // 식사 유형
    @NonNull
    private String mealType;
    // 식단 등록 날짜
    private LocalDateTime createdAt;
}

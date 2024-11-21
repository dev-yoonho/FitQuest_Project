package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDto {
    // 운동 ID
    private Integer exerciseId;
    // 사용자 ID
    @NonNull
    private Integer userId;
    // 운동 이름
    @NonNull
    private String exerciseName;
    // 운동 유형
    @NonNull
    private String exerciseType;
    // 세트 수
    private Integer exerciseSet;
    // 반복 횟수
    private Integer repetitions;
    // 무게
    private Float weight;
    // 거리
    private Float distance;
    // 시간
    private Integer time;
    // 속도
    private Float speed;
    // 실내외 여부
    private String indoorOutdoor;
    // 운동 등록 날짜
    private LocalDateTime createdAt;
}

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
    // 세트 수(근력 운동, 코어 운동)
    private Integer exerciseSet;
    // 반복 횟수(근력 운동, 코어 운동)
    private Integer repetitions;
    // 무게(근력 운동)
    private Float weight;
    // 거리(유산소 운동)
    private Float distance;
    // 시간(유산소 운동, 코어 운동)
    private Integer time;
    // 속도(유산소 운동)
    private Float speed;
    // 실내외 여부(근력, 유산소, 코어)
    private String indoorOutdoor;
    // 운동 등록 날짜
    private LocalDateTime createdAt;
}

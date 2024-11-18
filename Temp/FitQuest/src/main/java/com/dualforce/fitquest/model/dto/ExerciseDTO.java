package com.dualforce.fitquest.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ExerciseDTO {
    private Long exerciseId;
    @NonNull
    private String exerciseName;
    private String exerciseDescription;
    @NonNull
    private String exerciseType; // 근력, 유산소, 코어
    private Integer sets;
    private Integer repetitions;
    private Float weight;
    private Float distance;
    private Integer time;
    private Float speed;
    private String indoorOutdoor; // 실내, 실외
}

package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDto {
    private Integer exerciseId;
    @NonNull
    private Integer userId;
    @NonNull
    private String exerciseName;
    private String exerciseDescription;
    @NonNull
    private String exerciseType;
    private Integer exerciseSet;
    private Integer repetitions;
    private Float weight;
    private Float distance;
    private Integer time;
    private Float speed;
    private String indoorOutdoor;
    private LocalDateTime createdAt;
}

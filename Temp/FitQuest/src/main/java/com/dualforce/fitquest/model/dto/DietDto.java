package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietDto {
    private Integer dietId;
    @NonNull
    private Integer userId;
    @NonNull
    private String foodName;
    @NonNull
    private Integer calories;
    @NonNull
    private String mealType;
    private LocalDateTime createdAt;
}

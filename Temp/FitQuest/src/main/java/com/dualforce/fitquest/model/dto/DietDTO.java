package com.dualforce.fitquest.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DietDTO {
    private Long dietId;
    @NonNull
    private Long foodId;
    @NonNull
    private Long userId;
    @NonNull
    private String mealType; // 아침, 점심, 저녁
}
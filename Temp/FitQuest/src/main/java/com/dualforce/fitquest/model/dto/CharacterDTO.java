package com.dualforce.fitquest.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CharacterDTO {
    private Long characterId;
    @NonNull
    private Long userId;
    @NonNull
    private String characterJob;
    @NonNull
    private String characterGender; // 남성, 여성
    private Integer strength;
    private Integer agility;
    private Integer toughness;
    private Integer stamina;
    private Integer luck;
}
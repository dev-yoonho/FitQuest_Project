package com.dualforce.fitquest.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterInfoDto {
    @NonNull
    private Integer userId;
    @NonNull
    private String characterJob;
    @NonNull
    private String characterGender;
    private Integer strength;
    private Integer agility;
    private Integer toughness;
    private Integer stamina;
    private Integer luck;
    private Integer patk;
    private Integer def;
    private Integer cridam;
    private Integer matk;
    private Integer mres;
    private Integer hprecov;
}

package com.dualforce.fitquest.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonsterDto {
    @NonNull
    private String monsterName;
    @NonNull
    private String monsterDesc;
    @NonNull
    private String imgPath;
    private Integer stamina;
    private Integer patk;
    private Integer def;
    private Integer matk;
    private Integer mres;
    private Integer hprecov;
}

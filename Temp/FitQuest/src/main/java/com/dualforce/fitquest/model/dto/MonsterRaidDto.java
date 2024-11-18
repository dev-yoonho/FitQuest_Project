package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonsterRaidDto {
    private Integer raidId;
    @NonNull
    private Integer partyId;
    @NonNull
    private String monsterName;
    @NonNull
    private Integer stamina;
    private LocalDateTime raidedAt;
}

package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonsterRaidStoryDto {
    private Integer storyId;
    @NonNull
    private Integer raidId;
    private Integer userId;
    @NonNull
    private String battleDesc;
    @NonNull
    private Integer sequenceOrder;
    private LocalDateTime createdAt;
}

package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyMemberDto {
    @NonNull
    private Integer userId;
    @NonNull
    private Integer partyId;
    private LocalDateTime joinedAt;
}

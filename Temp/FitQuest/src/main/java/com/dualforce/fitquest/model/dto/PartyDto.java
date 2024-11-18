package com.dualforce.fitquest.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyDto {
    private Integer partyId;
    @NonNull
    private String partyName;
    private LocalDateTime createdAt;
}

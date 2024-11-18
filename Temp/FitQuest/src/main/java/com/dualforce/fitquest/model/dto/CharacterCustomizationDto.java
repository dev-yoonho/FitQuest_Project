package com.dualforce.fitquest.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterCustomizationDto {
    private Integer customizationId;
    @NonNull
    private Integer userId;
    private String customizationOptions;
    private Integer illustrationId;
    private Integer voiceId;
}

package com.dualforce.fitquest.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoiceDto {
    private Integer voiceId;
    @NonNull
    private String filePath;
}

package com.dualforce.fitquest.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IllustrationDto {
    private Integer illustrationId;
    @NonNull
    private String filePath;
}

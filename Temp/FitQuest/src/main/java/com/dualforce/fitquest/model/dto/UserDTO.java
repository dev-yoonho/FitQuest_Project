package com.dualforce.fitquest.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    private Long userId;
    @NonNull
    private String name;
    private String address;
    @NonNull
    private String email;
    private String phoneNumber;
    private Boolean isAdmin;
    @NonNull
    private String nickname;
    private Long illustrationId;
}

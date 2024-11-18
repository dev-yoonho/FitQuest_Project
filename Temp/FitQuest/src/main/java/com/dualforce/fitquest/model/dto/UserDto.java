package com.dualforce.fitquest.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer userId;
    @NonNull
    private String name;
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String address;
    @NonNull
    private String email;
    private String phoneNumber;
    @NonNull
    private String nickname;
    private boolean isAdmin;
    @NonNull
    private Float height;
    @NonNull
    private Float weight;
    @NonNull
    private Integer age;
}

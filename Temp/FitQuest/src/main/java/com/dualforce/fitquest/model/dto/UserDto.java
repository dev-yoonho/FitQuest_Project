package com.dualforce.fitquest.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    // 사용자 ID
    private Integer userId;
    // 이메일
    @NonNull
    private String email;
    // 비밀번호
    @NonNull
    private String password;
    // 이름
    @NonNull
    private String name;
    // 전화번호
    private String phoneNumber;
    // 닉네임
    @NonNull
    private String nickname;
    // 관리자 여부
    private boolean isAdmin;
    // 키
    @NonNull
    private Float height;
    // 체중
    @NonNull
    private Float weight;
    // 나이
    @NonNull
    private Integer age;
}

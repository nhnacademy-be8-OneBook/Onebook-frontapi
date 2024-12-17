package com.onebook.frontapi.auth.dto;

import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberResponseDto {

    Integer gradeId; // 1: REGULAR, 2: ROYAL, 3: GOLD, 4: PLATINUM
    Integer roleId;// 1: MEMBER, 2: ADMIN
    String name;
    String loginId;
    LocalDate dateOfBirth;
    String gender;
    String email;
    String phoneNumber;
    String status;
    LocalDateTime createdAt;
    LocalDateTime lastLoginAt;

}
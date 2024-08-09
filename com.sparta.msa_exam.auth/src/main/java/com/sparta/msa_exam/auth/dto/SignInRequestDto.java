package com.sparta.msa_exam.auth.dto;

import com.sparta.msa_exam.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {
    private String userId;
    private String password;

}

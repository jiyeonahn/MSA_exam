package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.SignInRequestDto;
import com.sparta.msa_exam.auth.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.dto.SignUpResponseDto;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto requestDto){
        return userService.signUp(requestDto);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto requestDto){
        String token = userService.signIn(requestDto);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/signIn")
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String user_id){
        return ResponseEntity.ok(new AuthResponse(userService.createAccessToken(user_id, "MEMBER")));
    }

    /**
     * JWT 액세스 토큰을 포함하는 응답 객체입니다.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class AuthResponse {
        private String access_token;

    }

    @GetMapping("{userId}")
    public boolean checkUser(@PathVariable("userId") String userId){
        return userService.checkUser(userId);
    }
}

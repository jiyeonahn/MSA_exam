package com.sparta.msa_exam.gateway;

import org.springframework.web.bind.annotation.PathVariable;

// 응용 계층 DIP 적용을 위한 인증 서비스 인터페이스
public interface AuthService {
    boolean checkUser(String userId);
}

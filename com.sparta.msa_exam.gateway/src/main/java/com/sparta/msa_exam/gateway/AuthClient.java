package com.sparta.msa_exam.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthClient extends AuthService {
    @GetMapping("/auth/{userId}")
    boolean checkUser(@PathVariable("userId") String userId);
}

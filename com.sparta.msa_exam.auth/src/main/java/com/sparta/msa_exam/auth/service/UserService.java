package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.dto.SignInRequestDto;
import com.sparta.msa_exam.auth.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.dto.SignUpResponseDto;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.management.openmbean.OpenDataException;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * AuthService 생성자.
     * Base64 URL 인코딩된 비밀 키를 디코딩하여 HMAC-SHA 알고리즘에 적합한 SecretKey 객체를 생성합니다.
     *
     * @param secretKey Base64 URL 인코딩된 비밀 키
     */
    public UserService(@Value("${service.jwt.secret-key}") String secretKey,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * 사용자 ID를 받아 JWT 액세스 토큰을 생성합니다.
     *
     * @param user_id 사용자 ID
     * @return 생성된 JWT 액세스 토큰
     */
    public String createAccessToken(String user_id, String role) {
        return Jwts.builder()
                // 사용자 ID를 클레임으로 설정
                .claim("user_id",user_id)
                // JWT 발행자를 설정
                .issuer(issuer)
                // JWT 발행 시간을 현재 시간으로 설정
                .issuedAt(new Date(System.currentTimeMillis()))
                // JWT 만료 시간을 설정
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                // SecretKey를 사용하여 HMAC-SHA512 알고리즘으로 서명
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                // JWT 문자열로 컴팩트하게 변환
                .compact();
    }

    public SignUpResponseDto signUp(SignUpRequestDto requestDto){
        Optional<User> findUser = userRepository.findById(requestDto.getUserId());
        if(findUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"중복된 사용자가 존재합니다.");
        }
        User user = SignUpRequestDto.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return SignUpResponseDto.fromEntity(userRepository.save(user));
    }


    public String signIn(SignInRequestDto requestDto){
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "유효하지 않은 ID입니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 틀렸습니다.");
        }

        return createAccessToken(user.getUserId(), user.getRole());
    }

    public boolean checkUser(String userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "유효하지 않은 ID입니다."));
        return true;
    }
}
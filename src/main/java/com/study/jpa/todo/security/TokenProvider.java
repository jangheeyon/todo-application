package com.study.jpa.todo.security;

import com.study.jpa.todo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //JWT 라이브러리를 이용해 JWT 토큰을 생성
    //토큰을 생성하는 과정에서 임의로 지정한 시크릿 키를 개인 키로 사용
    public String create(UserEntity userEntity) {
        //기한은 오늘날짜 + 1일
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );
        //JWT 토큰 생성
        return Jwts.builder()
                .signWith(SECRET_KEY)
                .setSubject(userEntity.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    //토큰을 디코딩, 파싱, 위조 여부를 확인
    //유저의 아이디 리턴
    public String validateAndGetUserId(String token) {
        //시크릿 키로 서명 후 token의 서명과 비교
        //위조 되지 않았다면 페이로드(claims) 리턴, 위조라면 예외
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}

package com.sso.utils.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = generateSecretKey(); // 시크릿 키
    private static final int SECRET_KEY_LENGTH = 32; // 256비트, 32바이트
    private static final long EXPIRATION_TIME = 600 * 1000; // 토큰 만료 시간 (1시간)

    // JWT 생성
    public static String generateJwtToken(String name, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "1234");
        claims.put("name", name);
        claims.put("email", email);
        claims.put("iss", "yulchon_server");
        if(name.equals("전민수")){
            claims.put("roles", "admin");
        }else{
            claims.put("roles", "user");
        }


        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // JWT 검증
    public static boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //
    public static Claims getJwtTokenInfo(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    // JWT에서 사용자명 추출
    public static String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 무작위 문자열로 SECRET_KEY 생성
    private static String generateSecretKey() {
        byte[] randomBytes = new byte[SECRET_KEY_LENGTH];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}

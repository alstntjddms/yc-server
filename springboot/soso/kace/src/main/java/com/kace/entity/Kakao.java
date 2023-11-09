package com.kace.entity;

import com.kace.dto.KakaoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kakao")
public class Kakao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // 카카오 아이디
    private String kakaoId;
    // 카카오 허용 토큰
    private String kakaoAccessToken;
    // 카카오 새로고침 토큰
    private String kakaoRefreshToken;
    // 카카오 이메일
    private String kakaoEmail;
    // 카카오 닉네임
    private String kakaoNickName;
    // 카카오 성별
    private String kakaoGender;
    // 카카오 생일
    private String kakaoBirthday;
    // 카카오 회원가입일
    private Timestamp kakaoRegisterDate;
    // 카카오 최종로그인날짜
    private Timestamp kakaoLoginDate;
    // 카카오 메세지 동의
    private boolean kakaoMsgYn;
    // 동의항목 눌름 여부
    private boolean kakaoScopeCheck;

    public static Kakao toEntity(KakaoDTO kakaoDTO) {
        return Kakao.builder()
                .id(kakaoDTO.getId())
                .kakaoId(kakaoDTO.getKakaoId())
                .kakaoAccessToken(kakaoDTO.getKakaoAccessToken())
                .kakaoRefreshToken(kakaoDTO.getKakaoRefreshToken())
                .kakaoEmail(kakaoDTO.getKakaoEmail())
                .kakaoNickName(kakaoDTO.getKakaoNickName())
                .kakaoGender(kakaoDTO.getKakaoGender())
                .kakaoBirthday(kakaoDTO.getKakaoBirthday())
                .kakaoRegisterDate(kakaoDTO.getKakaoRegisterDate())
                .kakaoLoginDate(kakaoDTO.getKakaoLoginDate())
                .kakaoMsgYn(kakaoDTO.isKakaoMsgYn())
                .kakaoScopeCheck(kakaoDTO.isKakaoScopeCheck())
                .build();
    }
}
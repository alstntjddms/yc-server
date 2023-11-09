package com.kace.dto;

import com.kace.entity.Kakao;
import lombok.*;
import java.sql.Timestamp;

@Data
@Builder
public class KakaoDTO {
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

    public static KakaoDTO toDTO(Kakao kakao) {
        return KakaoDTO.builder()
                .id(kakao.getId())
                .kakaoId(kakao.getKakaoId())
                .kakaoAccessToken(kakao.getKakaoAccessToken())
                .kakaoRefreshToken(kakao.getKakaoRefreshToken())
                .kakaoEmail(kakao.getKakaoEmail())
                .kakaoNickName(kakao.getKakaoNickName())
                .kakaoGender(kakao.getKakaoGender())
                .kakaoBirthday(kakao.getKakaoBirthday())
                .kakaoRegisterDate(kakao.getKakaoRegisterDate())
                .kakaoLoginDate(kakao.getKakaoLoginDate())
                .kakaoMsgYn(kakao.isKakaoMsgYn())
                .kakaoScopeCheck(kakao.isKakaoScopeCheck())
                .build();
    }
}

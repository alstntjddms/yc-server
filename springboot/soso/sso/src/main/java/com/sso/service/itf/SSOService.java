package com.sso.service.itf;


public interface SSOService {

    /**
     * OIDC를 클라이언트에 반납한다.
     */
    public String create(String idToken);

    /**
     * OIDC IDToken을 검증한다.
     * @return Boolean
     */
    public Boolean validateIDToken(String idToken);
    /**
     * 발급한 JWT토큰을 검증한다.
     * @param jwtToken
     * @return
     */
    public Boolean checkJWT(String jwtToken) throws Exception;
}

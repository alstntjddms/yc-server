package com.sso.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sso.service.itf.SSOService;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Service
public class SSOServiceImpl implements SSOService {

    @Override
    public String create(String idToken) {
        if(validateIDToken(idToken)){
            return idToken;
        }else{
            return null;
        }
    }

    @Override
    public Boolean validateIDToken(String idToken) {
        System.out.println("idToken = " + idToken);
        String tenantId = "6369db95-476d-4bbe-8b2b-f6f3926531fb"; // Replace with your Azure AD tenant ID

        try {
            URL jwksUrl = new URL("https://login.microsoftonline.com/" + tenantId + "/discovery/keys");
            HttpURLConnection connection = (HttpURLConnection) jwksUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String jwksResponse = in.readLine();
            in.close();

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(jwksResponse);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray keys = jsonObject.getAsJsonArray("keys");

            // Find the key that matches the token's "kid" value
            JsonObject selectedKey = null;
            for (JsonElement keyElement : keys) {
                JsonObject key = keyElement.getAsJsonObject();
                String kid = key.get("kid").getAsString();
                if (kid.equals(JWT.decode(idToken).getKeyId())) {
                    selectedKey = key;
                    break;
                }
            }

            if (selectedKey != null) {
                String x5c = selectedKey.getAsJsonArray("x5c").get(0).getAsString();

                byte[] certBytes = Base64.getDecoder().decode(x5c);
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certBytes));

                RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey();

                Algorithm algorithm = Algorithm.RSA256(publicKey, null);
                DecodedJWT jwt = JWT.require(algorithm).build().verify(idToken);

                System.out.println("ID token 인증 성공");
                System.out.println("Subject: " + jwt.getSubject());
                System.out.println("Issuer: " + jwt.getIssuer());
                System.out.println("Expiration: " + jwt.getExpiresAt());
//                return "id_token인증됨";
                return true;
            } else {
//                return "매칭되는 공개키 못찾음";
                return false;
            }
        } catch (Exception e) {
            System.out.println("ID token 인증 실패");
//            return "id_token이 이상함";
            return false;
        }
    }


    @Override
    public Boolean checkJWT(String jwtToken) throws Exception {
        if (!validateIDToken(jwtToken)) {
            throw new Exception("유효하지 않은 토큰");
        } else {
            System.out.println("통과");
            return true;
        }
    }
}

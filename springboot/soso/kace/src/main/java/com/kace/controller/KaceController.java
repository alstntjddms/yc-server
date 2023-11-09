package com.kace.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.common.Common;
import com.common.hash.SHA256;
import com.common.log.Log;
import com.common.log.LogFactory;
import com.common.aes.AES256;
import com.common.restapi.RestAPI;
import com.google.firebase.messaging.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kace.dto.*;
import com.kace.service.itf.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.microsoft.aad.msal4j.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = {"http://localhost:5500"})
public class KaceController {
    private final Log log = LogFactory.getInstance().createLog(KaceController.class);
    @Autowired
    Common common;
    @Autowired
    RestAPI restAPI;
    @Autowired
    AES256 aes256;
    @Autowired
    SHA256 sha256;
    @Autowired
    KakaoService kakaoService;
    @GetMapping("/test")
    public ResponseEntity<?> test() throws Exception {
        System.out.println("KaceController.test");
//        try {
            log.info("kakao", "Start");
            return new ResponseEntity<>(aes256.encrypt(common.com()), HttpStatus.OK);
//        } catch (Exception e) {
//            log.warn("kakao", e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
    }


    public String getAccessToken() throws MalformedURLException, ExecutionException, InterruptedException {
        IClientCredential credential = ClientCredentialFactory.createFromSecret("oLc8Q~qtX-ZX6my.Gd7z3RM3bMhQQ91J5EaPobyu");
        ConfidentialClientApplication application = ConfidentialClientApplication.builder("15203123-534e-4f12-b466-f92c5ba7fdf1", credential)
                .authority("https://login.microsoftonline.com/6369db95-476d-4bbe-8b2b-f6f3926531fb")
                .build();

        ClientCredentialParameters parameters = ClientCredentialParameters.builder(Collections.singleton("https://graph.microsoft.com/.default"))
                .build();

        CompletableFuture<IAuthenticationResult> future = application.acquireToken(parameters);
        IAuthenticationResult tokenResult = future.join();

        IAuthenticationResult result = future.get();

        java.net.http.HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://graph.microsoft.com/v1.0/applications/" + "81ec712d-7970-4663-85ad-26798417e992"))
                .header("Authorization", "Bearer " + tokenResult.accessToken())
                .GET()
                .build();

        return tokenResult.accessToken();
    }

    @GetMapping("/kakao")
    public ResponseEntity kakao() throws Exception {
        try {
            log.info("kakao", "Start");
            return new ResponseEntity(aes256.encrypt(common.com()), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("kakao", e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/kakao1")
    public ResponseEntity kakao1() throws Exception {
        System.out.println("KakaoController.kakao1");
        // 헤더 전달 X
        restAPI.get("http://localhost:8080/api/log/log");
        return new ResponseEntity(aes256.encrypt(common.com()), HttpStatus.OK);
    }

//    @GetMapping("/kakao1")
//    public ResponseEntity kakao1() {
//        log.info("kakao1");
//        return new ResponseEntity(restAPI.get("http://localhost:8082/api/login"), HttpStatus.OK);
//    }

    @PostMapping("/kakao2")
    public ResponseEntity kakao2(@RequestBody HashMap<String, String> test) {
//        log.info("kakao2");
        return new ResponseEntity(restAPI.post("http://localhost:8082/api/login1", test, HashMap.class), HttpStatus.OK);
    }

    @GetMapping("/kakao3")
    public ResponseEntity kakao3() {
        HashMap<String, String> test = new HashMap<>();
        test.put("aa", "aa");
        List<HashMap<String, String>> list = new ArrayList<>();
        list.add(test);
        list.add(test);
        list.add(test);
        return new ResponseEntity(restAPI.post("http://localhost:8082/api/login2", list, List.class), HttpStatus.OK);
    }

    @GetMapping("/kakaoall")
    public List<MenuDTO> kakaoAll() {
        return kakaoService.testAll();
    }

//    @GetMapping("/kakaoalljpa")
//    public List<ResponseMenuItems> kakaoAllJPA() {
//        return kakaoService.testAllJPA();
//    }

    //    테스트 시작
    @GetMapping("/menu/{userCode}")
    public List<ResponseMenuItemsDTO> settingMenu(@PathVariable("userCode") Integer userCode) {
        System.out.println("userCode = " + userCode);
        return kakaoService.testAllJPA(userCode);
    }

    @PostMapping("/menu")
    public void addMenu(@RequestBody MenuDTO menuDTO) {
        System.out.println("menuDTO = " + menuDTO);
        kakaoService.addMenu(menuDTO);
    }

    @PutMapping("/menu")
    public void updateMenu(@RequestBody MenuDTO menuDTO) {
        System.out.println("menuDTO = " + menuDTO);
        kakaoService.updateMenu(menuDTO);
    }

    @DeleteMapping("/menu")
    public void deleteMenu(@RequestParam Integer menuCode) {
        System.out.println("menuCode = " + menuCode);
        kakaoService.deleteMenu(menuCode);
    }

    @GetMapping("/menus")
    @Tag(name="모든 메뉴 조회")
    @Operation(description = "모든 메뉴를 조회한다.")
    public List<MenuDTO> findMenus() {
        return kakaoService.findMenus();
    }

    @GetMapping("/users")
    public List<UserDTO> findAllUsers() {
        return kakaoService.findAllUsers();
    }

    @GetMapping("/menu/auth/{userCode}")
    public List<MenuAuthDTO> selectUserMenuAuth(@PathVariable("userCode") Integer userCode) {
        return kakaoService.selectUserMenuAuth(userCode);
    }

    @PutMapping("/menu/auth")
    public void updateMenuAuth(@RequestBody MenuAuthDTO menuAuthDTO) {
        System.out.println("menuDTO = " + menuAuthDTO);
        kakaoService.updateMenuAuth(menuAuthDTO);
    }
//    테스트 끝
    
    
    @GetMapping("/mail")
    public String sendMail(){
        return kakaoService.sendMail();
    }

    @GetMapping("/testSP")
    public String testSP(){
        return kakaoService.testSP();
    }


    @PostMapping("/validateToken")
    public String validateIDToken(@RequestBody String idToken) throws Exception {
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
                return "id_token인증됨";
            } else {
                return "매칭되는 공개키 못찾음";
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("ID token 인증 실패");
            return "id_token이 이상함";
        }
    }

    @PostMapping("/notification")
    public String notificationTest(@RequestBody String token){
        try{
            testNoti(token);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("e = " + e);
        }
        return "알림전송..";
    }

    public void testNoti(String token) throws FirebaseMessagingException {
        try {
            System.out.println("알림 전송!");
            // 7초(7000 밀리초) 동안 딜레이
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            // 쓰레드 예외
            System.out.println("쓰레드 예외 e = " + e);
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // 날짜 및 시간 형식 지정

//        Message message = Message.builder()
//                .setToken(token)
//                .setNotification(
//                        Notification.builder()
//                                .setTitle("알림 테스트...").setBody("전송시간[" + now.format(formatter)+"]")
//                  .build()
//                )
////                .setWebpushConfig(WebpushConfig.builder().putData("link", "https://sso1.yulchon.com/pages/test5").build())
//
//                .build();

        Message message = Message.builder()
                .setToken(token)
                .setWebpushConfig(WebpushConfig.builder()
                    .setNotification(
                        WebpushNotification.builder()
                            .setTitle("알림 테스트...").setBody("전송시간[" + now.format(formatter)+"]")
                            .build()
                    )
                    .setFcmOptions(WebpushFcmOptions.withLink("https://sso1.yulchon.com/pages/test5"))
                    .build())
                .build();

        // 알림 보내기
        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("response = " + response);
    }


}
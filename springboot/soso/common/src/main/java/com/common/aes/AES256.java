package com.common.aes;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AES256 {

    public String alg = "AES/CBC/PKCS5Padding";
    private String key = "";
    private String iv = ""; // 16byte

    public AES256() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new FileReader("C:\\key\\AES256.txt"));
        key = bufferReader.readLine();
        iv = key.substring(0, 16);
        bufferReader.close();
    }

    public String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public String encryptEncodeReplace(String text) throws Exception {
        return URLEncoder.encode(encrypt(text), StandardCharsets.UTF_8).replaceAll("%", "MSJSM");
    }

    public String replaceDecodeDecryt(String text) throws Exception {
        return decrypt(URLDecoder.decode(text.replaceAll("MSJSM", "%"), StandardCharsets.UTF_8));
    }

}
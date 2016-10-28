package com.warp.android;

import android.util.Base64;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class WarpSessionKey {

    private final String KEY = "ZOKBK1IRZ0tMHPgg";
    private final byte[] IV_BYTES = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    private int userId = -1;
    private String sessionToken = null;
    private String userName = null;
    private String password = null;

    public static WarpSessionKey add(int userId, String sessionToken, String username, String password) {
        return new WarpSessionKey(userId, sessionToken, username, password);
    }

    public WarpSessionKey(int userId, String sessionToken, String userName, String password) {
        this.userId = userId;
        this.sessionToken = sessionToken;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPasswordEnc() { return enc(password); }
    public String getPasswordDec() { return dec(password); }

    public void setPassword(String password) { this.password = password; }

    public String enc(String text) {
        try {
            byte[] keyBytes = KEY.getBytes("UTF-8");

            AlgorithmParameterSpec ivSpec = new IvParameterSpec(IV_BYTES);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return Base64.encodeToString(cipher.doFinal(text.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String dec(String text) {
        try {
            byte[] keyBytes = KEY.getBytes("UTF-8");

            AlgorithmParameterSpec ivSpec = new IvParameterSpec(IV_BYTES);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            byte[] decode = Base64.decode(text.getBytes("UTF-8"), Base64.DEFAULT);
            return new String(cipher.doFinal(decode), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "WarpSessionKey{" +
                "userId=" + userId +
                ", sessionToken='" + sessionToken + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

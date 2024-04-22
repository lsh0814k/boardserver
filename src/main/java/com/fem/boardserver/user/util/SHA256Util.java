package com.fem.boardserver.user.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class SHA256Util {
    public static final String ENCRYPTION_KEY = "SHA-256";
    public static String encrypt(String str) {
        String sha = null;
        try {
            MessageDigest sh = MessageDigest.getInstance(ENCRYPTION_KEY);
            sh.update(str.getBytes());
            byte[] byteData = sh.digest();
            StringBuffer sb = new StringBuffer();
            for (byte byteDatum: byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }

            sha = sb.toString();
        } catch (Exception e) {
            log.error("encryptSHA256 ERROR : ", e);
        }

        return sha;
    }
}

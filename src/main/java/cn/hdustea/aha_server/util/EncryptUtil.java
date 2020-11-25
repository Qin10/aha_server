package cn.hdustea.aha_server.util;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密工具类
 *
 * @author STEA_YY
 **/
public class EncryptUtil {
    public static String getSHA256(String sourceStr) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        messageDigest.update(sourceStr.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(messageDigest.digest());

    }
}
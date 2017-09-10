package es.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 生成md5
 * Created by cao on 14/10/20.
 */
public class Md5Util {
    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public static String strToMD5(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("获取MD5出错", e);
            return null;
        }
        byte[] messageDigest = md.digest(str.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        return hashtext;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(strToMD5("123456"));
    }
}

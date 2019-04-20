package com.tradingPlatform.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密工具类
 */
public class MD5Util {

    /**
     * md5加密
     */
    public static String md5(String text) {

        // 加密后的结果
        String md5code = "";

        // 密码字节数组
        byte[] secretBytes = null;
        try {
            // MessageDigest：jdk提供给我们的摘要算法工具类
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 计算哈希值
            secretBytes = md5.digest(text.getBytes());

            // 转换成16进制的数字
            md5code = new BigInteger(1, secretBytes).toString(16);

            // 如果生成数字未满32位，需要前面补0
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code = "0" + md5code;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        return md5code;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        // 原始密码
        String password = "admin";
        // 加密
        String md5Password = md5(password);

        System.out.println("原始密码：" + password);
        System.out.println("md5加密后的密码：" + md5Password);
    }
}

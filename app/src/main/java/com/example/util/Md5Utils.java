package com.example.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

    /**
     * md5加密方法
     * @param password
     * @return
     */
    public static String md5Password(String password) {
        try {
            //得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            //把每一个byte做一个与运算0xff
            for (byte b : result) {
                //与运算
                int number = b & 0xff; //加盐：int number = b & 0xfff;
                String str = Integer.toHexString(number);
                System.out.println(str);
                if(str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            //标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}
package com.edianjucai.util;

import java.security.MessageDigest;
import java.util.UUID;

public class MD5Util {

    /***
     * MD5加码 生成32位md5码
     */
    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }
    
    //生成10位不重复的序列号
    public static String MD5Bit12(String inStr) {
        return MD5(inStr).substring(4, 24);
    }
    
    //生成8位数字密码
    public static String[] chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static String generatePassword() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x0a]);
        }
        return shortBuffer.toString();

    }
}

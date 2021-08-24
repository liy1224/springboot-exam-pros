package com.exam.starter.properties.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * uuid生成工具
 *
 * @author author
 * @date 2019/7/26 15:21
 */
public class UUIDUtil {

    private static Random rand;

    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取id
     *
     * @return 32位uuid
     */
    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }


    /**
     * 获取注册码
     * @return
     */
    public static String getRegistrationRandomNum(){
        int  maxNum = 36;
        int len = 8;
        int i;
        int count = 0;
        char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuffer pwd = new StringBuffer();

        while(count < len){
            i = Math.abs(rand.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count ++;
            }
        }
        return pwd.toString();
    }

}

package com.exam.starter.util;

/**
 * 字符串相关工具类
 * @author liyang
 */
public class StringUtils {
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否非空
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 转换为String类型
     * @param obj
     * @return
     */
    public static String toString(Object obj){
        if(obj == null){
            return "";
        }else{
            return obj.toString();
        }
    }

    /**
     * 左补齐
     * @param s
     * @param n
     * @param replace
     * @return
     */
    public static String lpad(String s, int n, String replace) {
        while (s.length() < n) {
            s = replace+s;
        }
        return s;
    }

    /**
     * 右补齐
     * @param s
     * @param n
     * @param replace
     * @return
     */
    public static String rpad(String s, int n, String replace) {
        while (s.length() < n) {
            s = s+replace;
        }
        return s;
    }
}

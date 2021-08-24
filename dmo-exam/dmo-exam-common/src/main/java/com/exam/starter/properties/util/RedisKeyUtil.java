package com.exam.starter.properties.util;

import cn.hutool.core.util.StrUtil;
import com.exam.starter.properties.constant.Constant;
import com.exam.starter.properties.excepiton.CommonException;
import com.exam.starter.properties.vo.RowVo;
import com.exam.starter.util.PageData;
import com.exam.starter.util.StringUtils;
import com.google.common.base.Strings;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户中心工具类
 */
public class RedisKeyUtil {


    /**
     * 老版身份证长度
     */
    private static final int IDCARD_OLD_LENGTH = 15;

    /**
     * 现行身份证长度
     */
    private static final int IDCARD_LENGTH = 18;

    /**
     * 设置行号
     *
     * @param pageData
     */
    public static void setRowNum(PageData<? extends RowVo> pageData) {
        if (pageData != null) {
            long start = pageData.getStartRow();
            for (RowVo rowVo : pageData.getData()) {
                rowVo.setRow(start++);
            }
        }
    }

    /**
     * 设置行号
     *
     * @param pageData
     */
    public static void setRowNum(List<? extends RowVo> pageData) {
        if (pageData != null) {
            long start = 1;
            for (RowVo rowVo : pageData) {
                rowVo.setRow(start++);
            }
        }
    }

    /**
     * 身份证敏感处理
     *
     * @param idCard
     * @return
     */
    public static String maskIdCard(String idCard) {
        if (!Strings.isNullOrEmpty(idCard)) {
            if (idCard.length() == IDCARD_OLD_LENGTH) {
                idCard = idCard.replaceAll("(\\w{4})\\w*(\\w{3})", "$1******$2");
            }
            if (idCard.length() == IDCARD_LENGTH) {
                idCard = idCard.replaceAll("(\\w{4})\\w*(\\w{3})", "$1*********$2");
            }
        }
        return idCard;
    }

    /**
     * passport脱敏
     *
     * @param passport
     * @return
     */
    public static String maskPassport(String passport) {
        if (StringUtils.isNotBlank(passport)) {
            char[] chars = passport.toCharArray();
            if (!StringUtils.isBlank(passport) && passport.length() > 0) {
                return passport.substring(0, 2) + "*******" + passport.substring(chars.length - 1);
            }
        }
        return passport;
    }

    /**
     * email脱敏
     *
     * @param email
     * @return
     */
    public static String maskEmail(String email) {
        int minLength = 2;
        if (StringUtils.isNotBlank(email)) {
            int idx = email.indexOf("@");
            if (idx > minLength) {
                String ret = email.substring(0, 1);
                for (int i = 0; i < idx - minLength; i++) {
                    ret += "*";
                }
                ret += email.substring(idx - 1);
                return ret;
            }
        }
        return email;
    }

    /**
     * telNum脱敏
     *
     * @param telNum
     * @return
     */
    public static String maskTelNum(String telNum) {
        int minLength = 8;
        if (StringUtils.isNotBlank(telNum)) {
            if (!StringUtils.isBlank(telNum) && telNum.length() > minLength) {
                return telNum.substring(0, 3) + "*****" + telNum.substring(8);
            }
        }
        return telNum;
    }

    /**
     * 生成最常见的redis key（cacheName::cacheKeyName:key)
     *
     * @param cacheName
     * @param cacheKeyName
     * @param key
     * @return
     */
    public static String getCacheKey(String cacheName, String cacheKeyName, String key) {
        return StrUtil.format("{}::{}:{}", cacheName, cacheKeyName, key);
    }

    /**
     * 生成最常见的hash类型的redis key（cacheName::cacheKeyName)
     *
     * @param cacheName
     * @param cacheKeyName
     * @return
     */
    public static String getCacheKey(String cacheName, String cacheKeyName) {
        return StrUtil.format("{}::{}", cacheName, cacheKeyName);
    }



    static Calendar c = Calendar.getInstance();

    /**
     * 通过身份证号码获取出生日期、性别、年龄
     *
     * @param idCard
     * @return 返回的出生日期格式：1990-01-01   性别格式：F-女，M-男
     */
    public static Map<String, String> analysisIdCard(String idCard) {
        int oldLength = 15;
        int newLength = 18;
        String birthday = "";
        String age = "";
        String sexCode = "";
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = idCard.toCharArray();
        boolean flag = true;
        if (number.length == IDCARD_OLD_LENGTH) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) {
                    throw new CommonException("身份证不合法");
                }
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == IDCARD_LENGTH) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) {
                    throw new CommonException("身份证不合法");
                }
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && idCard.length() == oldLength) {
            String year = "19" + idCard.substring(6, 8);
            String month = idCard.substring(8, 10);
            String day = idCard.substring(10, 12);
            verification(year, month, day);
            birthday = year + "-"
                    + month + "-"
                    + day;
            sexCode = Integer.parseInt(idCard.substring(idCard.length() - 3)) % 2 == 0 ? "F" : "M";
            age = (thisYear - Integer.parseInt("19" + idCard.substring(6, 8))) + "";
        } else if (flag && idCard.length() == newLength) {
            String year = idCard.substring(6, 10);
            String month = idCard.substring(10, 12);
            String day = idCard.substring(12, 14);
            verification(year, month, day);
            birthday = year + "-"
                    + month + "-"
                    + day;
            sexCode = Integer.parseInt(idCard.substring(idCard.length() - 4, idCard.length() - 1)) % 2 == 0 ? "F" : "M";
            age = (thisYear - Integer.parseInt(idCard.substring(6, 10))) + "";
        }
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("birthday", birthday);
        map.put("age", age);
        map.put("sexCode", sexCode);
        return map;
    }

    private static void verification(String year, String month, String day) {
        int minYear = 1900;
        int maxMonth = 12;
        try {
            if (Integer.valueOf(year) < minYear && Integer.valueOf(year) > c.get(Calendar.YEAR)) {
                throw new CommonException("年龄不合法");
            }
            if (Integer.valueOf(month) < 1 && Integer.valueOf(month) > maxMonth) {
                throw new CommonException("身份证不合法");
            }
            c.set(Integer.valueOf(year), Integer.valueOf(month), 0);
            if (Integer.valueOf(day) > c.get(Calendar.DAY_OF_MONTH)) {
                throw new CommonException("身份证不合法");
            }
        } catch (Exception e) {
            throw new CommonException("身份证号校验错误");
        }

    }
}
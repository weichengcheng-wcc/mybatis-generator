package com.cw.generator.utils;

/**
 * @Author C.W
 * @Date 2020/6/15 10:35 下午
 * @Description 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空的
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return str != null && !str.equals("") && !str.trim().equals("");
    }


    /**
     * 字符串转驼峰
     *
     * @param str
     * @param firstCamel
     * @return
     */
    public static String camelCase(String str, boolean firstCamel) {
        StringBuffer result = new StringBuffer();
        if (StringUtil.isNotBlank(str)) {
            str = str.trim();
            for (int i = 0; i < str.length(); i++) {
                if (firstCamel && i == 0) {
                    result.append(String.valueOf(str.charAt(i)).toUpperCase());
                    continue;
                }
                if (str.charAt(i) == '_') {
                    continue;
                }
                if (i > 0 && str.charAt(i - 1) == '_') {
                    result.append(String.valueOf(str.charAt(i)).toUpperCase());
                    continue;
                }
                result.append(str.charAt(i));
            }
        }

        return result.toString();
    }


}

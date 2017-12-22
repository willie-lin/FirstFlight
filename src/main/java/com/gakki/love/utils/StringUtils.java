package com.gakki.love.utils;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.util.StringUtils.hasLength;

/**
 * Created by 林漠 on 2017/6/14.
 */
@Slf4j
public class StringUtils {

    /**
     *
     * @param str
     * @return
     */

   //Check whether the given {@code String} is empty.


    public static boolean isEmpty(Object str){
        return (str == null || "".equals(str));
    }

    /**
     *
    Check whether the given {@code CharSequence} contains actual <em>text</em>.
    @param str
     * @return
     */
    public static boolean hasText(CharSequence str){
        if (!hasLength(str)){
            return false;
        }
        for (int i = 0, strLen = str.length();i < strLen; i++){

            if (!Character.isWhitespace(str.charAt(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given {@code String} has length.
     * @param str
     * @return
     */
    public static boolean hsaLength(CharSequence str){
        return str != null && str.length()>0;
    }


    /**
     * 判断给定的字符串中是否包含中文: 中文是全角,这种判断并不精确.
     *
     * @param str 需要判断的字符串
     * @return true, 如果含有中文
     */
    public static boolean hasChinese(String str){
        if (str.getBytes().length != str.length()){
            log.debug("has Chinese");
            return true;
        }
        return false;
    }

    /**
     * Concat strings.
     *
     * @param values the values
     * @return the string
     */
    public static String concat(final Object... values) {
        if (values == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (final Object value : values) {
            if (value == null) {
                sb.append("");
            } else {
                sb.append(value.toString());
            }
        }
        return sb.toString();
    }
}

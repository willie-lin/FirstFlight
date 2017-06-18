package com.gakki.love.utils;

/**
 * Created by 林漠 on 2017/6/14.
 */
public class StringUtils {

    /**
     * 判断给定的字符串中是否包含中文: 中文是全角,这种判断并不精确.
     *
     * @param args 需要判断的字符串
     * @return true, 如果含有中文
     */
    public static boolean hasChinese(String args) {
        if (args.getBytes().length != args.length()) {
            return true;
        }
        return false;
    }
}

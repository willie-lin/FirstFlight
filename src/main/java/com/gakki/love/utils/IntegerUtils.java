package com.gakki.love.utils;


import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/7
 * \* Time: 21:30
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class IntegerUtils {
    private static final Integer ZERO = 0;

    /*
     * Check whether the given {@code integer} greater than zero.
     * @param integer
     * @return
     */
    public static boolean isPositiveValue(Integer integer){
        return integer !=null && integer.compareTo(ZERO) > ZERO;

    }
    /*
    Check whether the given {@code integer} less than zero.
     */

    public static boolean isNegativeValue(Integer integer){
        return integer != null && integer.compareTo(ZERO) < ZERO;
    }
}
package com.gakki.love.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List; /**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/11
 * \* Time: 22:45
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class CollectionsUtils {
    public static <E> List<E> removeSame(Collection<E> tags) {
        return new ArrayList<>(new LinkedHashSet<>(tags));
    }
}
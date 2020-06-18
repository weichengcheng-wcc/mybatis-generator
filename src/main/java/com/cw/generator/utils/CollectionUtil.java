package com.cw.generator.utils;

import java.util.Collection;

/**
 * @Author C.W
 * @Date 2020/6/16 2:34 下午
 * @Description 集合工具
 */
public class CollectionUtil {

    /**
     * 集合不为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

}

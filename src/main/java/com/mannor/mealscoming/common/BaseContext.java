package com.mannor.mealscoming.common;

/**
 * 基于ThreadLocal封装工具类，用户保存的获取当前登录的Id
 * 只在当前的线程进行作用域，各个线程互不影响
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置id
      * @param id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取id
     * @return
     */
    public static Long getCurrentId() {
        return threadLocal.get();

    }
}

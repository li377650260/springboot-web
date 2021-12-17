package com.tonpower.springbootweb.utils;

import com.tonpower.springbootweb.domain.SysUser;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/23 15:33
 */
public class UserThreadLocal {
    // 一个线程内部共享，不同线程之间是隔离的，每个线程只能get()到自己的变量。
    // ThreadLocal 是 Java 里一种特殊变量，它是一个线程级别变量，每个线程都
    // 有一个 ThreadLocal 就是每个线程都拥有了自己独立的一个变量，竞态条件被
    // 彻底消除了，在并发模式下是绝对安全的变量。
    private UserThreadLocal(){
        
    }

    private final static ThreadLocal<SysUser> threadLocal= new ThreadLocal<>();

    public static void put(SysUser sysUser){
        threadLocal.set(sysUser);
    }

    public static SysUser get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}

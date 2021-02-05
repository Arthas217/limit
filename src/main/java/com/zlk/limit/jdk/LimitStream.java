package com.zlk.limit.jdk;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流http://dockone.io/article/10137
 *
 * @Author zc217
 * @Date 2020/12/18
 */
public class LimitStream {

    // 限流的个数
    private int maxCount = 10;
    // 指定的时间内
    private long interval = 60;
    // 原子类计数器
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    // 起始时间
    private static long startTime = System.currentTimeMillis();

    public static boolean limit(int maxCount, int interval) {
        atomicInteger.addAndGet(1);
        // 首次请求
        if (atomicInteger.get() == 1) {
            startTime = System.currentTimeMillis();
            atomicInteger.addAndGet(1);
            return true;
        }
        // 超过了间隔时间，直接重新开始计数
        if (System.currentTimeMillis() - startTime > interval * 1000) {
            startTime = System.currentTimeMillis();
            atomicInteger.set(1);
            return true;
        }
        // 还在间隔时间内,check有没有超过限流的个数
        if (atomicInteger.get() > maxCount) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(limit(5, 10));
        }
    }
}

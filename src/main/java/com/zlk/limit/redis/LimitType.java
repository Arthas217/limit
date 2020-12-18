package com.zlk.limit.redis;

/**
 * 限流类型
 * @Author zc217
 * @Date 2020/12/18
 */
public enum LimitType {
    /**
     * 自定义key
     */
    CUSTOMER,

    /**
     * 请求者IP
     */
    IP;
}

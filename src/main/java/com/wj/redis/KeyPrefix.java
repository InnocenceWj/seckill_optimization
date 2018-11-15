package com.wj.redis;

/**
 * @author ShallowAn
 */
public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}

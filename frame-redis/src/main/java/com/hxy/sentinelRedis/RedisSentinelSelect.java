package com.hxy.sentinelRedis;

/**
 * 类的功能描述.
 * 是否加载集群配置
 * @Auther hxy
 * @Date 2017/9/29
 */

public class RedisSentinelSelect {

    public RedisSentinelSelect(boolean isSentinel, String masterName) {
        //加载集群
        if (isSentinel) {
            new RedisSentinelConfigurationCustom(masterName);
        }
    }
}

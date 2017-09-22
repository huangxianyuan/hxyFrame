package com.hxy.sentinelRedis;

import org.springframework.data.redis.connection.RedisSentinelConfiguration;

/**
 * Created by zhph on 2017-07-20.
 */
public class RedisSentinelConfigurationCustom extends RedisSentinelConfiguration {

    public RedisSentinelConfigurationCustom(String master) {
        super(master, SetSentinel.getSentinelNodes());
    }

}

package com.hxy.utils;

import com.hxy.base.utils.SerializeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.Resource;

@Component
public class RedisUtil {


    /*private static JedisSentinelPool sentinelPool;*/

    private static JedisPool jedisPool;

   /* @Resource(name = "sentinelPool")
    public synchronized void setSentinelPool(JedisSentinelPool sentinelPool) {
        RedisUtil.sentinelPool = sentinelPool;
    }*/

    @Resource(name = "jedisPool")
    public synchronized void setJedisPool(JedisPool jedisPool) {
        RedisUtil.jedisPool = jedisPool;
    }


    private static Logger log = Logger.getLogger(RedisUtil.class);

    private static Jedis getJedis()  {
        Jedis jedis = null;
        try {
            //jedis = sentinelPool.getResource();
            jedis = jedisPool.getResource();
            return  jedis;
        } catch (JedisConnectionException e) {
            log.error("获取Redis 异常", e);
            throw e;
        }
    }


    /**
     * 保存对象到Redis 对象不过期
     *
     * @param key    待缓存的key
     * @param object 待缓存的对象
     * @return 返回是否缓存成功
     */
    public static boolean setObject(String key, Object object) throws Exception {
        return setObject(key, object, -1);
    }

    /**
     * 保存对象到Redis 并设置超时时间
     *
     * @param key     缓存key
     * @param object  缓存对象
     * @param timeout 超时时间
     * @return 返回是否缓存成功
     * @throws Exception 异常上抛
     */
    public static boolean setObject(String key, Object object, int timeout) throws Exception {
        String value = SerializeUtil.serialize(object);
        boolean result = false;
        try {
            //为-1时不设置超时时间
            if (timeout != -1) {
                setString(key,value,timeout);
            } else {
                setString(key,value);
            }
            result = false;
        } catch (Exception e) {
            throw e;
        }
        return  result;
    }

    /**
     * 从Redis中获取对象
     *
     * @param key 待获取数据的key
     * @return 返回key对应的对象
     */
    public static Object getObject(String key) throws Exception {
        Object object = null;
        try {
            String serializeObj = getString(key);
            if (null == serializeObj || serializeObj.length() == 0) {
                object = null;
            } else {
                object = SerializeUtil.deserialize(serializeObj);
            }
        }  catch (Exception e) {
            throw e;
        }
        return object;
    }

    /**
     * 缓存String类型的数据,数据不过期
     *
     * @param key   待缓存数据的key
     * @param value 需要缓存的额数据
     * @return 返回是否缓存成功
     */
    public static boolean setString(String key, String value) throws Exception {
        return setString(key, value, -1);
    }

    /**
     * 缓存String类型的数据并设置超时时间
     *
     * @param key     key
     * @param value   value
     * @param timeout 超时时间
     * @return 返回是否缓存成功
     */
    public static boolean setString(String key, String value, int timeout) throws Exception {
        String result;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.set(key, value);
            if (timeout != -1) {
                jedis.expire(key, timeout);
            }
            if ("OK".equals(result)) {
                return true;
            } else {
                return  false;
            }
        } catch (Exception e){
            throw  e;
        } finally {
            releaseRedis(jedis);
        }
    }

    /**
     * 获取String类型的数据
     *
     * @param key 需要获取数据的key
     * @return 返回key对应的数据
     */
    @SuppressWarnings("deprecation")
    public static  String getString(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            throw e;
        } finally {
            releaseRedis(jedis);
        }
    }

    /**
     * Jedis 对象释放
     * @param jedis
     */
    public static void releaseRedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


    /**
     * 删除缓存中的数据
     *
     * @param key 需要删除数据的key
     * @return 返回是否删除成功
     */
    public static boolean del(String key) throws Exception {
        Long num;
        Jedis jedis = null;
        Boolean result = false;
        try {
            jedis = getJedis();
            num = jedis.del(key);
            if (num.equals(1L)) {
                result = true;
            }
        } catch (Exception e) {
            throw  e;
        } finally {
            releaseRedis(jedis);
        }
        return result;
    }

    /**
     * 用户 登陆并踢出已登陆的用户
     * @param user  用户
     * @param session session
     * @return
     */
    /*public static boolean userLogon(UserEntity user, Session session) throws Exception {
        Jedis jedis = null;
        Assert.notNull(session,"session 不能为空");
        Assert.notNull(user,"用户不能为空");
        boolean result = false;
        try {
            jedis = getJedis();
            String sessionId = jedis.get(Constants.ONLINE_USER + "_"
                    + user.getNo());
            if (sessionId != null) {
                jedis.del(sessionId);
            }
            jedis.set(Constants.ONLINE_USER + "_" + user.getNo(), session.getId().toString());
            jedis.expire(Constants.ONLINE_USER + "_" + user.getNo(), 1800);
            result = true;
        } catch (Exception e) {
            throw  e;
        } finally {
            releaseRedis(jedis);
        }
        return result;
    }*/

    /**
     * 更新当前用户的在线时间
     * @param session session
     */
    /*@SuppressWarnings("deprecation")
    public static void updateCurrentUserTimeOut(Session session) throws Exception {
        UserEntity user = (UserEntity) session.getAttribute("user");
        Assert.notNull(session,"session 不能为空");
        if (user != null) {
            Jedis jedis = null;
            try {
                jedis = getJedis();
                jedis.set(Constants.ONLINE_USER + "_" + user.getNo(), session.getId().toString());
                jedis.expire(Constants.ONLINE_USER + "_" + user.getNo(), 1800);
            } catch (Exception e) {
                throw  e;
            } finally {
                releaseRedis(jedis);
            }
        }
    }*/

    /**
     * User
     * @param user 用户退出登陆
     * @return
     */
    /*public static boolean logonOut(UserEntity user) throws Exception {
        Jedis jedis = null;
        Assert.notNull(user,"user 不能为空");
        try {
            jedis = getJedis();
            if (jedis.exists(Constants.ONLINE_USER + "_" + user.getNo())) {
                String sessionId = jedis.get(Constants.ONLINE_USER + "_"
                        + user.getNo());
                if (sessionId != null) {
                    jedis.del(sessionId);
                }
                jedis.del(Constants.ONLINE_USER + "_" + user.getNo());
            }
            return true;
        } catch (Exception e) {
            throw  e;
        } finally {
            releaseRedis(jedis);
        }

    }*/

    /**
     * 判断用户是否登陆
     * @param user user
     * @return
     */
    /*public static boolean isUserLogon(UserEntity user) throws Exception {
        Jedis jedis = null;
        Assert.notNull(user,"user 不能为空");
        try {
            jedis = getJedis();
            if (jedis.exists(user.getNo())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            releaseRedis(jedis);
        }
    }*/

}

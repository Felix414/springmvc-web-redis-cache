package com.me.java.base.redis;



import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @ClassName JedisManager
 * @Description Redis Manager Utils
 * @author John
 * @Date 2016年10月10日下午12:33:31
 * @version 1.0.0
 */
public class JedisManager {
    private static JedisPool jedisPool;

    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public  void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
        } catch (Exception e) {
            throw new JedisConnectionException(e);
        }
        return jedis;
    }

    public static void returnResource(Jedis jedis, boolean isBroken) {
        if (jedis == null)
            return;
        /**
         * @deprecated starting from Jedis 3.0 this method will not be exposed.
         *             Resource cleanup should be done using @see
         *             {@link redis.clients.jedis.Jedis#close()} if (isBroken){
         *             getJedisPool().returnBrokenResource(jedis); }else{
         *             getJedisPool().returnResource(jedis); }
         */
        jedis.close();
    }

    public  static byte[] getValueByKey(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        byte[] result = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            result = jedis.get(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }

    public static void deleteByKey(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            Long result = jedis.del(key);
//            LoggerUtils.fmtDebug(getClass(), "删除Session结果：%s", result);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public static void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime)
            throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.set(key, value);
            // 如果过期时间大于0则设置过期时间
            if (expireTime > 0) {
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    /**
     * 根据KEY写入value
     * 
     * @author Bert create to 2018年8月10日 上午10:29:29
     * @param key
     * @param value
     * @param expireTime
     *            大于0则写入过期时间，秒为单位
     * @return 
     * @throws Exception
     */
    public static boolean setValue(String key, String value, int expireTime) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        boolean is_b=false;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            // 如果过期时间大于0则设置过期时间
            if (expireTime > 0) {
                jedis.expire(key, expireTime);
            }
            is_b=true;
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return is_b;
    }

    /**
     * 获取key的值
     * 
     * @author Bert create to 2018年8月10日 上午10:29:04
     * @param key
     * @return
     * @throws Exception
     */
    public static String getValue(String key) throws Exception {
        Jedis jedis = null;
        String result = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }

    /**
     * 查询key是否存在
     * 
     * @author Bert create to 2018年8月10日 下午4:07:48
     * @param key
     * @return
     * @throws Exception
     */
    public static boolean exists(String key) throws Exception {
        Jedis jedis = null;
        boolean result = false;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            result = jedis.exists(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }

    public static boolean delValue(String key) throws Exception {
        Jedis jedis = null;
        boolean result = false;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            if (jedis.exists(key)) {
                result = jedis.del(key) > 0;
            }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }


}

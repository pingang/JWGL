package com.xupt.util;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCacheUtil {

    private static JedisPool pool;

    static {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(2000);
            config.setMaxIdle(500);
            config.setTestOnBorrow(true);


            pool = new JedisPool(config, "203.195.193.218",6379);
    }


    public static Jedis getJedis(){
       return pool.getResource();
    }

}

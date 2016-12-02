package com.cn.pflow.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisUtil
{
	/* static value */
	static JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
	

	public static Jedis getJedisEx()
	{
		return jedisPool.getResource();
	}
	
	public static JedisPool getJedisPoolEx()
	{
		return jedisPool;
	}
}

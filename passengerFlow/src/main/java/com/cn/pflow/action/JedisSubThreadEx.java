package com.cn.pflow.action;

import brickstream.http.model.PflowHandler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class JedisSubThreadEx extends Thread 
{
	private final JedisPool jedisPool;
	private final JedisPubSubEx subscriber = new JedisPubSubEx();
	private final String JEDIS_CHNL_REALTIMEDATA = "jedisRealtimeData";
	private final String JEDIS_CHNL_ENABLE_EQPT = "jedisEnableEqpt";
	private final String JEDIS_CHNL_DISABLE_EQPT = "jedisDisableEqpt";

	public JedisSubThreadEx(JedisPool jedisPool) throws Exception 
	{
		super("JedisSubThreadEx");
		this.jedisPool = jedisPool;
	}

	@Override
	public void run() 
	{
		System.out.println(String.format("subscribe redis, channel %s, thread will be blocked", JEDIS_CHNL_REALTIMEDATA));
		
		Jedis jedis = null;
		try 
		{
			jedis = jedisPool.getResource();
			jedis.subscribe(subscriber, JEDIS_CHNL_REALTIMEDATA, JEDIS_CHNL_ENABLE_EQPT, JEDIS_CHNL_DISABLE_EQPT);
		}
		catch (Exception e) 
		{
			System.out.println(String.format("subsrcibe channel error, %s", e));
		}
		finally 
		{
			if (jedis != null) 
			{
				jedis.close();
			}
		}
	}

	public class JedisPubSubEx extends JedisPubSub 
	{
		RealTimeReverse _reverse = new RealTimeReverse();
		PflowHandler _pflow = new PflowHandler();

		@Override
		public void onMessage(String channel, String message) 
		{
			// System.out.println("~~~~~~jedis:[channel]"+channel+"[message]"+message);
			try {
				switch (channel) 
				{
				case JEDIS_CHNL_REALTIMEDATA: 
				{
					if (null != RealTimeReverse.wctx)
						_reverse.RealtimeReverseData(message);
				}
				break;
				case JEDIS_CHNL_ENABLE_EQPT: 
				{
					_pflow.AddSubscribeEqpt(message);
				}
				break;
				case JEDIS_CHNL_DISABLE_EQPT:
				{
					_pflow.DelSubscribeEqpt(message);
				}
				break;
				default: break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

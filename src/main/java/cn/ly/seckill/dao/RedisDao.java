package cn.ly.seckill.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import cn.ly.seckill.po.SeckillStock;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private JedisPool jedisPool;
	
	private RuntimeSchema<SeckillStock> schema = RuntimeSchema.createFrom(SeckillStock.class);
	
	public RedisDao(String ip,int port){
		jedisPool = new JedisPool(ip,port);
	}
	
	public SeckillStock getSeckill(int seckillId){
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckillId:" + seckillId;
				//redis没有实现序列化，存储在redis中的数据都是byte类型，所以取出来的数据也是byte类型
				//这个与memcached不同，memcached取出是Object类型
				//使用protostuff进行反序列化
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes != null){
					SeckillStock seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return null;
	}
	
	public String putSeckill(SeckillStock seckill){
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckillId:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));//LinkedBuffer一个字节缓冲区
				int timeout = 60 * 60;//60秒 * 60
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}
	
	

}

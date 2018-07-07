package cn.ly.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.ly.seckill.po.SeckillStock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest{
	
	private int seckillId = 1000;
	
	@Autowired
	private RedisDao rdao;
	
	@Autowired
	private SeckillStockDao ssdao;

	@Test
	public void testSeckill() throws Exception{
		SeckillStock seckill = ssdao.getSeckillStock(seckillId);
		String result = rdao.putSeckill(seckill);
		System.out.println(result);
		SeckillStock s = rdao.getSeckill(seckillId);
		System.out.println(s);
	}

}

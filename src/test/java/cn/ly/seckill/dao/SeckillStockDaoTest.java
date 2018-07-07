package cn.ly.seckill.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.ly.seckill.po.SeckillStock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillStockDaoTest {
	
	@Autowired
	private SeckillStockDao sdao;

	@Test
	public void testUpdateSeckillStockNumber() {
		int seckillId = 1005;
		int num = 50;
		int r = sdao.updateSeckillStockNumber(seckillId, num);
		if(r > 0){
			System.out.println("成功");
		}else{
			System.out.println("失败");
		}
	}

	@Test
	public void testReduceNumber() {
		int seckillId = 1000;
		Date killTime = new Date();
		int r = sdao.reduceNumber(seckillId, killTime);
		if(r > 0){
			System.out.println("成功");
		}else{
			System.out.println("失败");
		}
	}
	
	/**
	 * SeckillStock [seckillId=1000, name=999元秒杀iphone6, number=100, startTime=Sat Oct 01 00:48:45 CST 2016, 
	 * endTime=Sun Oct 02 00:00:00 CST 2016, createTime=Sat Oct 01 00:37:12 CST 2016]
	 */
	@Test
	public void testGetSeckillStock() {
		int seckillId = 1000;
		SeckillStock s = sdao.getSeckillStock(seckillId);
		System.out.println(s);
	}

	/**
	 * 	
	 	SeckillStock [seckillId=1005, name=399元秒杀联想, number=400, startTime=Sat Oct 01 00:00:00 CST 2016, endTime=Sun Oct 02 00:00:00 CST 2016, createTime=Sat Oct 01 00:37:12 CST 2016]
		SeckillStock [seckillId=1004, name=799元秒杀华为青春版, number=400, startTime=Sat Oct 01 00:00:00 CST 2016, endTime=Sun Oct 02 00:00:00 CST 2016, createTime=Sat Oct 01 00:37:12 CST 2016]
		SeckillStock [seckillId=1003, name=199元秒杀红米note, number=400, startTime=Sun Oct 02 00:00:00 CST 2016, endTime=Mon Oct 03 00:00:00 CST 2016, createTime=Sat Oct 01 00:37:12 CST 2016]
		SeckillStock [seckillId=1002, name=299元秒杀小米4, number=300, startTime=Sun Oct 02 00:00:00 CST 2016, endTime=Mon Oct 03 00:00:00 CST 2016, createTime=Sat Oct 01 00:37:12 CST 2016]
		SeckillStock [seckillId=1001, name=499元秒杀ipad2, number=200, startTime=Sat Oct 01 00:00:00 CST 2016, endTime=Sun Oct 02 00:00:00 CST 2016, createTime=Sat Oct 01 00:37:12 CST 2016]
	 */
	@Test
	public void testQuerySeckillStock() {
		List<SeckillStock> list = sdao.querySeckillStock(0, 5);
		for(SeckillStock s : list){
			System.out.println(s);
		}
	}

}

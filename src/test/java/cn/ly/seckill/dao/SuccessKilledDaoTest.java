package cn.ly.seckill.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.ly.seckill.po.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Autowired
	private SuccessKilledDao sdao;

	@Test
	public void testInsertSuccessKilled() {
		try {
			SuccessKilled sk = new SuccessKilled();
			sk.setSeckillId(1000);
			sk.setCreateTime(new Date());
			sk.setUserPhone(13264087964L);
			sk.setState(Short.valueOf("0"));
			int insertCount = sdao.insertSuccessKilled(sk);
	        System.out.println("insertCount=" + insertCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSuccessKilled() {
		int seckillId = 1000;
		long userPhone = 13264087964L;
		SuccessKilled sk = sdao.getSuccessKilled(seckillId, userPhone);
		System.out.println(sk);
		System.out.println(sk.getSeckillStock());
	}

}

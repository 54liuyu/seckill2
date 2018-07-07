package cn.ly.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.ly.seckill.dto.Exposer;
import cn.ly.seckill.dto.SeckillExecution;
import cn.ly.seckill.po.SeckillStock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"})
public class SeckillStockServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillStockService service;

	@Test
	public void testGetSeckillStockList() {
		List<SeckillStock> list = service.getSeckillStockList(0, 5);
        logger.info("list={}",list);
		/*for(SeckillStock s : list){
			System.out.println(s);
		}*/
	}

	@Test
	public void testGetSeckillStock() {
		int seckillId = 1000;
		SeckillStock s = service.getSeckillStock(seckillId);
        logger.info("seckill={}",s);
	}

	@Test
	public void testExportSeckillUrl() {
		try{
			int seckillId1 = 1000;
			Exposer e1 = service.exportSeckillUrl(seckillId1);
			System.out.println(e1);
		}catch(Exception e){
			e.getMessage();
		}
		try{
			int seckillId2 = 1001;
			Exposer e2 = service.exportSeckillUrl(seckillId2);
			System.out.println(e2);
		}catch(Exception e){
			e.getMessage();
		}
		try{
			int seckillId3 = 1002;
			Exposer e3 = service.exportSeckillUrl(seckillId3);
			System.out.println(e3);
		}catch(Exception e){
			e.getMessage();
		}
		try{
			int seckillId4 = 1;
			Exposer e4 = service.exportSeckillUrl(seckillId4);
			System.out.println(e4);
		}catch(Exception e){
			e.getMessage();
		}
	}

	@Test
	public void testExecuteSeckill() {
		try {
			String md5 = "039682714a004d08a41b1324d17c6260";
			int seckillId = 1000;
			long userPhone = 13264087964L;
			SeckillExecution se = service.executeSeckill(seckillId, userPhone, md5);
			logger.info("SeckillExecution={}",se);
		} catch (Exception e) {
			logger.info("ex3333={}",e.getMessage());
		}
	}
	
	@Test
	public void testExecuteSeckillProcedure(){
		try {
			String md5 = "95815017d422cc6be8023e16458186fd";
			int seckillId = 1001;
			long userPhone = 18564087966L;
			SeckillExecution se = service.executeSeckillProcedure(seckillId, userPhone, md5);
			logger.info("SeckillExecution==========={}",se);
		} catch (Exception e) {
			logger.info("ex======={}" + e.getMessage());
		}
	}

}

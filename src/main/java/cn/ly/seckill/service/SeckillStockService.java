package cn.ly.seckill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.ly.seckill.dto.Exposer;
import cn.ly.seckill.dto.SeckillExecution;
import cn.ly.seckill.exception.RepeatKillException;
import cn.ly.seckill.exception.SeckillCloseException;
import cn.ly.seckill.exception.SeckillException;
import cn.ly.seckill.po.SeckillStock;

/**
 * 业务接口:站在"使用者"角度设计接口
 * 三个方面:方法定义粒度,参数,返回类型(return 类型/异常)
 */

public interface SeckillStockService {
	
	/**
	 * 
	 * @return
	 */
	List<SeckillStock> getSeckillStockList(int offset,int rows);
	
	/**
	 * 
	 * @param seckillId
	 * @return
	 */
	SeckillStock getSeckillStock(int seckillId);
	
	/**
	 * 
	 * @param seckillId
	 * @return
	 */
	Exposer exportSeckillUrl(int seckillId);
	
	/**
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws RepeatKillException
	 * @throws SeckillCloseException
	 */
	SeckillExecution executeSeckill(int seckillId,long userPhone,String md5)
		throws SeckillException,RepeatKillException,SeckillCloseException;
	
	
	/**
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws RepeatKillException
	 * @throws SeckillCloseException
	 */
	SeckillExecution executeSeckillProcedure(int seckillId,long userPhone,String md5)
		throws SeckillException;
	
	
	

}

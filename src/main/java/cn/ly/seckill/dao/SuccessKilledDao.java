package cn.ly.seckill.dao;

import org.apache.ibatis.annotations.Param;

import cn.ly.seckill.po.SuccessKilled;

public interface SuccessKilledDao {
	
	/**
	 * 保存用户秒杀成功的商品
	 * @param sk
	 * @return
	 */
	int insertSuccessKilled(SuccessKilled sk);
	
	/**
	 * 获取一个用户秒杀的一个商品
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	SuccessKilled getSuccessKilled(@Param("seckillId") int seckillId,@Param("userPhone") long userPhone);

}

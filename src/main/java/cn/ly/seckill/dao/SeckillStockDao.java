package cn.ly.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.ly.seckill.po.SeckillStock;

public interface SeckillStockDao {
	
	/**
	 * 更新库存
	 * @param seckillId
	 * @param num
	 * @return
	 */
	int updateSeckillStockNumber(@Param("seckillId") int seckillId,@Param("num") int num);
	
	/**
	 * 秒杀减库存
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	int reduceNumber(@Param("seckillId") int seckillId,@Param("killTime") Date killTime);
	
	/**
	 * 获取一个秒杀商品对象
	 * @param seckillId
	 * @return
	 */
	SeckillStock getSeckillStock(int seckillId);
	
	
	/**
	 * 查询秒杀商品列表
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<SeckillStock> querySeckillStock(@Param("offset") int offset,@Param("rows") int rows);
	
	
	/**
	 * 执行秒杀的存储过程
	 * @param map
	 * @return
	 */
	void executeSeckill(Map<String,Object> map);

}

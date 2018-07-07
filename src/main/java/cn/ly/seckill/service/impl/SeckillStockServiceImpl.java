package cn.ly.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import cn.ly.seckill.dao.RedisDao;
import cn.ly.seckill.dao.SeckillStockDao;
import cn.ly.seckill.dao.SuccessKilledDao;
import cn.ly.seckill.dto.Exposer;
import cn.ly.seckill.dto.SeckillExecution;
import cn.ly.seckill.exception.RepeatKillException;
import cn.ly.seckill.exception.SeckillCloseException;
import cn.ly.seckill.exception.SeckillException;
import cn.ly.seckill.po.SeckillStock;
import cn.ly.seckill.po.SuccessKilled;
import cn.ly.seckill.service.SeckillStockService;
import cn.ly.seckill.util.SeckillState;
import cn.ly.seckill.util.SeckillTool;
import cn.ly.seckill.util.SuccessKilledState;

@Service
public class SeckillStockServiceImpl implements SeckillStockService {
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private RedisDao rdao;
	
	@Autowired
	private SeckillStockDao ssdao;

	@Autowired
	private SuccessKilledDao skdao;
	
	public List<SeckillStock> getSeckillStockList(int offset,int rows) {
		return ssdao.querySeckillStock(offset, rows);
	}

	public SeckillStock getSeckillStock(int seckillId) {
		//return ssdao.getSeckillStock(seckillId);
		SeckillStock s = rdao.getSeckill(seckillId);
		if(s == null){
			s = ssdao.getSeckillStock(seckillId);
			rdao.putSeckill(s);
			return s;
		}
		return s;
	}

	/**
	 * 返回一个秒杀暴露的接口
	 * 0、如果用户提供的秒杀商品id对应的产品不存在（为null），则返回false
	 * 1、如果当前时间小于秒杀开启时间，则秒杀未开始，返回false
	 * 2、如果当前时间大于秒杀结束时间，则秒杀已结束，返回false
	 * 3、当前时间大于开启时间，小于结束时间，则返回true
	 */
	public Exposer exportSeckillUrl(int seckillId) {
		//SeckillStock s = ssdao.getSeckillStock(seckillId);
		SeckillStock s = rdao.getSeckill(seckillId);
		if(s == null){
			s = ssdao.getSeckillStock(seckillId);
			if(s == null){
				return new Exposer(false,seckillId);
			}
		}
		rdao.putSeckill(s);
		long startTime = s.getStartTime().getTime();
		long endTime = s.getEndTime().getTime();
		long nowTime = new Date().getTime();
		if(startTime > nowTime || nowTime > endTime){
			return new Exposer(false,seckillId,startTime,nowTime,endTime);
		}
		return new Exposer(true,SeckillTool.getMD5(seckillId),seckillId);
	}
	
	/**
	 * 1、当md5为null或者解密失败，则人为是md5数据被篡改
	 * 2、正常的情况是先查询库存，库存数量大于0，才继续进行。但是这样降低了多线程的并发度，
	 * 因为减库存表是需要对商品库存记录进行行级锁的，并且减库存操作和插入成功秒杀记录是在一个事务中的，
	 * 所以这样所有的秒杀线程就都是顺序执行了，完全没有发挥线程并发的功能。所以效率低。
	 * 3、现在我们先假设库存是满足的，所以先进行插入成功秒杀单，然后再进行减库存。
	 * 4、如果库存不足，则事务回滚，也不会造成插入错误。
	 * 
	 * 总结：这种处理方式就是先假设数据满足条件，先进行没有锁的，可以线程并发的处理逻辑，然后再进行验证，
	 * 假设成功，则提交数据。如果数据不满足条件，则回滚，非常类似CAS操作，只不过这种CAS操作，范围更大了。
	 * 不是一个sql搞定的，而是分成了两次，甚至多次，但是多线程并行操作的，效率比原来的方式高。
	 * 这中做法核心就是把有锁的地方需要串行的程序缩小在最小的范围内，只有这个点上，程序是串行执行的，其它的程序
	 * 都是并发执行的。
	 */
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public SeckillExecution executeSeckill(int seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if(md5 == null || !md5.equals(SeckillTool.getMD5(seckillId))){
			return new SeckillExecution(seckillId, SeckillState.DATAREWRITE);
		}
		try {
			SuccessKilled sk = new SuccessKilled(seckillId,userPhone,SuccessKilledState.SUCCESS.getState());
			int insertResult = skdao.insertSuccessKilled(sk);
			if(insertResult <= 0){
				throw new RepeatKillException("seckill repeated");
			}else{
				int updateResult = ssdao.reduceNumber(seckillId, new Date());
				if(updateResult <= 0){
					throw new SeckillCloseException("seckill closed");
				}else{
					sk = skdao.getSuccessKilled(seckillId, userPhone);
					return new SeckillExecution(sk,SeckillState.SUCCESS);
				}
			}
		} catch (RepeatKillException e) {
			throw e;
		} catch (SeckillCloseException e){
			throw e;
		} catch (Exception e){
			logger.error(e.getMessage(),e);
			throw new SeckillException(e.getMessage());
		}
	}

	@Override
	public SeckillExecution executeSeckillProcedure(int seckillId, long userPhone, String md5){
		if(md5 == null || !md5.equals(SeckillTool.getMD5(seckillId))){
			return new SeckillExecution(seckillId, SeckillState.DATAREWRITE);
		}
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			Date killTime = new Date();
			map.put("seckillId", seckillId);
			map.put("userPhone", userPhone);
			map.put("killTime", killTime);
			map.put("killState", SuccessKilledState.SUCCESS.getState());
			map.put("result", null);
			ssdao.executeSeckill(map);
			short result = MapUtils.getShortValue(map, "result",Short.valueOf("-2"));
			if(result > 0){
				SuccessKilled successKilled = new SuccessKilled(seckillId, userPhone, SuccessKilledState.SUCCESS.getState(),killTime);
				return new SeckillExecution(successKilled, SeckillState.SUCCESS);
			}else{
				return new SeckillExecution(seckillId, SeckillState.getSeckillState(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new SeckillExecution(seckillId, SeckillState.ERROR);
		}
	}

}

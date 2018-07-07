package cn.ly.seckill.action;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ly.seckill.dto.Exposer;
import cn.ly.seckill.dto.SeckillExecution;
import cn.ly.seckill.dto.SeckillResult;
import cn.ly.seckill.exception.RepeatKillException;
import cn.ly.seckill.exception.SeckillCloseException;
import cn.ly.seckill.po.SeckillStock;
import cn.ly.seckill.service.SeckillStockService;
import cn.ly.seckill.util.SeckillState;

@Controller
@RequestMapping("/seckill")
public class SeckillAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillStockService service;
	
	//1、获得秒杀商品列表页
	@RequestMapping(value = "/list/{offset}/{rows}",method = RequestMethod.GET)
	public String list(@PathVariable("offset") int offset,@PathVariable("rows") int rows,Model model){
		model.addAttribute("list",service.getSeckillStockList(offset, rows));
		return "list";
	}
	
	//2、获得商品详情
	@RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Integer seckillId,Model model){
		//根据seckillId判断是否有这个秒杀商品
		if(seckillId == null){
			return "redirect:/seckill/list";
		}
		SeckillStock s = service.getSeckillStock(seckillId);
		if(s == null){
			return "redirect:/seckill/list";
		}
		//有则返回SeckillStock对象，到detail页面
		model.addAttribute("seckill", s);
		return "detail";
	}
	
	//3、获得系统时间
	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> getServerTime(){
		return new SeckillResult(true,new Date().getTime());
	}
	
	//4、暴露秒杀接口
	@RequestMapping(value = "/{seckillId}/expose", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> expose(@PathVariable("seckillId") Integer seckillId){
		SeckillResult<Exposer> result;
		try {
			//暴露秒杀接口的service
			Exposer exposer = service.exportSeckillUrl(seckillId);
			result = new SeckillResult(true,exposer);
		} catch (Exception e) {
			logger.info(e.getMessage());
			result = new SeckillResult(false,e.getMessage());
		}
		return result;
	}
	
	//5、执行秒杀
	@RequestMapping(value = "/{seckillId}/{md5}/execute", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Integer seckillId,
			@PathVariable("md5") String md5,@CookieValue(value = "killPhone", required = false) Long userPhone){
		//查看cookie是否保存了手机
		if(userPhone == null){
			return new SeckillResult(false,"未注册");
		}
		try{
			//执行秒杀
			SeckillExecution se = service.executeSeckill(seckillId, userPhone, md5);
			return new SeckillResult<SeckillExecution>(true,se);
		}catch(RepeatKillException e){
			SeckillExecution se = new SeckillExecution(seckillId, SeckillState.REPEATKILL);
			return new SeckillResult<SeckillExecution>(true, se);
		}catch(SeckillCloseException e){
			SeckillExecution se = new SeckillExecution(seckillId, SeckillState.END);
			return new SeckillResult<SeckillExecution>(true, se);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			SeckillExecution se = new SeckillExecution(seckillId, SeckillState.ERROR);
			return new SeckillResult<SeckillExecution>(true, se);
		}
	}
	
	//5、执行秒杀（mysql存储过程的方式）
	@RequestMapping(value = "/{seckillId}/{md5}/executeProcedure", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> executeProcedure(@PathVariable("seckillId") Integer seckillId,
			@PathVariable("md5") String md5,@CookieValue(value = "killPhone", required = false) Long userPhone){
		//查看cookie是否保存了手机
		if(userPhone == null){
			return new SeckillResult(false,"未注册");
		}
		try{
			//执行秒杀
			SeckillExecution se = service.executeSeckillProcedure(seckillId, userPhone, md5);
			return new SeckillResult<SeckillExecution>(true,se);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			SeckillExecution se = new SeckillExecution(seckillId, SeckillState.ERROR);
			return new SeckillResult<SeckillExecution>(true, se);
		}
	}

}

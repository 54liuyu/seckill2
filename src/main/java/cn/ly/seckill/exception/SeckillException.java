package cn.ly.seckill.exception;

public class SeckillException extends RuntimeException {
	
	public SeckillException(String msg) {
		super(msg);
	}
	
	public SeckillException(String msg,Throwable cause) {
		super(msg,cause);
	}
}

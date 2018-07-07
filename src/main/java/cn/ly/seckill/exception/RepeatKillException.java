package cn.ly.seckill.exception;

public class RepeatKillException extends SeckillException {

	public RepeatKillException(String msg) {
		super(msg);
	}
	
	public RepeatKillException(String msg,Throwable cause) {
		super(msg,cause);
	}
	
}

package cn.ly.seckill.util;

public enum SeckillState {
	
	SUCCESS(Short.parseShort("1"),"秒杀成功"),
	END(Short.parseShort("0"),"秒杀已结束"),
	REPEATKILL(Short.parseShort("-1"),"重复秒杀"),
	ERROR(Short.parseShort("-2"),"服务器错误"),
	DATAREWRITE(Short.parseShort("-3"),"数据被篡改");
	
	private short state;
	private String stateInfo;
	
	private SeckillState(short state,String msg){
		this.state = state;
		this.stateInfo = msg;
	}

	public short getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	public static SeckillState getSeckillState(short state){
        for (SeckillState s : values()) {
            if (s.getState() == state) {
                return s;
            }
        }
        return null;
	}
}
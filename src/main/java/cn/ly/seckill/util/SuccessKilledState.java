package cn.ly.seckill.util;

public enum SuccessKilledState {
	VALIDATE(Short.parseShort("-1"),"无效"),
	SUCCESS(Short.parseShort("0"),"成功"),
	PAY(Short.parseShort("1"),"已付款"),
	SEND(Short.parseShort("2"),"已发货");
	
	private short state;
	private String msg;
	private SuccessKilledState(short state,String msg){
		this.state = state;
		this.msg = msg;
	}
	
	public Short getState() {
		return state;
	}
	public String getMsg() {
		return msg;
	}
	
	public static SuccessKilledState getSuccessKilledState(int state){
        for (SuccessKilledState s : values()) {
            if (s.getState() == state) {
                return s;
            }
        }
        return null;
	}
}

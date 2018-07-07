package cn.ly.seckill.po;

import java.util.Date;

import cn.ly.seckill.util.SuccessKilledState;

public class SuccessKilled {
    private int seckillId;

    private long userPhone;

    private short state;

    private Date createTime;
    
    private SeckillStock seckillStock;
    
    public SuccessKilled(){
    	
    }

	public SuccessKilled(int seckillId, long userPhone, short state) {
		super();
		this.seckillId = seckillId;
		this.userPhone = userPhone;
		this.state = state;
	}



	public SuccessKilled(int seckillId, long userPhone, short state, Date createTime) {
		super();
		this.seckillId = seckillId;
		this.userPhone = userPhone;
		this.state = state;
		this.createTime = createTime;
	}

	public SeckillStock getSeckillStock() {
		return seckillStock;
	}

	public void setSeckillStock(SeckillStock seckillStock) {
		this.seckillStock = seckillStock;
	}

	public int getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(int seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", createTime=" + createTime + "]";
	}
}

package cn.ly.seckill.dto;

import cn.ly.seckill.po.SuccessKilled;
import cn.ly.seckill.util.SeckillState;

public class SeckillExecution {

	private int seckillId;
	
	private short state;
	
	private String stateInfo;
	
	private SuccessKilled successKilled;
	
	
	
	public SeckillExecution(int seckillId,SeckillState seckillState) {
		super();
		this.seckillId = seckillId;
		this.state = seckillState.getState();
		this.stateInfo = seckillState.getStateInfo();
	}

	public SeckillExecution(SuccessKilled successKilled,SeckillState seckillState) {
		super();
		this.seckillId = successKilled.getSeckillId();
		this.successKilled = successKilled;
		this.state = seckillState.getState();
		this.stateInfo = seckillState.getStateInfo();
	}

	public int getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(int seckillId) {
		this.seckillId = seckillId;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}
	
	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}
}

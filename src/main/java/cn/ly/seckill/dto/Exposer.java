package cn.ly.seckill.dto;

public class Exposer {
	
	private boolean exposed;
	
	private String md5;
	
	private int seckillId;
	
	private long nowTime;
	
	private long startTime;
	
	private long endTime;


	public Exposer(boolean exposed, int seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	

	public Exposer(boolean exposed, String md5, int seckillId) {
		super();
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}



	public Exposer(boolean exposed, int seckillId, long nowTime, long startTime, long endTime) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.nowTime = nowTime;
		this.startTime = startTime;
		this.endTime = endTime;
	}



	public boolean isExposed() {
		return exposed;
	}



	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}



	public String getMd5() {
		return md5;
	}



	public void setMd5(String md5) {
		this.md5 = md5;
	}



	public int getSeckillId() {
		return seckillId;
	}



	public void setSeckillId(int seckillId) {
		this.seckillId = seckillId;
	}



	public long getNowTime() {
		return nowTime;
	}



	public void setNowTime(long nowTime) {
		this.nowTime = nowTime;
	}



	public long getStartTime() {
		return startTime;
	}



	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}



	public long getEndTime() {
		return endTime;
	}



	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}



	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", seckillId=" + seckillId + ", nowTime=" + nowTime
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
	
	
	
	

}

package cn.ly.seckill.dto;

public class SeckillResult<T> {
	
	private T data;
	
	private boolean success;
	
	private String errorMsg;

	public SeckillResult(boolean success, String errorMsg) {
		super();
		this.success = success;
		this.errorMsg = errorMsg;
	}

	public SeckillResult(boolean success,T data) {
		super();
		this.data = data;
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}

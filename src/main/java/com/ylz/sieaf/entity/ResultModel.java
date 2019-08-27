package com.ylz.sieaf.entity;

public class ResultModel {

	public static final String SUCCESS = "1";
	public static final String FAILED = "0";

	private Object data;
	private String flag;
	private String cause;

	public Object getData() {
		if(null == data || "null".equals(data)){
			return "";
		}
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
}

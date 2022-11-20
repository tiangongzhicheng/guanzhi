package com.moyu.vo;

import com.alibaba.fastjson.JSON;


public class BaseResponse<T> {

	private boolean status;
	private String message;
	private boolean success;
	private int errorCode;
	private T result;
	private PageUtil pageUtil;


	public BaseResponse() {
	}

	public BaseResponse(boolean status, String message, boolean success, int errorCode) {
		this.status = status;
		this.message = message;
		this.success = success;
		this.errorCode = errorCode;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}


	public String toJsonString() {
		return JSON.toJSONString(this);
	}

	public static BaseResponse getSuccessRes() {
		return new BaseResponse(true, "", true, 0);
	}

	public static BaseResponse getFailedRes(String message) {
		return new BaseResponse(false, message, false, 0);
	}

	public PageUtil getPageUtil() {
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}
}

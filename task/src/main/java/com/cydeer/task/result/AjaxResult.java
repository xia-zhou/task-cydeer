/*
 * Copyright (C), 2014-2015, 
 * FileName: AjaxResult.java
 * Author:   xia zhou
 * Date:     2015年5月12日 下午7:26:21
 * Description: 
 */
package com.cydeer.task.result;

/**
 * <pre>
 * 〈一句话是什么〉
 * 〈详细描述做什么〉
 *
 * @author xia zhou
 */
public class AjaxResult<T> {
	private boolean success;
	private String code;
	private String message;
	private T data;

	public AjaxResult() {
		this.success = true;
		this.code = "";
		this.message = "";
	}

	public AjaxResult(T data) {
		this.success = true;
		this.code = "";
		this.message = "";
		this.data = data;
	}

	public AjaxResult(ErrorMessage errorMessage) {
		this(errorMessage.getCode(), errorMessage.getMessage(), null);
	}

	public AjaxResult(String code, String message) {
		this(code, message, null);
	}

	public AjaxResult(String code, String message, T cause) {
		this.success = true;
		this.code = "";
		this.message = "";
		this.success = false;
		this.message = message;
		this.code = code;
		this.data = cause;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}
}


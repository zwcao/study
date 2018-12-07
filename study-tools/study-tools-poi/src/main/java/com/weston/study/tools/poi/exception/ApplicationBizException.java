package com.weston.study.tools.poi.exception;

public class ApplicationBizException extends RuntimeException {

	private static final long serialVersionUID = -1L;

	private String code;

	private String message;
	
	public ApplicationBizException(String message) {
		this.message = message;
	}

	public ApplicationBizException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}

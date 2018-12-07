package com.weston.study.tools.poi.excel.upload.model;

public class HandlerResult {
	
	private ResultState state;
	private String message;

	public ResultState getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}
	
	private HandlerResult(ResultState state) {
		this.state = state;
	}
	
	private HandlerResult(ResultState state, String message) {
		this.state = state;
		this.message = message;
	}

	public static HandlerResult success() {
		return new HandlerResult(ResultState.Success);
	}
	
	public static HandlerResult error(ResultState state, String message) {
		return new HandlerResult(state, message);
	}

	public static enum ResultState {
		Success, Skip, CellValueParseFailure, ValidationFailure, PersistenceFailure
	}
}

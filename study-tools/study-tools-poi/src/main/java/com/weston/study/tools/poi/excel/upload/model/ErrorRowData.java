package com.weston.study.tools.poi.excel.upload.model;

public class ErrorRowData {

	private ViewObjectRow viewObjectRow;

	private String message;
	
	public ErrorRowData(ViewObjectRow viewObjectRow, String message) {
		super();
		this.viewObjectRow = viewObjectRow;
		this.message = message;
	}

	public ViewObjectRow getViewObjectRow() {
		return viewObjectRow;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ErrorRowData [viewObjectRow=" + viewObjectRow + ", message=" + message + "]";
	}

}

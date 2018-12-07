package com.weston.study.tools.poi.excel.upload.model;

public class ExcelCellData {
	private int index;
	private String value;

	public ExcelCellData(int index, String value) {
		this.index = index;
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

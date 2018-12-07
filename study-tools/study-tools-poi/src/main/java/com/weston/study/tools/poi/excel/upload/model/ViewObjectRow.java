package com.weston.study.tools.poi.excel.upload.model;

public class ViewObjectRow {

	public static final ViewObjectRow FINISH_COMMAND = new ViewObjectRow();
	public static final ViewObjectRow EMPTY_COMMAND = new ViewObjectRow();

	public Class<?> clazz;
	public int rowIndex;
	public Object viewObject;
	public ExcelRowData rowData;

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Object getViewObject() {
		return viewObject;
	}

	public void setViewObject(Object viewObject) {
		this.viewObject = viewObject;
	}

	public boolean isEmpty() {
		return this.equals(EMPTY_COMMAND);
	}

	public boolean isFinish() {
		return this.equals(FINISH_COMMAND);
	}

	public ExcelRowData getRowData() {
		return rowData;
	}

	public void setRowData(ExcelRowData rowData) {
		this.rowData = rowData;
	}

	@Override
	public String toString() {
		return "ViewObjectRow [clazz=" + clazz + ", rowIndex=" + rowIndex + ", viewObject=" + viewObject + ", rowData="
				+ rowData + "]";
	}
}

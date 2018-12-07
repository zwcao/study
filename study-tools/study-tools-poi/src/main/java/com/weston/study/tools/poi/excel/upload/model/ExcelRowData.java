package com.weston.study.tools.poi.excel.upload.model;

import java.util.ArrayList;
import java.util.List;

public class ExcelRowData {
	
	private int index;
	
	private List<ExcelCellData> cols = new ArrayList<>();
	
	public ExcelRowData(int index) {
		this.index = index;
	}
	
	public void addColumn(int colIndex, String value) {
		cols.add(new ExcelCellData(colIndex, value));
	}

	public int getIndex() {
		return index;
	}

	public List<ExcelCellData> getCols() {
		return cols;
	}
}

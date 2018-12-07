package com.weston.study.tools.poi.excel.upload.listener;

import com.weston.study.tools.poi.excel.upload.model.ExcelRowData;

public interface ExcelParserListener {

	void onEvent(ExcelParserEvent event);

	/**
	 * excel解析事件
	 * 
	 * @author czw
	 */
	public static class ExcelParserEvent {

		public static final int EVENT_START_READ_DOC = 1 << 0;
		public static final int EVENT_END_READ_DOC = 1 << 1;
		public static final int EVENT_END_READ_ROW = 1 << 2;

		private int eventType;

		private ExcelRowData excelRowData;

		public ExcelParserEvent(int eventType) {
			this.eventType = eventType;
		}

		public ExcelParserEvent(int eventType, ExcelRowData excelRowData) {
			super();
			this.eventType = eventType;
			this.excelRowData = excelRowData;
		}

		public int getEventType() {
			return eventType;
		}

		public void setEventType(int eventType) {
			this.eventType = eventType;
		}

		public ExcelRowData getExcelRowData() {
			return excelRowData;
		}

		public void setExcelRowData(ExcelRowData excelRowData) {
			this.excelRowData = excelRowData;
		}
	}
}

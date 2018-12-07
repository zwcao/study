package com.weston.study.tools.poi.excel.upload.listener;

import com.weston.study.tools.poi.excel.upload.model.HandlerResult;
import com.weston.study.tools.poi.excel.upload.model.ViewObjectRow;

public interface ExcelHandlerListener {

	void onEvent(ExcelHandlerEvent event);

	public static class ExcelHandlerEvent {

		public static final int EVENT_START_READ_DOC = 1 << 0;
		public static final int EVENT_END_READ_DOC = 1 << 1;
		public static final int EVENT_READ_ROW = 1 << 2;
		public static final int EVENT_READ_COMPLETE = 1 << 3;
		public static final int EVENT_READ_ROW_ERROR = 1 << 4;

		private int eventType;
		
		private ViewObjectRow ViewObjectRow;
		
		private HandlerResult result;
		
		public ExcelHandlerEvent(int eventType, ViewObjectRow viewObjectRow, HandlerResult result) {
			this.eventType = eventType;
			this.ViewObjectRow = viewObjectRow;
			this.result = result;
		}

		public int getEventType() {
			return eventType;
		}

		public ViewObjectRow getViewObjectRow() {
			return ViewObjectRow;
		}

		public HandlerResult getResult() {
			return result;
		}
	}

}

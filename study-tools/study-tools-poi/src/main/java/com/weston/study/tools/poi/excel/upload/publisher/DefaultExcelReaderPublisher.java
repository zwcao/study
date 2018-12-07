package com.weston.study.tools.poi.excel.upload.publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.weston.study.tools.poi.excel.support.ExcelObjectReflector;
import com.weston.study.tools.poi.excel.upload.listener.ExcelHandlerListener;
import com.weston.study.tools.poi.excel.upload.listener.ExcelHandlerListener.ExcelHandlerEvent;
import com.weston.study.tools.poi.excel.upload.model.ErrorRowData;
import com.weston.study.tools.poi.excel.upload.model.ExcelRowData;
import com.weston.study.tools.poi.excel.upload.model.HandlerResult;
import com.weston.study.tools.poi.excel.upload.model.ViewObjectRow;
import com.weston.study.tools.poi.excel.upload.model.HandlerResult.ResultState;
import com.weston.study.tools.poi.excel.upload.parser.ExcelReaderContext;
import com.weston.study.tools.poi.exception.ApplicationBizException;

public class DefaultExcelReaderPublisher implements ExcelReaderPublisher {

	private ExcelReaderContext context;

	private BlockingQueue<ViewObjectRow> blockingQueue;

	private List<ExcelHandlerListener> listeners = new ArrayList<>();

	public DefaultExcelReaderPublisher(ExcelReaderContext context, BlockingQueue<ViewObjectRow> blockingQueue) {
		this.blockingQueue = blockingQueue;
		this.context = context;
	}

	@Override
	public void publish(ExcelRowData rowData) {
		if (rowData == null) {
			return;
		}
		ExcelObjectReflector reflector = context.getReflector();
		ViewObjectRow viewObjectRow = new ViewObjectRow();
		viewObjectRow.clazz = reflector.getClazz();
		viewObjectRow.rowIndex = rowData.getIndex();
		viewObjectRow.rowData = rowData;
		try {
			viewObjectRow.viewObject = reflector.trans2Object(rowData);
			this.blockingQueue.put(viewObjectRow);
		} catch (InterruptedException e) {
			context.getErrors().add(new ErrorRowData(viewObjectRow, e.getMessage()));
			sendEvent(new ExcelHandlerEvent(ExcelHandlerEvent.EVENT_READ_ROW_ERROR, viewObjectRow, HandlerResult.error(ResultState.CellValueParseFailure, e.getMessage())));
		} catch (ApplicationBizException e) {
			context.getErrors().add(new ErrorRowData(viewObjectRow, e.getMessage()));
			sendEvent(new ExcelHandlerEvent(ExcelHandlerEvent.EVENT_READ_ROW_ERROR, viewObjectRow, HandlerResult.error(ResultState.CellValueParseFailure, e.getMessage())));
		}
	}

	@Override
	public void onEvent(ExcelParserEvent event) {
		if ((ExcelParserEvent.EVENT_START_READ_DOC & event.getEventType()) != 0) {
			sendEvent(new ExcelHandlerEvent(ExcelHandlerEvent.EVENT_START_READ_DOC, null, null));
		} else if ((ExcelParserEvent.EVENT_END_READ_ROW & event.getEventType()) != 0) {
			publish(event.getExcelRowData());
		} else if ((ExcelParserEvent.EVENT_END_READ_DOC & event.getEventType()) != 0) {
			try {
				blockingQueue.put(ViewObjectRow.FINISH_COMMAND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sendEvent(new ExcelHandlerEvent(ExcelHandlerEvent.EVENT_END_READ_DOC, null, null));
		}
	}

	@Override
	public void addListener(ExcelHandlerListener listener) {
		if(listener == null) {
			return;
		}
		listeners.add(listener);
	}

	private void sendEvent(ExcelHandlerEvent event) {
		listeners.forEach(p -> {
			p.onEvent(event);
		});
	}
}

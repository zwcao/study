package com.weston.study.tools.poi.excel.upload.parser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.weston.study.tools.poi.excel.upload.listener.ExcelParserListener;
import com.weston.study.tools.poi.excel.upload.listener.ExcelParserListener.ExcelParserEvent;
import com.weston.study.tools.poi.excel.upload.model.ExcelRowData;

/**
 * 大文件excel读取(不支持写)
 * 
 * @author czw
 */
public class BigExcelParser {

	private InputStream inputStream;
	private ExcelParserListener listener;

	// 有效的行与列
	private Integer fromRowIndex = null;
	private Set<Integer> columnIndexs = null;

	public BigExcelParser(InputStream inputStream, ExcelParserListener listener) {
		this.inputStream = inputStream;
		this.listener = listener;
	}

	public Integer getFromRowIndex() {
		return fromRowIndex;
	}

	public void setFromRowIndex(Integer fromRowIndex) {
		this.fromRowIndex = fromRowIndex;
	}

	public Set<Integer> getColumnIndexs() {
		return columnIndexs;
	}

	public void setColumnIndexs(Set<Integer> columnIndexs) {
		this.columnIndexs = columnIndexs;
	}

	public void read(String sheetname) throws Exception {
		OPCPackage opcPackage = OPCPackage.open(inputStream);
		XSSFReader reader = new XSSFReader(opcPackage);

		StylesTable stylesTable = reader.getStylesTable();
		SharedStringsTable sharedStringsTable = reader.getSharedStringsTable();

		XMLReader parser = fetchSheetParser(sharedStringsTable, stylesTable);

		XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) reader.getSheetsData();

		InputStream sheetInputStream;
		while (sheetIterator.hasNext()) {
			sheetInputStream = sheetIterator.next();
			if (sheetIterator.getSheetName().equals(sheetname)) {
				InputSource sheetSource = new InputSource(sheetInputStream);
				parser.parse(sheetSource);
				sheetInputStream.close();
				break;
			} else {
				sheetInputStream.close();
			}
		}
	}

	public void read(int sheet) throws Exception {
		OPCPackage opcPackage = OPCPackage.open(inputStream);
		XSSFReader reader = new XSSFReader(opcPackage);

		// sheetId can be rId# or rSheet#
		InputStream sheetInputStream = null;
		StylesTable stylesTable = reader.getStylesTable();
		SharedStringsTable sharedStringsTable = reader.getSharedStringsTable();

		XMLReader parser = fetchSheetParser(sharedStringsTable, stylesTable);

		sheetInputStream = reader.getSheet("rId" + sheet);

		output(sheetInputStream);

		InputSource sheetSource = new InputSource(sheetInputStream);
		parser.parse(sheetSource);
		sheetInputStream.close();
	}

	private void output(InputStream sheetInputStream) throws IOException {
		BufferedInputStream fis = new BufferedInputStream(sheetInputStream);
		BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(
				"/Users/weston/1.workspace/trials/tools/src/main/java/com/weston/tools/excel/upload/demo.xml"));
		byte[] bytes = new byte[1024];
		int length = 0;
		while ((length = fis.read(bytes)) != -1) {
			fos.write(bytes, 0, length);
		}
		fos.flush();
		sheetInputStream.reset();
	}

	/**
	 * 获取解析器
	 *
	 * @param sharedStringsTable
	 * @param stylesTable
	 * @return
	 * @throws SAXException
	 */
	private XMLReader fetchSheetParser(SharedStringsTable sharedStringsTable, StylesTable stylesTable)
			throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new XlsxContentHandler(sharedStringsTable, stylesTable);
		parser.setContentHandler(handler);
		return parser;
	}

	private class XlsxContentHandler extends DefaultHandler {
		private SharedStringsTable sharedStringsTable;
		private StylesTable stylesTable;

		private DataFormatter formatter = new DataFormatter();

		ExcelRowData curRowData = null;

		private Integer cellIndex;
		private String cellType;
		private String cellStyle;
		private String cellVal;

		private String formatString;
		private int formatIndex;

		public XlsxContentHandler(SharedStringsTable sharedStringsTable, StylesTable stylesTable) {
			this.sharedStringsTable = sharedStringsTable;
			this.stylesTable = stylesTable;
		}

		public void startDocument() throws SAXException {
			// 以通知形式告知监听者
			listener.onEvent(new ExcelParserEvent(ExcelParserEvent.EVENT_START_READ_DOC));
		}

		public void endDocument() throws SAXException {
			// 以通知形式告知监听者
			listener.onEvent(new ExcelParserEvent(ExcelParserEvent.EVENT_END_READ_DOC));
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if ("row".equals(qName)) {
				int row = Integer.valueOf(attributes.getValue("r"));
				if (fromRowIndex != null && row < fromRowIndex) {
					return;
				}
				curRowData = new ExcelRowData(row);
			} else if ("c".equals(qName)) {
				if (curRowData == null) {
					return;
				}
				int column = this.getCol(attributes.getValue("r"));
				if (columnIndexs != null && columnIndexs.contains(column)) {
					return;
				}
				cellIndex = column;
				cellType = attributes.getValue("t");
				cellStyle = attributes.getValue("s");
			} else if ("v".equals(qName)) {
				// 单元格值
				cellVal = "";
			} else if ("f".equals(qName)) {
				// 公式
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (curRowData == null) {
				return;
			}
			if ("c".equals(qName)) {
				if(cellIndex == null) {
					return;
				}
				// 获取当前单元格值
				curRowData.addColumn(cellIndex, getCellStringValue());
				cellIndex = null;
			} else if ("row".equals(qName)) {
				// 发布
				listener.onEvent(new ExcelParserEvent(ExcelParserEvent.EVENT_END_READ_ROW, curRowData));
				curRowData = null;
			}
		}

		/**
		 * 获取element的文本数据
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (curRowData == null) {
				return;
			}
			cellVal += new String(ch, start, length);
		}

		private String getCellStringValue() {
			switch (getCellType()) {
			case BOOLEAN:
				char first = cellVal.charAt(0);
				return first == '0' ? "0" : "1";
			case INLINESTR:
			case STRING:
				int idx = Integer.parseInt(cellVal);
				return new XSSFRichTextString(sharedStringsTable.getEntryAt(idx)).toString();
			case ERROR:
				return "\"ERROR:" + cellVal.toString() + '"';
			case DATE:
				return formatter.formatRawCellContents(Double.parseDouble(cellVal), formatIndex, "yyyy-mm-dd hh:mm:ss");
			default:
				return cellVal.trim();
			}
		}

		private CellDataType getCellType() {
			if ("b".equals(cellType)) {
				return CellDataType.BOOLEAN;
			} else if ("s".equals(cellType)) {
				return CellDataType.STRING;
			} else if ("inlineStr".equals(cellType)) {
				return CellDataType.INLINESTR;
			} else if ("str".equals(cellType)) {
				return CellDataType.FORMULA;
			} else if ("e".equals(cellType)) {
				return CellDataType.ERROR;
			} else {
				if (cellStyle != null) {
					int styleIndex = Integer.parseInt(cellStyle);
					XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);

					formatIndex = style.getDataFormat();
					formatString = style.getDataFormatString();
					if (formatString == null) {
						formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
					} else if (DateUtil.isADateFormat(formatIndex, formatString)) {
						return CellDataType.DATE;
					}
				}
				return CellDataType.GENERAL;
			}
		}

		private int getCol(String cell) {
			int col = 0;
			String colIndex = cell.replaceAll("\\d+", "").toUpperCase();
			char[] chars = colIndex.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				col += (i * 26) + (chars[i] - 'A' + 1);
			}
			return col;
		}
	}

	private enum CellDataType {
		GENERAL, BLANK, BOOLEAN, ERROR, FORMULA, INLINESTR, STRING, NUMERIC, DATE
	}
}

package com.weston.study.tools.poi.excel.support;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.weston.study.tools.poi.excel.upload.annotation.ExcelCell;
import com.weston.study.tools.poi.excel.upload.annotation.ExcelWorksheet;
import com.weston.study.tools.poi.excel.upload.model.ExcelCellData;
import com.weston.study.tools.poi.excel.upload.model.ExcelRowData;
import com.weston.study.tools.poi.exception.ApplicationBizException;

public class ExcelObjectReflector {
	private static final ConcurrentHashMap<Class<?>, ExcelObjectReflector> excelObjectMetaHashMap = new ConcurrentHashMap<>();
	private static final ExcelObjectReflector EMPTY_OBJECT = new ExcelObjectReflector(null, null);

	private Class<?> clazz;

	private WorkSeetMeta workSeetMeta;

	private ExcelObjectReflector(Class<?> clazz, WorkSeetMeta workSeetMeta) {
		this.clazz = clazz;
		this.workSeetMeta = workSeetMeta;
	}

	private static ExcelObjectReflector loadClazz(Class<?> clazz) {
		ExcelWorksheet sheetAnno = clazz.getAnnotation(ExcelWorksheet.class);
		if (sheetAnno == null) {
			return EMPTY_OBJECT;
		}
		WorkSeetMeta workSeetMeta = new WorkSeetMeta();
		workSeetMeta.sheetIndex = sheetAnno.sheetIndex();
		workSeetMeta.sheetName = sheetAnno.sheetName();

		// 解析列
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ExcelCell cellAnno = field.getAnnotation(ExcelCell.class);
			if (cellAnno == null) {
				continue;
			}
			CellMeta cellMeta = new CellMeta();
			cellMeta.colIndex = cellAnno.colIndex();
			cellMeta.colName = cellAnno.colName();
			cellMeta.field = field;
			// 待实现
			cellMeta.formatter = null;

			workSeetMeta.cellMetas.add(cellMeta);
		}
		if (workSeetMeta.cellMetas.size() == 0) {
			return EMPTY_OBJECT;
		}
		return new ExcelObjectReflector(clazz, workSeetMeta);
	}

	public static boolean isEmpty(ExcelObjectReflector reflector) {
		return EMPTY_OBJECT.equals(reflector);
	}

	public static ExcelObjectReflector forClass(Class<?> clazz) {
		ExcelObjectReflector cached = excelObjectMetaHashMap.get(clazz);
		if (cached == null) {
			synchronized (ExcelObjectReflector.class) {
				// 用ConcurrentHashMap能保证Node<K,V>[] table 有可见性
				cached = excelObjectMetaHashMap.get(clazz);
				if (cached == null) {
					cached = ExcelObjectReflector.loadClazz(clazz);
					excelObjectMetaHashMap.put(clazz, cached);
				}
			}
		}
		return cached;
	}

	public Object trans2Object(ExcelRowData rowData) {
		if (rowData == null || isEmpty(this)) {
			return null;
		}
		
		try {
			Object viewObject = this.clazz.newInstance();
			for (ExcelCellData cellData : rowData.getCols()) {
				if (StringUtils.isBlank(cellData.getValue())) {
					continue;
				}
				for (CellMeta cellMeta : this.workSeetMeta.cellMetas) {
					if (cellMeta.colIndex == cellData.getIndex()) {
						Field field = cellMeta.field;
						field.setAccessible(true);
						Class fieldType = field.getType();
						if (fieldType == String.class) {
							field.set(viewObject, cellData.getValue());
						} else if (fieldType == Integer.class || fieldType.getName().equals("int")) {
							field.set(viewObject, Integer.valueOf(cellData.getValue()));
						} else if (fieldType == Long.class || fieldType.getName().equals("long")) {
							field.set(viewObject, Long.valueOf(cellData.getValue()));
						} else if (fieldType == Double.class || fieldType.getName().equals("double")) {
							field.set(viewObject, Double.valueOf(cellData.getValue()));
						} else if (fieldType == Float.class || fieldType.getName().equals("float")) {
							field.set(viewObject, Float.valueOf(cellData.getValue()));
						} else if (fieldType == Boolean.class || fieldType.getName().equals("boolean")) {
							field.set(viewObject, Boolean.valueOf(cellData.getValue()));
						} else if (fieldType == BigDecimal.class) {
							field.set(viewObject, new BigDecimal(cellData.getValue()));
						} else if (fieldType == Date.class) {
							field.set(viewObject, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(cellData.getValue()));
						}
					}
				}
			}
			return viewObject;
		} catch (Exception e) {
			throw new ApplicationBizException("parse row as Object error, reaseon:" + e.getMessage());
		}
	}

	public Class getClazz() {
		return clazz;
	}

	public WorkSeetMeta getWorkSeetMeta() {
		return workSeetMeta;
	}

	public static class WorkSeetMeta {
		public int sheetIndex;
		public String sheetName;
		public List<CellMeta> cellMetas = new ArrayList<>();

		public List<Integer> getColumnIndexs() {
			return cellMetas.stream().map(p -> p.colIndex).filter(p -> p > 0).collect(Collectors.toList());
		}
	}

	public static class CellMeta {
		// 列序号
		public int colIndex;
		// 列名
		public String colName;
		// 格式化器
		public Object formatter;
		// 字段
		public Field field;
	}
}

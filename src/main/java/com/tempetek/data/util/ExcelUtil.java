package com.tempetek.data.util;

import com.tempetek.core.util.DateUtil;
import com.tempetek.core.util.ReflectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

	private static HSSFWorkbook hssfWorkbook;
	private static CellStyle fieldStyle; // 标题行样式
	private static Font fieldFont; // 标题行字体
	private static CellStyle headStyle; // 表头行样式
	private static Font headFont; // 表头行字体
	private static CellStyle contentStyle; // 内容行样式
	private static Font contentFont; // 内容行字体

	public static InputStream getExcelStream(InputStream inputStream, String fileName, String[] titles, List<List<Object>> dataList) throws IllegalArgumentException, IOException, IllegalAccessException {
		ExportParam exportParam = ExcelUtil.getTemplate(inputStream, fileName);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		exportParam.setTitles(titles);
		exportParam.setDataList(dataList);
		exportParam.setOutputStream(byteArrayOutputStream);
		exportExcel(exportParam);
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	public static ExportParam getTemplate(InputStream inputStream, String fileName) throws IOException {
		Workbook workbook = getWorkbook(inputStream, fileName);
		Sheet sheet = getSheet(workbook, 0);
		int cells = sheet.getRow(0).getPhysicalNumberOfCells();
		ExportParam exportParam = new ExcelUtil().new ExportParam();
		exportParam.setSheetNames(new String[] { sheet.getSheetName() });

		for (int i = 0; i < 2; i++) {
			Row row = sheet.getRow(i);
			String[] columns = new String[cells];

			for (int j = 0; j < cells; j++) {
				Cell cell = row.getCell(j);

				if (cell == null) {
					cell = row.createCell(j);
				}

				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
				columns[j] = value;
			}

			List<String[]> columnList = new ArrayList<>();
			columnList.add(columns);

			if (i == 0) {
				exportParam.setHeadNameList(columnList);
			} else {
				exportParam.setFieldNameList(columnList);
			}
		}

		return exportParam;
	}

	public static List<Object> importExcel(Class<?> clazz, InputStream inputStream, String fileName) throws IOException, InstantiationException, IllegalAccessException, ParseException {
		List<Object> list = new ArrayList<Object>();
		Workbook workbook = getWorkbook(inputStream, fileName);
		Sheet sheet = getSheet(workbook, 0);
		int rows = sheet.getPhysicalNumberOfRows();
		int cells = sheet.getRow(0).getPhysicalNumberOfCells();
		Map<Integer, String> columuNameMap = new HashMap<>();
		// 获取模板中列名
		Row row1 = sheet.getRow(1);

		for (int i = 0; i < cells; i++) {
			Cell cell = row1.getCell(i);

			if (cell == null) {
				cell = row1.createCell(i);
			}

			cell.setCellType(Cell.CELL_TYPE_STRING);
			String value = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
			columuNameMap.put(i, value);
		}

		// 获取模板数据
		for (int i = 2; i < rows; i++) {// 第一行为标题栏，从第二行开始取数据
			Row row = sheet.getRow(i);
			Object obj = clazz.newInstance();

			for (int j = 0; j < cells; j++) {
				Cell cell = row.getCell(j);

				if (cell == null) {
					cell = row.createCell(j);
				}

				cell.setCellType(Cell.CELL_TYPE_STRING);
				Object value = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
				String fieldName = columuNameMap.get(j);
				Field field = ReflectUtil.getDeclaredField(obj, fieldName);
				Class<?> fieldType = field.getType();

				if (!StringUtils.isEmpty(String.valueOf(value))) {
					if (Long.class.getName().equals(fieldType.getName()) || long.class.getName().equals(fieldType.getName())) {
						value = Long.parseLong(value.toString());
					} else if (Integer.class.getName().equals(fieldType.getName()) || int.class.getName().equals(fieldType.getName())) {
						value = Integer.parseInt(value.toString());
					} else if (Float.class.getName().equals(fieldType.getName()) || float.class.getName().equals(fieldType.getName())) {
						value = Float.parseFloat(value.toString());
					} else if (Double.class.getName().equals(fieldType.getName()) || double.class.getName().equals(fieldType.getName())) {
						value = Double.parseDouble(value.toString());
					} else if (Boolean.class.getName().equals(fieldType.getName()) || boolean.class.getName().equals(fieldType.getName())) {
						value = Boolean.parseBoolean(value.toString());
					} else if (Date.class.getName().equals(fieldType.getName())) {
						value = DateUtil.parse(String.valueOf(value));
					}

					Method method = ReflectUtil.getDeclaredMethod(obj, fieldName, ReflectUtil.SET_METHOD_TYPE);
					ReflectUtil.invokeMethod(obj, method, ReflectUtil.SET_METHOD_TYPE, value);
				}
			}
			list.add(obj);
		}

		return list;
	}

	/**
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @Description: 将Map里的集合对象数据输出Excel数据流
	 */
	public static void exportExcel(ExportParam exportParam) throws IOException, IllegalArgumentException, IllegalAccessException {
		init();
		List<List<Object>> dataList = exportParam.getDataList();
		String[] sheetNames = exportParam.getSheetNames();
		HSSFSheet[] hssfSheets = getSheets(exportParam.getDataList().size(), sheetNames);
		int sheetNum = 0;

		for (List<Object> list : dataList) {
			creatTableHeadRow(exportParam, hssfSheets, sheetNum);
			creatFieldRow(exportParam, hssfSheets, sheetNum);
			String[] fieldNames = exportParam.getFieldNameList().get(sheetNum);
			int rowNum = 2;

			for (Object obj : list) {
				HSSFRow contentRow = hssfSheets[sheetNum].createRow(rowNum);
				contentRow.setHeight((short) 300);
				HSSFCell[] cells = getCells(contentRow, exportParam.getFieldNameList().get(sheetNum).length - 1);
				int cellNum = 0;

				if (fieldNames != null) {
					for (int num = 0; num < fieldNames.length; num++) {
						Method method = ReflectUtil.getDeclaredMethod(obj, fieldNames[num], ReflectUtil.GET_METHOD_TYPE);
						Object value = ReflectUtil.invokeMethod(obj, method, ReflectUtil.GET_METHOD_TYPE, null);

						if (value != null) {
							if ("class java.util.Date".equals(value.getClass().toString())) {
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								value = simpleDateFormat.format(value);
							}
						}

						cells[cellNum].setCellValue(value == null ? "" : value.toString());
						cellNum++;
					}
				}
				rowNum++;
			}

			adjustColumnSize(hssfSheets, sheetNum, fieldNames);
			sheetNum++;
		}

		hssfWorkbook.write(exportParam.getOutputStream());
	}

	/**
	 * @Description: 初始化
	 */
	private static void init() {
		hssfWorkbook = new HSSFWorkbook();
		headStyle = hssfWorkbook.createCellStyle();
		headFont = hssfWorkbook.createFont();
		fieldFont = hssfWorkbook.createFont();
		fieldStyle = hssfWorkbook.createCellStyle();
		contentStyle = hssfWorkbook.createCellStyle();
		contentFont = hssfWorkbook.createFont();

		initHeadCellStyle();
		initHeadFont();
		initFieldCellStyle();
		initFieldFont();
		initContentCellStyle();
		initContentFont();
	}

	private static Workbook getWorkbook(InputStream inputStream, String fileName) throws IOException {
		if (fileName.endsWith(".xls")) {
			return new HSSFWorkbook(inputStream);
		} else if (fileName.endsWith(".xlsx")) {
			return new XSSFWorkbook(inputStream);
		}
		return null;
	}

	private static Sheet getSheet(Workbook workbook, int sheetIndex) {
		return workbook.getSheetAt(sheetIndex);
	}

	/**
	 * @Description: 自动调整列宽
	 */
	private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum, String[] fieldNames) {
		for (int i = 0; i < fieldNames.length + 1; i++) {
			sheets[sheetNum].autoSizeColumn(i, true);
		}
	}

	/**
	 * @Description: 创建表头行(需合并单元格)
	 */
	private static void creatTableHeadRow(ExportParam exportParam, HSSFSheet[] sheets, int sheetNum) {
		HSSFRow headRow = sheets[sheetNum].createRow(0);
		headRow.setHeight((short) 350);

		for (int num = 0, len = exportParam.getHeadNameList().get(sheetNum).length; num < len; num++) {
			HSSFCell headCell = headRow.createCell(num);
			headCell.setCellStyle(headStyle);
			headCell.setCellValue(exportParam.getHeadNameList().get(sheetNum)[num]);
		}
	}

	/**
	 * @Description: 创建属性行(需合并单元格)
	 */
	private static void creatFieldRow(ExportParam exportParam, HSSFSheet[] sheets, int sheetNum) {
		HSSFRow FieldRow = sheets[sheetNum].createRow(1);
		FieldRow.setHeight((short) 350);
		FieldRow.setZeroHeight(true);

		for (int num = 0, len = exportParam.getFieldNameList().get(sheetNum).length; num < len; num++) {
			HSSFCell FieldCell = FieldRow.createCell(num);
			FieldCell.setCellStyle(headStyle);
			FieldCell.setCellValue(exportParam.getFieldNameList().get(sheetNum)[num]);
		}
	}

	/**
	 * @Description: 创建所有的Sheet
	 */
	private static HSSFSheet[] getSheets(int num, String[] names) {
		HSSFSheet[] sheets = new HSSFSheet[num];

		for (int i = 0; i < num; i++) {
			sheets[i] = hssfWorkbook.createSheet(names[i]);
		}
		return sheets;
	}

	/**
	 * @Description: 创建内容行的每一列(附加一列序号)
	 */
	private static HSSFCell[] getCells(HSSFRow contentRow, int num) {
		HSSFCell[] cells = new HSSFCell[num + 1];

		for (int i = 0, len = cells.length; i < len; i++) {
			cells[i] = contentRow.createCell(i);
			cells[i].setCellStyle(contentStyle);
		}
		cells[0].setCellValue(contentRow.getRowNum() - 2);
		return cells;
	}

	/**
	 * @Description: 初始化表头行样式
	 */
	private static void initHeadCellStyle() {
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headStyle.setFont(headFont);
		headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
		headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headStyle.setBorderRight(CellStyle.BORDER_THIN);
		headStyle.setTopBorderColor(IndexedColors.BLUE.index);
		headStyle.setBottomBorderColor(IndexedColors.BLUE.index);
		headStyle.setLeftBorderColor(IndexedColors.BLUE.index);
		headStyle.setRightBorderColor(IndexedColors.BLUE.index);
	}

	/**
	 * @Description: 初始化属性行样式
	 */
	private static void initFieldCellStyle() {
		fieldStyle.setAlignment(CellStyle.ALIGN_CENTER);
		fieldStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		fieldStyle.setFont(headFont);
		fieldStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
		fieldStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		fieldStyle.setBorderBottom(CellStyle.BORDER_THIN);
		fieldStyle.setBorderLeft(CellStyle.BORDER_THIN);
		fieldStyle.setBorderRight(CellStyle.BORDER_THIN);
		fieldStyle.setTopBorderColor(IndexedColors.BLUE.index);
		fieldStyle.setBottomBorderColor(IndexedColors.BLUE.index);
		fieldStyle.setLeftBorderColor(IndexedColors.BLUE.index);
		fieldStyle.setRightBorderColor(IndexedColors.BLUE.index);
	}

	/**
	 * @Description: 初始化内容行样式
	 */
	private static void initContentCellStyle() {
		contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		contentStyle.setFont(contentFont);
		contentStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentStyle.setTopBorderColor(IndexedColors.BLUE.index);
		contentStyle.setBottomBorderColor(IndexedColors.BLUE.index);
		contentStyle.setLeftBorderColor(IndexedColors.BLUE.index);
		contentStyle.setRightBorderColor(IndexedColors.BLUE.index);
		contentStyle.setWrapText(true);
	}

	/**
	 * @Description: 初始化表头行字体
	 */
	private static void initHeadFont() {
		headFont.setFontName("宋体");
		headFont.setFontHeightInPoints((short) 10);
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setCharSet(Font.DEFAULT_CHARSET);
		headFont.setColor(IndexedColors.BLUE_GREY.index);
	}

	/**
	 * @Description: 初始化属性行字体
	 */
	private static void initFieldFont() {
		fieldFont.setFontName("宋体");
		fieldFont.setFontHeightInPoints((short) 10);
		fieldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fieldFont.setCharSet(Font.DEFAULT_CHARSET);
		fieldFont.setColor(IndexedColors.BLUE_GREY.index);
	}

	/**
	 * @Description: 初始化内容行字体
	 */
	private static void initContentFont() {
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short) 10);
		contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		contentFont.setCharSet(Font.DEFAULT_CHARSET);
		contentFont.setColor(IndexedColors.BLUE_GREY.index);
	}

	class ExportParam {
		private List<List<Object>> dataList;
		private String[] titles;
		private String[] sheetNames;
		private List<String[]> headNameList;
		private List<String[]> fieldNameList;
		private OutputStream outputStream;

		public List<List<Object>> getDataList() {
			return dataList;
		}

		public void setDataList(List<List<Object>> dataList) {
			this.dataList = dataList;
		}

		public String[] getTitles() {
			return titles;
		}

		public void setTitles(String[] titles) {
			this.titles = titles;
		}

		public String[] getSheetNames() {
			return sheetNames;
		}

		public void setSheetNames(String[] sheetNames) {
			this.sheetNames = sheetNames;
		}

		public List<String[]> getHeadNameList() {
			return headNameList;
		}

		public void setHeadNameList(List<String[]> headNameList) {
			this.headNameList = headNameList;
		}

		public List<String[]> getFieldNameList() {
			return fieldNameList;
		}

		public void setFieldNameList(List<String[]> fieldNameList) {
			this.fieldNameList = fieldNameList;
		}

		public OutputStream getOutputStream() {
			return outputStream;
		}

		public void setOutputStream(OutputStream outputStream) {
			this.outputStream = outputStream;
		}

	}
}
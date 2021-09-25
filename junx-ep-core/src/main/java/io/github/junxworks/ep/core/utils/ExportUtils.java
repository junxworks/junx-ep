/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  ExportUtils.java   
 * @Package io.github.junxworks.ep.core.utils   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:36   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import io.github.junxworks.junx.core.util.DateUtils;

/**
 * Excel导出工具类
 *
 * @ClassName:  ExportUtils
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public class ExportUtils {

	/** 常量 EXCEL_SHEET_MAXSIZE. */
	//设置一个sheet最大行数
	private static final int EXCEL_SHEET_MAXSIZE = 50001;

	/**
	 * Download excel.
	 *
	 * @param response the response
	 * @param objList the obj list
	 * @param fileName the file name
	 * @param columnNames the column names
	 * @param keys the keys
	 * @throws Exception the exception
	 */
	public static void downloadExcel(HttpServletResponse response, List<?> objList, String fileName, String[] columnNames, String[] keys) throws Exception {
		List<Map<String, Object>> list = createExcelRecord(objList, keys);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ExportUtils.createWorkBook(fileName, list, keys, columnNames).write(os);
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		fileName = fileName + DateUtils.formatDateTime(new Date());
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	/**
	 * Creates the excel record.
	 *
	 * @param projects the projects
	 * @param keys the keys
	 * @return the list
	 * @throws Exception the exception
	 */
	private static List<Map<String, Object>> createExcelRecord(List<?> projects, String[] keys) throws Exception {
		List<Map<String, Object>> listmap = new ArrayList<>();
		for (Object project : projects) {
			Map<String, Object> mapValue = new HashMap<>();
			for (String key : keys) {
				mapValue.put(key, project.getClass().getMethod("get" + (key.substring(0, 1).toUpperCase() + key.substring(1))).invoke(project));
			}
			listmap.add(mapValue);
		}
		return listmap;
	}

	/**
	 * Creates the work book.
	 *
	 * @param sheetName the sheet name
	 * @param list the list
	 * @param keys the keys
	 * @param columnNames the column names
	 * @return the workbook
	 */
	public static Workbook createWorkBook(String sheetName, List<Map<String, Object>> list, String[] keys, String columnNames[]) {
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建两种单元格格式
		CellStyle cs = ExportUtils.createCellStyleTocolumn(wb);
		CellStyle stringCs = ExportUtils.createCellStyleToValue(wb);
		CellStyle numberCs = ExportUtils.createCellStyleToValue(wb);
		numberCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		CellStyle dateCs = ExportUtils.createCellStyleToValue(wb);
		dateCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		//创建第一个sheet（页）
		short sheetNameNum = 1;
		Sheet sheet = ExportUtils.createSheet(wb, cs, sheetName + sheetNameNum, keys, columnNames);
		//设置每行每列的值
		//行数
		int rowNum = 1;
		for (Map<String, Object> map : list) {
			if (rowNum == ExportUtils.EXCEL_SHEET_MAXSIZE) {//如果超出指定值重新创建一个sheet
				sheetNameNum++;
				sheet = ExportUtils.createSheet(wb, cs, sheetName + sheetNameNum, keys, columnNames);
				rowNum = 1;
			}
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow(rowNum);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				Cell cell = row1.createCell(j);
				Object value = map.get(keys[j]);
				if (value != null) {
					if (value instanceof Integer || value instanceof Long) {
						cell.setCellValue(value.toString());
						cell.setCellStyle(stringCs);
					} else if (value instanceof Number) {
						cell.setCellValue(Double.parseDouble(value.toString()));
						cell.setCellStyle(numberCs);
					} else if (value instanceof Date) {//日期格式处理
						cell.setCellValue((Date) value);
						cell.setCellStyle(dateCs);
					} else {
						cell.setCellValue(value.toString());
						cell.setCellStyle(stringCs);
					}
				} else {
					cell.setCellValue(" ");
					cell.setCellStyle(stringCs);
				}
			}
			rowNum++;
		}
		return wb;
	}

	/**
	 * Creates the cell style tocolumn.
	 *
	 * @param wb the wb
	 * @return the cell style
	 */
	private static CellStyle createCellStyleTocolumn(Workbook wb) {
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBold(true);
		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(BorderStyle.THIN);
		cs.setBorderRight(BorderStyle.THIN);
		cs.setBorderTop(BorderStyle.THIN);
		cs.setBorderBottom(BorderStyle.THIN);
		cs.setAlignment(HorizontalAlignment.CENTER);
		return cs;
	}

	/**
	 * Creates the cell style to value.
	 *
	 * @param wb the wb
	 * @return the cell style
	 */
	private static CellStyle createCellStyleToValue(Workbook wb) {
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		// 创建第二种字体样式（用于值）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		// 设置第二种单元格的样式（用于值）
		cs.setFont(f);
		cs.setBorderLeft(BorderStyle.THIN);
		cs.setBorderRight(BorderStyle.THIN);
		cs.setBorderTop(BorderStyle.THIN);
		cs.setBorderBottom(BorderStyle.THIN);
		cs.setAlignment(HorizontalAlignment.CENTER);
		return cs;
	}

	/**
	 * Creates the sheet.
	 *
	 * @param wb the wb
	 * @param cs the cs
	 * @param sheetName the sheet name
	 * @param keys the keys
	 * @param columnNames the column names
	 * @return the sheet
	 */
	private static Sheet createSheet(Workbook wb, CellStyle cs, String sheetName, String[] keys, String columnNames[]) {
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet(sheetName);
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}
		// 创建第一行
		Row row = sheet.createRow((short) 0);
		//设置列名
		for (int i = 0; i < columnNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		return sheet;
	}

}

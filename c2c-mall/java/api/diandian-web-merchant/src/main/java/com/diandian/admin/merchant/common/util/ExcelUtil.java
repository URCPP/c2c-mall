package com.diandian.admin.merchant.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

/***
 * Excel服务
 * @author ltg
 *
 */
@Slf4j
public class ExcelUtil {

	public HSSFSheet sheet ;
	public HSSFWorkbook book;
	public HSSFCellStyle headStyle;
	public HSSFCellStyle boldStyle;
	/**
	 * 换行样式
	 */
	public HSSFCellStyle lineStyle;

	public HSSFCellStyle style;
	public HSSFCellStyle leftStyle;
	//蓝色
	public HSSFCellStyle blueStyle;

	//默认字大小
	public short defaultFontSize = 10;

	public ExcelUtil() {
		init(defaultFontSize);
	}
	public ExcelUtil(short _defaultFontSize) {
		init(_defaultFontSize);
	}
	public void init(short defaultFontSize) {
		book = new HSSFWorkbook();
		//列头样式
		headStyle = book.createCellStyle();

		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//上下居中
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//字体大小
		HSSFFont font = book.createFont();
		font.setFontHeightInPoints((short)10);
		/**设置粗体*/
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headStyle.setFont(font);
		this.setBorder(headStyle);
		//粗体行样式
		boldStyle = book.createCellStyle();
		boldStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		boldStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font = book.createFont();
		font.setFontHeightInPoints((short)defaultFontSize);
		/**设置粗体*/
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldStyle.setFont(font);
		this.setBorder(boldStyle);
		//行样式
		style = book.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font = book.createFont();
		font.setFontHeightInPoints((short)defaultFontSize);
//		font.setColor(HSSFColor.BLUE.index);
		style.setFont(font);
		this.setBorder(style);
		//换行样式
		lineStyle = book.createCellStyle();
		lineStyle.setWrapText(true);
		lineStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		lineStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font = book.createFont();
		font.setFontHeightInPoints((short)defaultFontSize);
		lineStyle.setFont(font);
		this.setBorder(lineStyle);
		//行样式(左)
		leftStyle = book.createCellStyle();
		leftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		leftStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font = book.createFont();
		font.setFontHeightInPoints((short)defaultFontSize);
		leftStyle.setFont(font);
		this.setBorder(leftStyle);

		blueStyle = book.createCellStyle();
		blueStyle.setWrapText(true);
		blueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		blueStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font = book.createFont();
		font.setFontHeightInPoints((short)defaultFontSize);
		font.setColor(HSSFColor.BLUE.index);
		blueStyle.setFont(font);
		this.setBorder(blueStyle);
	}
	/**
	 * 设置边框
	 * @param cellStyle
	 */
	private void setBorder(HSSFCellStyle cellStyle){
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	}
	/**
	 * 获取粗体样式
	 * @param book
	 * @param fontSize 字大小
	 * @return
	 */
	public HSSFCellStyle getBoleStyle(HSSFWorkbook book,int fontSize){
		HSSFCellStyle boldStyle = book.createCellStyle();
		boldStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		boldStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = book.createFont();
		font = book.createFont();
		font.setFontHeightInPoints((short)fontSize);
		/**设置粗体*/
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldStyle.setFont(font);
		boldStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		boldStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		boldStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		boldStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		return boldStyle;
	}
	/**
	 * 创建头行
	 * @param rowIndex 行索引
	 * @param rowHeight 行高
	 * @param maxColIndex 最大列索引
	 * @param title  标题
	 * @param fontPoints 字大小
	 */
	public HSSFRow createHead(int rowIndex,int rowHeight,int maxColIndex,String title,int fontPoints){
		HSSFRow row = sheet.createRow(rowIndex);
		/**Region(start行,(short) start列,end行,(short)end列)*/
		CellRangeAddress cellRangeAddress = new   CellRangeAddress(rowIndex,(short)0,rowIndex,(short)maxColIndex);
		HSSFCellStyle titleStyle = book.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = book.createFont();
		/**设置粗体*/
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)fontPoints);
		titleStyle.setFont(font);
		createCell(row, 0, title, titleStyle, cellRangeAddress);
		row.setHeight((short)rowHeight);
		return row;
	}
	/**
	 * 创建 合并行
	 * @param rowIndex 行索引
	 * @param rowHeight 行高
	 * @param maxColIndex 最大列索引
	 * @param title  标题
	 * @param fontPoints 字大小
	 * @param fontBold 是否粗体
	 * @param align 对齐方式
	 * HSSFCellStyle.VERTICAL_BOTTOM 不一样
	 */
	public HSSFRow createUniteRow(int rowIndex,int rowHeight,int maxColIndex,String title,
								int fontPoints,boolean fontBold,short align){
		return this.createUniteRow(rowIndex, rowHeight, maxColIndex, title, fontPoints, fontBold, align, false);
	}
	/**
	 * 创建 合并行
	 * @param rowIndex 行索引
	 * @param rowHeight 行高
	 * @param maxColIndex 最大列索引
	 * @param title  标题
	 * @param fontPoints 字大小
	 * @param fontBold 是否粗体
	 * @param align 对齐方式
	 * @param border 是否有边框
	 * HSSFCellStyle.VERTICAL_BOTTOM 不一样
	 */
	public HSSFRow createUniteRow(int rowIndex,int rowHeight,int maxColIndex,String title,
									int fontPoints,boolean fontBold,short align,boolean border){
		HSSFRow row = sheet.createRow(rowIndex);
		/**Region(start行,(short) start列,end行,(short)end列)*/
		CellRangeAddress cellRangeAddress = new  CellRangeAddress(rowIndex,(short)0,rowIndex,(short)maxColIndex);
		HSSFCellStyle titleStyle = book.createCellStyle();
		titleStyle.setAlignment(align);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
		HSSFFont font = book.createFont();
		/**设置粗体*/
		if(fontBold){
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		font.setFontHeightInPoints((short)fontPoints);
		titleStyle.setFont(font);
		/***设置边框*/
		if(border){
			this.setBorder(titleStyle);
			createCell(row, 0, title, titleStyle, cellRangeAddress);
			for(int i=1;i<= maxColIndex; i++){
				createCell(row, i, "", titleStyle);
			}
		}else{
			createCell(row, 0, title, titleStyle, cellRangeAddress);
		}
		row.setHeight((short)rowHeight);
		return row;
	}
	/**
	 * 创建单元格
	 * @param row 行
	 * @param column 列索引
	 * @param text  内容
	 */
	public void createCell(HSSFRow row,int column,String text){
		this.createCell(row, column, text,null,null);
	}
	/**
	 * 创建单元格
	 * @param row 行
	 * @param column 列索引
	 * @param num  内容是数字
	 * @param style 单元格样式
	 */
	public void createCell(HSSFRow row,int column,String text,HSSFCellStyle _style){
		this.createCell(row, column, text,_style,null);
	}
	/**
	 * 创建单元格
	 * @param row 行
	 * @param column 列索引
	 * @param text  内容
	 * @param style 单元格样式
	 * @param region 合并单元格的区域
	 */
	public void createCell(HSSFRow row,int column,String text,HSSFCellStyle _style,CellRangeAddress cellRangeAddress){
		HSSFCell cell = row.createCell((short)column);
		cell.setCellValue(text);
		if(_style != null){
			cell.setCellStyle(_style);
		}else{
			cell.setCellStyle(this.style);
		}
		if(cellRangeAddress != null){
			sheet.addMergedRegion(cellRangeAddress);
		}
	}
	/**
	 * 创建单元格，支持文本\r\n换行
	 * @param row 行
	 * @param column 列索引
	 * @param text  内容
	 * @param style 单元格样式
	 * @param region 合并单元格的区域
	 */
	public void createRichCell(HSSFRow row,int column,String text,HSSFCellStyle _style,CellRangeAddress cellRangeAddress){
		HSSFCell cell = row.createCell((short)column);
		if(_style != null){
			HSSFCellStyle s = _style;
			s.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
			s.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			cell.setCellStyle(s);
		}else{
			cell.setCellStyle(this.style);
		}
		cell.setCellValue(new HSSFRichTextString(text));
		if(cellRangeAddress != null){
			sheet.addMergedRegion(cellRangeAddress);
		}
	}
	public static String getCellValue(HSSFRow row,int cellIndex){
		String str = "";
		HSSFCell cell = null;
		try {
			cell = row.getCell((short)cellIndex);
			if(cell == null){
				return "";
			}
			HSSFRichTextString richText = cell.getRichStringCellValue();
			if(richText == null){
				str = "";
			}else{
				str = richText.toString();
			}
			return str.trim();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Double d = cell.getNumericCellValue();
				return d.toString();
			} catch (Exception e1) {
				e1.printStackTrace();
				return "";
			}
		}
	}
	public static double getCellDoubleValue(HSSFRow row,int cellIndex){
		HSSFCell cell = null;
		try {
			cell = row.getCell((short)cellIndex);
			if(cell == null){
				return 0.0;
			}
			return cell.getNumericCellValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	public static int getCellIntValue(HSSFRow row,int cellIndex){
		try {
			Double d = getCellDoubleValue(row,cellIndex);
			String str = d.toString();
			str = str.substring(0, str.length()-2);
			log.info(str);
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}

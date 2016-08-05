package demo.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import demo.bean.User;
import demo.util.UserUtils;

public class ExcelAction {
	private InputStream excelFile;

	private String downloadFileName;

	public ExcelAction() {
	}

	public String getDownloadFileName() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd ");
		this.downloadFileName = (sf.format(new Date()).toString()) + "人员列表.xls";
		try {
			this.downloadFileName = new String(this.downloadFileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this.downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	public String export() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		List<User> dataList = UserUtils.genUsers();
		HSSFWorkbook workbook = exportExcel(dataList);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		workbook.write(output);
		byte[] ba = output.toByteArray();
		excelFile = new ByteArrayInputStream(ba);
		output.flush();
		output.close();
		return "excel";
	}

	public HSSFWorkbook exportExcel(List dataList) throws Exception {
		HSSFWorkbook workbook = null;
		try {
			// 这里的数据即时你要从后台取得的数据
			// 创建工作簿实例
			workbook = new HSSFWorkbook();
			// 创建工作表实例
			HSSFSheet sheet = workbook.createSheet("TscExcel");
			// 设置列宽
			this.setSheetColumnWidth(sheet);
			// 获取样式
			HSSFCellStyle style = this.createTitleStyle(workbook);
			//
			if (dataList != null && dataList.size() > 0) {
				// 创建第一行标题,标题名字的本地信息通过resources从资源文件中获取
				HSSFRow row = sheet.createRow((short) 0);// 建立新行

				this.createCell(row, 0, style, HSSFCell.CELL_TYPE_STRING, "序号");
				this.createCell(row, 1, style, HSSFCell.CELL_TYPE_STRING, "姓名");
				this.createCell(row, 2, style, HSSFCell.CELL_TYPE_STRING, "密码");
//				this.createCell(row, 3, style, HSSFCell.CELL_TYPE_STRING, "建设单位");
//				this.createCell(row, 4, style, HSSFCell.CELL_TYPE_STRING, "牵头责任单位");
//				this.createCell(row, 5, style, HSSFCell.CELL_TYPE_STRING, "建设起始年");
//				this.createCell(row, 6, style, HSSFCell.CELL_TYPE_STRING, "总投资（万元）");
//				this.createCell(row, 7, style, HSSFCell.CELL_TYPE_STRING, "申报时间");

				// 给excel填充数据
				for (int i = 0; i < dataList.size(); i++) {
					// 将dataList里面的数据取出来，假设这里取出来的是Model,也就是某个javaBean的意思啦
					User model = (User) dataList.get(i);
					HSSFRow row1 = sheet.createRow((short) (i + 1));// 建立新行
					this.createCell(row1, 0, style, HSSFCell.CELL_TYPE_STRING, i + 1);
					if (model.getUsername() != null)
						this.createCell(row1, 1, style, HSSFCell.CELL_TYPE_STRING, model.getUsername());
					if (model.getPassword() != null)
						this.createCell(row1, 2, style, HSSFCell.CELL_TYPE_STRING, model.getPassword());
//					if (model.getXmdw() != null)
//						this.createCell(row1, 3, style, HSSFCell.CELL_TYPE_STRING, model.getXmdw());
//					if (model.getZrbm() != null)
//						this.createCell(row1, 4, style, HSSFCell.CELL_TYPE_STRING, model.getZrbm());
//					if (model.getJsqsn() != null)
//						this.createCell(row1, 5, style, HSSFCell.CELL_TYPE_STRING, model.getJsqsn());
//					if (model.getZtz() != null)
//						this.createCell(row1, 6, style, HSSFCell.CELL_TYPE_STRING, model.getZtz());
//					if (model.getSbsj() != null)
//						this.createCell(row1, 7, style, HSSFCell.CELL_TYPE_STRING, model.getSbsj());
				}
			} else {
				this.createCell(sheet.createRow(0), 0, style, HSSFCell.CELL_TYPE_STRING, "查无资料");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private void setSheetColumnWidth(HSSFSheet sheet) {
		// 根据你数据里面的记录有多少列，就设置多少列
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 3000);
//		sheet.setColumnWidth(3, 8000);
//		sheet.setColumnWidth(4, 8000);
//		sheet.setColumnWidth(5, 5000);
//		sheet.setColumnWidth(6, 5000);
//		sheet.setColumnWidth(7, 5000);
	}

	// 设置excel的title样式
	private HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
		HSSFFont boldFont = wb.createFont();
		boldFont.setFontHeight((short) 200);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(boldFont);
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00"));
		return style;
	}

	// 创建Excel单元格
	private void createCell(HSSFRow row, int column, HSSFCellStyle style, int cellType, Object value) {
		HSSFCell cell = row.createCell(column);
		if (style != null) {
			cell.setCellStyle(style);
		}
		switch (cellType) {
		case HSSFCell.CELL_TYPE_BLANK: {
		}
			break;
		case HSSFCell.CELL_TYPE_STRING: {
			cell.setCellValue(value.toString());
		}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.parseDouble(value.toString()));
		}
			break;
		default:
			break;
		}
	}
}

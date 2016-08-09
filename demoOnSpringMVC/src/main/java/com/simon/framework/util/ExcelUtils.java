package com.simon.framework.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.simon.framework.base.BaseRequest;

/**
 * 
 * @ClassName: ExcelUtils <br>
 * @Description: Excel操作Util类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class ExcelUtils {
	public static Log log = LogFactory.getLog(ExcelUtils.class);

	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	public static void exportExcel(String fileName, BaseRequest baseReq, List<?> ts, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, IOException {

		ServletOutputStream out = null;
		List<Map<String, String>> rns = new ObjectMapper().readValue(baseReq.getRowNames(), new ArrayList().getClass());
		HSSFWorkbook hb = new HSSFWorkbook();
		HSSFSheet sheet = hb.createSheet("sheet");

		// 设置excel标题
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < rns.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(rns.get(i).get("title"));
		}

		// 设置excel内容
		for (int i = 0; i < ts.size(); i++) {
			row = sheet.createRow(i + 1);
			Object t = ts.get(i);
			for (int j = 0; j < rns.size(); j++) {
				HSSFCell cell = row.createCell(j);
				String field = rns.get(j).get("field");
				String cellvalue = (String) ReflectionUtils.getFieldValue(t, field);
				cell.setCellValue(cellvalue);
			}
		}

		// 防止文件名乱码
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1"));
		response.setContentType("application/vnd.ms-excel");

		// 文件流输出
		out = response.getOutputStream();
		hb.write(out);
		out.flush();
	}
}

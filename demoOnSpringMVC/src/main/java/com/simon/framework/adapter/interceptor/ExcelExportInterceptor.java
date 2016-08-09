/**
 * Title: ExcelExportInterceptor.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.adapter.interceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.simon.framework.base.BaseRequest;
import com.simon.framework.util.ExcelUtils;
import com.simon.framework.util.LogUtil;
import com.simon.framework.util.ObjectUtils;

/**
 * @ClassName: ExcelExportInterceptor <br>
 * @Description: 日志导出拦截器 <br>
 */
@Aspect
public class ExcelExportInterceptor {

	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(ExcelExportInterceptor.class);

	/**
	 * 
	 * <p>
	 * Discription:[page的切面定义]
	 * </p>
	 * 
	 */
	@Pointcut("execution(* com.simon.ibms..controller.*.page*(..))")
	public void export() {
	}

	/**
	 * 
	 * <p>
	 * Discription:[export()切面下的excel导出]
	 * </p>
	 * 
	 * @param jp
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@SuppressWarnings("rawtypes")
	@AfterReturning(value = "export()", returning = "result")
	public void doExport(JoinPoint jp, Map result) {

		BaseRequest baseReq = null;
		// 取出当前线程的response
		try {
			HttpServletResponse response = ThreadLocalResponse.get();
			Object[] objs = jp.getArgs();
			for (Object obj : objs) {
				if (obj instanceof BaseRequest) {
					baseReq = (BaseRequest) obj;
				} else if (obj instanceof HttpServletResponse) {
					response = (HttpServletResponse) obj;
				}
			}
			// 判断是否是导出excel操作
			if (baseReq.getExport() == 1) {
				List list = null;
				for (Object object : result.values()) {
					if (object instanceof List) {
						list = (List) object;
					}
				}
				if (ObjectUtils.isNotEmpty(response) && ObjectUtils.isNotEmpty(baseReq)) {
					try {
						ExcelUtils.exportExcel(baseReq.getTitle() + ".xls", baseReq, list, response);
					} catch (IOException e) {
						LogUtil.error(log, e.getMessage());
					}
					LogUtil.info(log, "excel导出成功");
				} else {
					LogUtil.error(log, "excel导出失败");
				}

			}

		} catch (Exception e) {
			LogUtil.error(log, e.getMessage());
		} finally {
			ThreadLocalResponse.remove();
		}
	}
}

/**
 * Title: LogController.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.log.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simon.framework.base.BaseController;
import com.simon.framework.sys.log.beans.LogType;
import com.simon.framework.sys.log.service.LoggerService;
import com.simon.framework.util.LogUtil;

/**
 * @ClassName: LogController <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

@Controller("logController")
@RequestMapping("/ibms/log")
public class LogController extends BaseController {
	/** The logger. */
	private static final Log LOG = LogFactory.getLog(LogController.class);

	/** The loggerService. */
	@Autowired
	private LoggerService loggerService;

	/**
	 * 
	 * <p>
	 * Discription:[日志页面索引]
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping("/show")
	public String listLogs() {
		LogUtil.info(LOG, "日志展示页面被访问");
		return "framework/syslog/list";
	}

	/**
	 * 
	 * <p>
	 * Discription:[通过条件组合获取日志记录]
	 * </p>
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/page")
	public @ResponseBody Map<String, Object> pageLogs(HttpServletRequest request, String page, String rows, String sort,
			String order, String username, String startDate, String endDate) throws Exception {

		List<LogType> logs = loggerService.getLogs(sort, order, username, startDate, endDate,
				new RowBounds((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows)));

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("total", loggerService.getLogsSize(username, startDate, endDate));
		result.put("rows", logs);

		return result;
	}

}

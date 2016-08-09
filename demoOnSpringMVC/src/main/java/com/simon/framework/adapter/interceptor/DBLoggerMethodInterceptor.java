/**
 * Title: DBMethodInterceptor.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.adapter.interceptor;

import java.text.MessageFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.simon.framework.config.SysConstants;
import com.simon.framework.sys.authorize.security.SecurityContextUtil;
import com.simon.framework.sys.log.beans.LogType;
import com.simon.framework.sys.log.service.LoggerService;
import com.simon.framework.util.DateUtil;
import com.simon.framework.util.LogUtil;

/**
 * @ClassName: DBMethodInterceptor <br>
 * @Description: 日志切面记录拦截器 <br>
 */
@Aspect
public class DBLoggerMethodInterceptor {

	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(DBLoggerMethodInterceptor.class);

	/**
	 * LoggerService
	 */
	private LoggerService loggerService;

	/**
	 * Request
	 */
	private HttpServletRequest request;

	/**
	 * @param loggerService
	 *            the loggerService to set
	 */
	public void setLoggerService(LoggerService loggerService) {
		this.loggerService = loggerService;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	@Autowired
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 
	 * <p>
	 * Discription:[insert和add的切面定义]
	 * </p>
	 * 
	 */
	@Pointcut("execution(public * com.simon.ibms..controller.*.insert*(..))||execution(* com.simon.ibms..controller.*.add*(..))")
	public void insert() {
	}

	/**
	 * 
	 * <p>
	 * Discription:[update和edit的切面定义]
	 * </p>
	 * 
	 */
	@Pointcut("execution(* com.simon.ibms..controller.*.update*(..))||execution(* com.simon.ibms..controller.*.edit*(..))")
	public void udpate() {
	}

	/**
	 * 
	 * <p>
	 * Discription:[remove和delete的切面定义]
	 * </p>
	 * 
	 */
	@Pointcut("execution(* com.simon.ibms..controller.*.remove*(..))||execution(* com.simon.ibms..controller.*.delete*(..))")
	public void remove() {
	}

	/**
	 * 
	 * <p>
	 * Discription:[insert()切面下的日志入库操作]
	 * </p>
	 * 
	 * @param jp
	 */
	@AfterReturning("insert()")
	public void doInsert(JoinPoint jp) {
		String target = request.getSession().getAttribute(SysConstants.SUB_SYSTEM_KEY).toString();
		String ip = request.getRemoteAddr();
		this.logIntoDB(target, ip, "新增");
	}

	/**
	 * 
	 * <p>
	 * Discription:[update()切面下的日志入库操作]
	 * </p>
	 * 
	 * @param jp
	 */
	@AfterReturning("udpate()")
	public void doUpdate(JoinPoint jp) {

		String target = request.getSession().getAttribute(SysConstants.SUB_SYSTEM_KEY).toString();
		String ip = request.getRemoteAddr();
		this.logIntoDB(target, ip, "更新");

	}

	/**
	 * 
	 * <p>
	 * Discription:[remove()切面下的日志入库操作]
	 * </p>
	 * 
	 * @param jp
	 */
	@AfterReturning("remove()")
	public void doRemove(JoinPoint jp) {

		String target = request.getSession().getAttribute(SysConstants.SUB_SYSTEM_KEY).toString();
		String ip = request.getRemoteAddr();
		this.logIntoDB(target, ip, "删除");

	}

	/**
	 * 执行日志入库操作
	 * 
	 * @param targetObj
	 * @param arguments
	 * @param ip
	 * @param operateType
	 */
	public void logIntoDB(String targetObj, String ip, String operateType) {

		String description = MessageFormat.format("[{0}]操作", operateType);

		LogType logType = new LogType();

		String username = SecurityContextUtil.getUserName();
		String currentDate = DateUtil.date2String(new Date(), DateUtil.PATTERN_STANDARD);

		logType.setUsername(username);
		logType.setOperateObject(targetObj);
		logType.setOperateDesc(description);
		logType.setOperateTime(currentDate);
		logType.setOperateIp(ip);

		loggerService.insertLog(logType);

		/**
		 * 0-用户名 1-时间 2-对象 3-操作类型 4-参数 5-IP
		 */
		String operateLogDescription = "用户[{0}]在[{1}]时间对[{2}]进行了[{3}]操作,用户IP[{4}]";
		LogUtil.info(log, operateLogDescription, username, currentDate, targetObj, operateType, ip);
	}

}

/**
 * Title: LoggerService.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.log.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;

import com.simon.framework.exception.ServiceBusinessException;
import com.simon.framework.sys.log.beans.LogType;
import com.simon.framework.sys.log.mapper.LoggerMapper;
import com.simon.framework.util.DateUtil;
import com.simon.framework.util.LogUtil;
import com.simon.framework.util.StringUtils;

/**
 * @ClassName: LoggerService <br>
 * @Description: 日志服务的实现 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class LoggerServiceImpl implements LoggerService {
	/** The log. */
	private static final Log LOG = LogFactory.getLog(LoggerServiceImpl.class);

	/** The loggerMapper. */
	private LoggerMapper loggerMapper;

	/**
	 * <p>
	 * Discription:[Get loggerMapper]
	 * </p>
	 * 
	 * @return LoggerMapper loggerMapper.
	 */
	public LoggerMapper getLoggerMapper() {
		return loggerMapper;
	}

	/**
	 * <p>
	 * Discription:[Set loggerMapper]
	 * </p>
	 * 
	 * @param loggerMapper
	 *            The loggerMapper to set.
	 */
	public void setLoggerMapper(LoggerMapper loggerMapper) {
		this.loggerMapper = loggerMapper;
	}

	/**
	 * 
	 * <p>
	 * Discription:[通过条件获取日志记录总奥数]
	 * </p>
	 * 
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getLogsSize(String username, String startDate, String endDate) {
		if (StringUtils.isEmpty(username)) {
			username = null;
		}

		if (!StringUtils.isEmpty(startDate)) {
			Date startDateTime = DateUtil.string2Date(startDate + " 00:00:00", DateUtil.PATTERN_STANDARD);
			startDate = DateUtil.date2String(startDateTime, DateUtil.PATTERN_STANDARD);
		} else {
			startDate = null;
		}

		if (!StringUtils.isEmpty(endDate)) {
			Date endDateTime = DateUtil.string2Date(endDate + " 23:59:59", DateUtil.PATTERN_STANDARD);
			endDate = DateUtil.date2String(endDateTime, DateUtil.PATTERN_STANDARD);
		} else {
			endDate = null;
		}

		return loggerMapper.getLogsSize(username, startDate, endDate);
	}

	/**
	 * 
	 * <p>
	 * Discription:[通过条件获得日志记录]
	 * </p>
	 * 
	 * @param sort
	 * @param order
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @param rb
	 * @return
	 * @throws ServiceBusinessException
	 */
	public List<LogType> getLogs(String sort, String order, String username, String startDate, String endDate,
			RowBounds rb) throws ServiceBusinessException {

		order = StringUtils.isEmpty(order) ? "DESC" : order.toUpperCase();

		StringBuilder logString = new StringBuilder("获取范围为[").append(rb.getOffset()).append(",")
				.append(rb.getOffset() + rb.getLimit()).append("]的LOG记录");

		if (!StringUtils.isEmpty(username)) {
			logString.append(",查询用户[").append(username).append("]的日志记录");
		} else {
			username = null;
		}

		if (!StringUtils.isEmpty(startDate)) {
			try {
				Date startDateTime = DateUtil.string2Date(startDate + " 00:00:00", DateUtil.PATTERN_STANDARD);
				startDate = DateUtil.date2String(startDateTime, DateUtil.PATTERN_STANDARD);
			} catch (Exception e) {
				throw new ServiceBusinessException("日志查询中，传入的起始时间格式错误，请使用[yyyy-MM-dd]格式");
			}
			logString.append(",查询起始时间为[").append(startDate).append("]的日志记录");
		} else {
			startDate = null;
		}

		if (!StringUtils.isEmpty(endDate)) {
			try {
				Date endDateTime = DateUtil.string2Date(endDate + " 23:59:59", DateUtil.PATTERN_STANDARD);
				endDate = DateUtil.date2String(endDateTime, DateUtil.PATTERN_STANDARD);
			} catch (Exception e) {
				throw new ServiceBusinessException("日志查询中，传入的结束时间格式错误，请使用[yyyy-MM-dd]格式");
			}
			logString.append(",查询结束时间为[").append(endDate).append("]的日志记录");
		} else {
			endDate = null;
		}

		if (!StringUtils.isEmpty(sort)) {
			logString.append(",通过字段[").append(sort).append("]进行[").append(order).append("]排列");
		} else {
			sort = "operateTime";
		}

		LogUtil.info(LOG, logString.toString());

		return loggerMapper.getLogs(sort, order, username, startDate, endDate, rb);
	}

	/**
	 * 
	 * <p>
	 * Discription:[插入日志记录]
	 * </p>
	 * 
	 * @param logType
	 */
	public void insertLog(LogType logType) {
		LogUtil.info(LOG, "插入日志记录[" + logType.getUsername() + "," + logType.getOperateObject() + ","
				+ logType.getOperateDesc() + "," + logType.getOperateTime() + "]");
		loggerMapper.insertLog(logType);
	}

	/**
	 * 
	 * <p>
	 * Discription:[更新日志记录]
	 * </p>
	 * 
	 * @param logType
	 */
	public void updateLog(LogType logType) {
		loggerMapper.updateLog(logType);
	}

	/**
	 * 
	 * <p>
	 * Discription:[删除日志记录]
	 * </p>
	 * 
	 * @param indexs
	 */
	public void deleteLog(String[] indexs) {
		int[] ids = new int[indexs.length];
		for (int i = 0; i < indexs.length; i++) {
			ids[i] = Integer.valueOf(indexs[i]);
		}

		LogUtil.info(LOG, "即将删除ID为" + Arrays.toString(ids) + "的日志记录");

		loggerMapper.deleteLog(ids);
	}
}

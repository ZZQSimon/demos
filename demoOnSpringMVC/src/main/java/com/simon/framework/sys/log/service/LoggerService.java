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

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.simon.framework.exception.ServiceBusinessException;
import com.simon.framework.sys.log.beans.LogType;

/**
 * @ClassName: LoggerService <br>
 * @Description: 日志服务接口的定义 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public interface LoggerService {

	/**
	 * 
	 * <p>
	 * Discription:[通过条件获取日志记录]
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
			RowBounds rb) throws ServiceBusinessException;

	/**
	 * 
	 * <p>
	 * Discription:[根据条件获取日志记录的总个数]
	 * </p>
	 * 
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getLogsSize(String username, String startDate, String endDate);

	/**
	 * 
	 * <p>
	 * Discription:[插入日志记录]
	 * </p>
	 * 
	 * @param logType
	 */
	public void insertLog(LogType logType);

	/**
	 * 
	 * <p>
	 * Discription:[更新日志记录]
	 * </p>
	 * 
	 * @param logType
	 */
	public void updateLog(LogType logType);

	/**
	 * 
	 * <p>
	 * Discription:[通过日志记录ID列表删除日志记录]
	 * </p>
	 * 
	 * @param indexs
	 */
	public void deleteLog(String[] indexs);
}

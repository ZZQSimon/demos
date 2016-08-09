/**
 * Title: LoggerMapper.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.log.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.simon.framework.sys.log.beans.LogType;

/**
 * @ClassName: LoggerMapper <br>
 * @Description: 日志管理数据库操作接口 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public interface LoggerMapper {
	/**
	 * 
	 * <p>
	 * Discription:[获取所有的日志记录]
	 * </p>
	 * 
	 * @return
	 */
	List<LogType> getLogs();

	/**
	 * 
	 * <p>
	 * Discription:[通过条件组合查询日志记录]
	 * </p>
	 * 
	 * @param sort
	 * @param order
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @param rb
	 * @return
	 */
	List<LogType> getLogs(@Param("sort") String sort, @Param("order") String order, @Param("username") String username,
			@Param("startDate") String startDate, @Param("endDate") String endDate, RowBounds rb);

	/**
	 * 
	 * <p>
	 * Discription:[获取日志记录的总数量]
	 * </p>
	 * 
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int getLogsSize(@Param("username") String username, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	/**
	 * 
	 * <p>
	 * Discription:[插入日志记录]
	 * </p>
	 * 
	 * @param logType
	 */
	void insertLog(LogType logType);

	/**
	 * 
	 * <p>
	 * Discription:[更新日志记录]
	 * </p>
	 * 
	 * @param logType
	 */
	void updateLog(LogType logType);

	/**
	 * 
	 * <p>
	 * Discription:[通过ID列表删除日志记录]
	 * </p>
	 * 
	 * @param ids
	 */
	void deleteLog(@Param("ids") int[] ids);
}

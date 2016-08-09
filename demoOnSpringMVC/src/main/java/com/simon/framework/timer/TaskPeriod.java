/**
 * Title: TaskPeriod.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.timer;

/**
 * 
 * @ClassName: TaskPeriod <br>
 * @Description: 定时任务周期枚举定义 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
@Deprecated
public enum TaskPeriod {
	/**
	 * 秒定义
	 */
	SECOND(1000L),
	/**
	 * 分钟定义
	 */
	MINUTE(1000L * 60),
	/**
	 * 小时定义
	 */
	HOUR(1000L * 60 * 60),
	/**
	 * 天定义
	 */
	DAY(1000L * 60 * 60 * 24),
	/**
	 * 星期定义
	 */
	WEEK(1000L * 60 * 60 * 24 * 7),
	/**
	 * 月定义
	 */
	MONTH(1000L * 60 * 60 * 24 * 30),
	/**
	 * 年定义
	 */
	YEAR(1000L * 60 * 60 * 24 * 365);

	private long value;

	TaskPeriod(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}
}

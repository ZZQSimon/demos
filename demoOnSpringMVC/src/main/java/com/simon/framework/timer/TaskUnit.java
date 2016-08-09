/**
 * Title: TaskUnit.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.timer;

import java.util.Date;

/**
 * 
 * @ClassName: TaskUnit <br>
 * @Description: 定时任务配置文件获取对象接口定义 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
@Deprecated
public interface TaskUnit {

	String getName();

	Runnable getTask();

	Class<?> getTaskClass();

	int getPriority();

	Date getStartTime();

	long getPeriod();

	long getDelay();

	boolean isRunnable();

}

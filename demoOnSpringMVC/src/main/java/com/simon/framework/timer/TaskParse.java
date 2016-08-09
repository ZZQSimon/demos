/**
 * Title: TaskParse.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.timer;

import java.util.Collection;

/**
 * 
 * @ClassName: TaskParse <br>
 * @Description: 定时器任务转换接口定义 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
@Deprecated
public interface TaskParse {

	Collection<TaskUnit> getTaskUnits();

	TaskUnit getTaskUnit(String name);

}

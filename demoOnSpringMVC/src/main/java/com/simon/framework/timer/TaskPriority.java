/**
 * Title: TaskPriority.java <br>
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
 * @ClassName: TaskPriority <br>
 * @Description: 定时任务优先级定义 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
@Deprecated
public enum TaskPriority {
	high(2), normal(1), low(0);

	private int value;

	private TaskPriority(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}

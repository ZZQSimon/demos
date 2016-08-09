/**
 * Title: DefaultTaskFactory.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.timer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import com.simon.framework.exception.ServiceIOException;
import com.simon.framework.timer.TaskEngine;
import com.simon.framework.timer.TaskFactory;
import com.simon.framework.timer.TaskParse;
import com.simon.framework.timer.TaskUnit;

/**
 * 
 * @ClassName: DefaultTaskFactory <br>
 * @Description: 定时器中Task的Factory管理类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

@Deprecated
public class DefaultTaskFactory extends TaskFactory {

	private TaskParse parser = null;

	@Override
	public Runnable getTask(String name) {
		if (parser == null)
			return null;
		TaskUnit taskUnit = this.parser.getTaskUnit(name);
		if (taskUnit != null) {
			return taskUnit.getTask();
		}
		return null;
	}

	@Override
	public void initTasks(File file) throws ServiceIOException {
		if (file == null) {
			return;
		}
		try {
			initTasks(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new ServiceIOException("定时器配置文件未找到");
		}
	}

	@Override
	public void initTasks(InputStream is) {
		if (is == null) {
			return;
		}

		this.parser = new TaskParseImpl(is);

	}

	@Override
	public void startAllTasks() {
		if (this.parser == null)
			return;
		startTasks(this.parser.getTaskUnits());
	}

	@Override
	public void startTasks(Collection<TaskUnit> units) {
		if (units == null || units.isEmpty())
			return;
		for (TaskUnit taskUnit : units) {
			if (taskUnit.isRunnable()) {
				Runnable task = taskUnit.getTask();
				Date startTime = taskUnit.getStartTime();
				int priority = taskUnit.getPriority();
				long period = taskUnit.getPeriod();

				if (period == 0) {
					TaskEngine.scheduleTask(task, startTime, priority);
				} else {
					TaskEngine.scheduleTask(task, startTime, period, priority);
				}
			}
		}
	}

	@Override
	public TaskParse getTaskParse() {
		return this.parser;
	}

}

/**
 * Title: TaskFactory.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.timer;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import com.simon.framework.exception.ServiceIOException;

/**
 * 
 * @ClassName: TaskFactory <br>
 * @Description: 定时任务Factory管理的基类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
@Deprecated
public abstract class TaskFactory {

	private static final String className = "com.simon.framework.timer.impl.DefaultTaskFactory";

	private static TaskFactory factory = null;

	private static Object lock = new Object();

	public static TaskFactory getFactory() {
		if (factory == null) {
			synchronized (lock) {
				if (factory == null) {
					try {
						Class<?> clazz = Class.forName(className);
						factory = (TaskFactory) clazz.newInstance();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return factory;
	}

	public abstract void initTasks(File file) throws ServiceIOException;

	public abstract void initTasks(InputStream is);

	public abstract Runnable getTask(String name);

	public abstract void startAllTasks();

	public abstract void startTasks(Collection<TaskUnit> units);

	public abstract TaskParse getTaskParse();

}

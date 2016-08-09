package com.simon.framework.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: ThreadUtils <br>
 * @Description: 线程相关的Utils函数集合. <br>
 * @date 2015-1-13 下午04:08:24 <br>
 * 
 * @author Dean
 */
public class ThreadUtils {

	/**
	 * @ClassName: WorkerThreadFactory <br>
	 * @Description: 内部类,自定义ThreadFactory,可定制线程池的名称. <br>
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */

	private static class WorkerThreadFactory implements ThreadFactory {

		private final String namePrefix;

		private final AtomicInteger threadNumber = new AtomicInteger(1);

		public WorkerThreadFactory(final String poolName) {
			namePrefix = poolName + "-";
		}

		public Thread newThread(final Runnable runable) {
			return new Thread(runable, namePrefix + threadNumber.getAndIncrement());
		}
	}

	/**
	 * <p>
	 * Discription:[创建WorkerThreadFactory实例，自定义名字.]
	 * </p>
	 * 
	 * @param poolName
	 *            线程池名字
	 * @return WorkerThreadFactory
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */

	public static WorkerThreadFactory instanceThreadFactory(final String poolName) {
		return new WorkerThreadFactory(poolName);
	}

	/**
	 * Discription:[延迟终止线程池.]
	 * 
	 * @param pool
	 *            ExecutorService
	 * @param timeout
	 *            int
	 * @param timeUnit
	 *            TimeUnit
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */

	public static void normalShutdown(final ExecutorService pool, final int timeout, final TimeUnit timeUnit) {
		try {
			pool.shutdownNow();
			if (!pool.awaitTermination(timeout, timeUnit)) {
				System.err.println("Pool did not terminate");
			}
		} catch (final InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * sleep等待,单位毫秒,忽略InterruptedException.
	 * 
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */

	public static void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {
			// Ignore.
		}
	}
}

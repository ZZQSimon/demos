/**
 * Title: ThreadLocalUtils.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.adapter.interceptor;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: ThreadLocalUtils <br>
 * @Description: ThreadLocal工具类 <br>
 */
public final class ThreadLocalResponse {

	/**
	 * 当前线程中存储一个HttpServletResponse的对象
	 */
	private static final ThreadLocal<HttpServletResponse> threadContext = new ThreadLocal<HttpServletResponse>();

	/**
	 * 设置response
	 * 
	 * @param response
	 */
	public static void set(HttpServletResponse response) {
		threadContext.set(response);
	}

	/**
	 * 通过response
	 * 
	 * @return
	 */
	public static HttpServletResponse get() {
		return threadContext.get();
	}

	/**
	 * 删除response
	 * 
	 */
	public static void remove() {
		threadContext.remove();
	}

}

/**
 * Title: ServiceIOException.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.exception;

/**
 * @ClassName: ServiceIOException <br>
 * @Description: IO操作异常 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class ServiceIOException extends Exception {
	private static final long serialVersionUID = -1L;

	public ServiceIOException(String message) {
		super(message);
	}
}

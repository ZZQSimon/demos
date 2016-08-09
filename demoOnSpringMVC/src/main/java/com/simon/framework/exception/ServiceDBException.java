/**
 * Title: ServiceDBException.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.exception;

/**
 * @ClassName: ServiceDBException <br>
 * @Description: DB 操作异常 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class ServiceDBException extends Exception {
	private static final long serialVersionUID = -1L;

	public ServiceDBException(String message) {
		super(message);
	}
}

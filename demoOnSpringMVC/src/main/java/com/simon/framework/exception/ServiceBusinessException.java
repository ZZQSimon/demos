/**
 * Title: ServiceBusinessException.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.exception;

/**
 * @ClassName: ServiceBusinessException <br>
 * @Description: Without log stack trace. <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class ServiceBusinessException extends Exception {
	private static final long serialVersionUID = -1L;

	public ServiceBusinessException(String message) {
		super(message);
	}
}

/**
 * Title: ServiceAuthorizeException.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.exception;

/**
 * @ClassName: ServiceAuthorizeException <br>
 * @Description: 权限验证异常 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class ServiceAuthorizeException extends Exception {
	private static final long serialVersionUID = -1L;

	public ServiceAuthorizeException(String message) {
		super(message);
	}
}

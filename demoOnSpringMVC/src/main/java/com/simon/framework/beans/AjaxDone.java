/**
 * Title: AjaxDone.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.beans;

/**
 * @ClassName: AjaxDone <br>
 * @Description: 成功返回对象 <br>
 * @date 2016-08-09 09:07:03 <br>
 * 
 * @author Simon
 */
public class AjaxDone {

	/**
	 * The status code.
	 */
	private String statusCode;

	/**
	 * The message.
	 */
	private String message;

	/**
	 * The return url.
	 */
	private String returnUrl;

	public AjaxDone() {
		super();
	}

	public AjaxDone(String statusCode, String message, String returnUrl) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.returnUrl = returnUrl;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}

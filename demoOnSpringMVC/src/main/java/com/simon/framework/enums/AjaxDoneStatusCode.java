/**
 * Title: AjaxDoneStatusCode.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang<br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.enums;

/**
 * @ClassName: AjaxDoneStatusCode <br>
 * @Description: return for web page. <br>
 * @date 2016-08-09 09:26:01 <br>
 * 
 * @author Simon
 */
public enum AjaxDoneStatusCode implements BaseEnum<String> {

	/**
	 * 操作成功返回码和操作描述
	 */
	CD_SUCCESS("200", "操作成功!"),
	/**
	 * 操作失败返回码和操作描述
	 */
	CD_FAILED("300", "操作失败!");

	/**
	 * 返回码
	 */
	private String code;

	/**
	 * 返回描述
	 */
	private String desc;

	private AjaxDoneStatusCode(String code, String desc) {
		this.desc = desc;
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}
}

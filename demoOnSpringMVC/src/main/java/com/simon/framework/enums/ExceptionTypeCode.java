/**
 * Title: ExceptionTypeCode.java <br>
 * Description:[] <br>
 * Date: 2016-08-09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.enums;

/**
 * @ClassName: ExceptionTypeCode <br>
 * @Description: System error code enum. <br>
 * @date 2016-08-09 09:17:03 <br>
 * 
 * @author Simon
 */
public enum ExceptionTypeCode implements BaseEnum<String> {

	/**
	 * 系统错误及编码
	 */
	CD_SYS("系统错误", "10000"),

	/**
	 * 业务错误及编码
	 */
	CD_BUSINESS("业务错误", "20000"),

	/**
	 * 数据库错误及编码
	 */
	CD_DB("数据库错误", "30000"),

	/**
	 * 验证或权限异常及编码
	 */
	CD_AUTHORIZATION("验证或权限异常", "40000"),

	/**
	 * CSS server异常及编码
	 */
	CD_CSS("CSS server异常", "50000"),

	/**
	 * 定时器错误及编码
	 */
	CD_TIMER("定时器错误", "60000"),

	/**
	 * 文件操作出错及编码
	 */
	CD_FILE("文件操作出错", "70000"),

	/**
	 * 线程出错及编码
	 */
	CD_THREAD("线程出错", "80000"),

	/**
	 * 邮件操作出错及编码
	 */
	CD_EMAIL("邮件操作出错", "90000");

	private String desc;

	private String code;

	private ExceptionTypeCode(String desc, String code) {
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

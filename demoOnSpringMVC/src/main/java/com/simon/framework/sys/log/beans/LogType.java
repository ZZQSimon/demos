/**
 * Title: LogType.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.log.beans;

/**
 * @ClassName: LogType <br>
 * @Description: 日志类型 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class LogType {

	/** The id. */
	private int id;

	/** 操作用户 */
	private String username;

	/** 操作对象 */
	private String operateObject;

	/** 操作描述 */
	private String operateDesc;

	/** 操作时间 */
	private String operateTime;

	/** 操作人IP */
	private String operateIp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperateObject() {
		return operateObject;
	}

	public void setOperateObject(String operateObject) {
		this.operateObject = operateObject;
	}

	public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

}

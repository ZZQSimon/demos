/**
 * Title: StyleHistory.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.style.bean;

import java.sql.Timestamp;

/**
 * @ClassName: StyleHistory <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class StyleHistory {

	private int id;

	private int styleId;

	private String operator;

	private Timestamp operateDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStyleId() {
		return styleId;
	}

	public void setStyleId(int styleId) {
		this.styleId = styleId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Timestamp operateDate) {
		this.operateDate = operateDate;
	}

}

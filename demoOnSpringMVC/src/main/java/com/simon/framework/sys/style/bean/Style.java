/**
 * Title: Style.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.style.bean;

/**
 * @ClassName: Style <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class Style {

	private int id;

	private String style;

	private boolean status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}

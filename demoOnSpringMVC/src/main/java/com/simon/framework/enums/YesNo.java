/**
 * Title: YesNo.java <br>
 * Description:[] <br>
 * Date: 2016-08-09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.enums;

/**
 * @ClassName: YesNo <br>
 * @Description: yes or no enum. <br>
 * @date 2016-08-09 09:28:52 <br>
 * 
 * @author Simon
 */
public enum YesNo implements BaseEnum<Integer> {
	Yes("是", 0), No("否", 1);

	private String desc;
	private Integer code;

	private YesNo(String desc, Integer code) {
		this.desc = desc;
		this.code = code;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}

	public static YesNo bool2YesNo(boolean flag) {
		if (flag) {
			return YesNo.Yes;
		}
		return YesNo.No;
	}

	public boolean boolValue() {
		if (this.equals(YesNo.Yes)) {
			return true;
		}
		return false;
	}
}

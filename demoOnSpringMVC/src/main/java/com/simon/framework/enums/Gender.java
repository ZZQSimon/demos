/**
 * Title: Gender.java <br>
 * Description:[] <br>
 * Date: 2016-08-09<br>
 * Copyright (c) 2016 Simon Zhang<br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.enums;

/**
 * @ClassName: Gender <br>
 * @Description: male or female enum. <br>
 * @date 2016-08-09 09:14:24<br>
 * 
 * @author Dean
 */
public enum Gender implements BaseEnum<Integer> {
	Male("男", 1), FMale("女", 0);

	private String desc;
	private Integer code;

	private Gender(String desc, Integer code) {
		this.desc = desc;
		this.code = code;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}

}

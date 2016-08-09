/**
 * Title: BaseEnum.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.enums;

/**
 * @ClassName: BaseEnum <br>
 * @Description: 通用枚举接口 <br>
 * @date 2016-08-09 09:26:56 <br>
 * 
 * @author Simon
 * @param <K>
 */
public interface BaseEnum<K> {

	/**
	 * 值信息
	 * 
	 * @return
	 */
	K getCode();

	/**
	 * 描述信息
	 * 
	 * @return
	 */
	String getDesc();

	/**
	 * 显示文本名称
	 * 
	 * @return
	 */
	String name();

}

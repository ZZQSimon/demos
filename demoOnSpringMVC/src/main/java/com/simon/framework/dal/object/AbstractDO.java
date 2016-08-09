/**
 * Title: AbstractDO.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.dal.object;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @ClassName: AbstractDO <br>
 * @Description: DO对象基类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public abstract class AbstractDO implements Serializable {

	private static final long serialVersionUID = -3942149913171834745L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

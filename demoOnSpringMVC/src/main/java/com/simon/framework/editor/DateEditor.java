/**
 * Title: DateEditor.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.simon.framework.util.DateUtil;

/**
 * 
 * @ClassName: DateEditor <br>
 * @Description: Date与String类型转换器 <br>
 */
public class DateEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (!StringUtils.hasText(text)) {
			setValue(null);
		} else {
			setValue(DateUtil.string2Date(text, "yyyy-MM-dd"));
		}
	}

	@Override
	public String getAsText() {

		return getValue().toString();
	}
}

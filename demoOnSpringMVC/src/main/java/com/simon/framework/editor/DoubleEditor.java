/**
 * Title: DoubleEditor.java <br>
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

/**
 * 
 * @ClassName: DoubleEditor <br>
 * @Description: Double与String类型转换器 <br>
 */
public class DoubleEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.equals(""))
			text = "0";
		if (!StringUtils.hasText(text)) {
			setValue(null);
		} else {
			setValue(Double.parseDouble(text));
		}
	}

	@Override
	public String getAsText() {

		return getValue().toString();
	}
}

/**
 * Title: StyleServiceImpl.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.style.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simon.framework.sys.style.bean.Style;
import com.simon.framework.sys.style.mapper.StyleMapper;
import com.simon.framework.util.StringUtils;

/**
 * @ClassName: StyleServiceImpl <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

@Service("styleService")
public class StyleServiceImpl implements StyleService {

	@Autowired
	private StyleMapper styleMapper;

	public String getCurrentStyle() {
		try {
			Style style = styleMapper.getStyle();
			if (style == null || StringUtils.isEmpty(style.getStyle())) {
				return "blue";
			}
			return style.getStyle();
		} catch (Exception e) {
			return "blue";
		}

	}
}

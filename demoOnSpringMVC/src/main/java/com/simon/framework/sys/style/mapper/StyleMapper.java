/**
 * Title: StyleMapper.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.style.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.simon.framework.sys.style.bean.Style;
import com.simon.framework.sys.style.bean.StyleHistory;

/**
 * @ClassName: StyleMapper <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public interface StyleMapper {
	public Style getStyle();

	public void updateStylesAsUnavaliable();

	public void updateStyleToAvaliable(Style style);

	public List<StyleHistory> getHistories(RowBounds rb);

	public void insertHistory(StyleHistory styleHistory);

}

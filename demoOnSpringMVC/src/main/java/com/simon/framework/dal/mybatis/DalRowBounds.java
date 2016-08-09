/**
 * Title: DalRowBounds.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.dal.mybatis;

import org.apache.ibatis.session.RowBounds;

/**
 * 
 * @ClassName: DalRowBounds <br>
 * @Description: 方言 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class DalRowBounds extends RowBounds {

	private int extOffset;

	private int extLimit;

	private int total;

	public DalRowBounds(int offset, int limit) {
		this.extOffset = offset;
		this.extLimit = limit;
	}

	public int getExtOffset() {
		return extOffset;
	}

	public int getExtLimit() {
		return extLimit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}

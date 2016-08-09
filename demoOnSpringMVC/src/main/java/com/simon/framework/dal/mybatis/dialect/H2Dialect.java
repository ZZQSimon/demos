/**
 * Title: H2Dialect.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.dal.mybatis.dialect;

/**
 * 
 * @ClassName: H2Dialect <br>
 * @Description: H2的分页方言实现 <br>
 */
public class H2Dialect extends Dialect {

	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		return new StringBuffer(sql.length() + 40).append(sql).append((offset > 0)
				? " limit " + limitPlaceholder + " offset " + offsetPlaceholder : " limit " + limitPlaceholder)
				.toString();
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

	// public boolean bindLimitParametersInReverseOrder() {
	// return true;
	// }
	//
	// public boolean bindLimitParametersFirst() {
	// return false;
	// }

}
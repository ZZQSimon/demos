/**
 * Title: PostgreSQLDialect.java <br>
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
 * @ClassName: PostgreSQLDialect <br>
 * @Description: PostgreSQL的分页方言实现 <br>
 */
public class PostgreSQLDialect extends Dialect {

	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset() {
		return true;
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		return new StringBuffer(sql.length() + 20).append(sql).append(offset > 0
				? " limit " + limitPlaceholder + " offset " + offsetPlaceholder : " limit " + limitPlaceholder)
				.toString();
	}
}

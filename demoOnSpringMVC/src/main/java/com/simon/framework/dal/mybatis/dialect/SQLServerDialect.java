/**
 * Title: SQLServerDialect.java <br>
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
 * @ClassName: SQLServerDialect <br>
 * @Description: SQLServer的分页方言实现 <br>
 */
public class SQLServerDialect extends Dialect {

	public boolean supportsLimitOffset() {
		return false;
	}

	public boolean supportsLimit() {
		return true;
	}

	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}

	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, null, limit, null);
	}

	public String getLimitString(String querySelect, int offset, String offsetPlaceholder, int limit,
			String limitPlaceholder) {
		if (offset > 0) {
			throw new UnsupportedOperationException("sql server has no offset");
		}
		// if(limitPlaceholder != null) {
		// throw new UnsupportedOperationException(" sql server not support
		// variable limit");
		// }

		return new StringBuffer(querySelect.length() + 8).append(querySelect)
				.insert(getAfterSelectInsertPoint(querySelect), " top " + limit).toString();
	}

	// public boolean supportsVariableLimit() {
	// return false;
	// }

}

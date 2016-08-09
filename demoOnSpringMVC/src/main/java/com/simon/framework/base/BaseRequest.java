package com.simon.framework.base;

/**
 * @ClassName: BaseRequest <br>
 * @Description: Request 基础类. <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class BaseRequest {

	private String title;

	private String page;

	private String rows;

	private String[] sort;

	private String[] order;

	private int export;

	public String[] getSort() {
		return sort;
	}

	public void setSort(String[] sort) {
		this.sort = sort;
	}

	public String[] getOrder() {
		return order;
	}

	public void setOrder(String[] order) {
		this.order = order;
	}

	private String rowNames;

	public String getRowNames() {
		return rowNames;
	}

	public void setRowNames(String rowNames) {
		this.rowNames = rowNames;
	}

	public int getExport() {
		return export;
	}

	public void setExport(int export) {
		this.export = export;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

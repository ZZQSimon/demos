package com.simon.framework.util.security.springsecurity;

import java.util.LinkedHashMap;

/**
 * 
 * @ClassName: ResourceDetailsService <br>
 * @Description: RequestMap生成接口,由用户自行实现从数据库或其它地方查询URL-授权关系定义. <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public interface ResourceDetailsService {

	/**
	 * 返回带顺序的URL-授权关系Map, key为受保护的URL, value为能访问该URL的授权名称列表,以','分隔.
	 */
	public LinkedHashMap<String, String> getRequestMap() throws Exception; // NOSONAR
}

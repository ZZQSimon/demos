package com.simon.framework.util;

import java.util.Map;

/**
 * @ClassName: MapUtils <br>
 * @Description: map工具类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class MapUtils {

	/**
	 * <p>
	 * Discription:[if key or value is null there will throw exception.]
	 * </p>
	 * 
	 * @param map
	 *            Map<Object,Object>
	 * @param key
	 *            Object
	 * @param defaultValue
	 *            Object
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */
	public static void putIfNull(Map<Object, Object> map, Object key, Object defaultValue) {
		if (key == null)
			throw new IllegalArgumentException("key must be not null");
		if (map == null)
			throw new IllegalArgumentException("map must be not null");
		if (map.get(key) == null) {
			map.put(key, defaultValue);
		}
	}

}

package com.simon.framework.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ArrayUtils <br>
 * @Description: 工具类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class ArrayUtils {

	private ArrayUtils() {
	}

	/**
	 * <p>
	 * Discription:[将一个array转为根据keys的map]
	 * </p>
	 * 
	 * @param array
	 * @param keys
	 * @return
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */
	public static Map<String, Object> toMap(Object[] array, String... keys) {
		if (array == null)
			return new HashMap<String, Object>();
		Map<String, Object> m = new LinkedHashMap<String, Object>();
		for (int i = 0; i < keys.length; i++) {
			if (array.length == i) {
				break;
			}
			m.put(keys[i], array[i]);
		}
		return m;
	}

	/**
	 * <p>
	 * Discription:[将array转为list]
	 * </p>
	 * 
	 * @param array
	 * @return
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */
	public static List<Object> toList(Object[] array) {
		if (array == null)
			return null;
		return Arrays.asList(array);
	}

}

/**
 * Title: EnumUtils.java <br>
 * Description:[] <br>
 * Date: 2016-08-09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */
package com.simon.framework.enums;

import java.util.LinkedHashMap;

/**
 * @ClassName: EnumUtils <br>
 * @Description: 枚举工具类 <br>
 * @date 2016-08-09 09:25:00 <br>
 * 
 * @author Simon
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EnumUtils {
	/**
	 * 根据code查找得到Enum
	 * 
	 * @param code
	 * @param values
	 * @return
	 */
	private static <T extends BaseEnum> T getByCode(final Object code, final T[] values) {
		if (code == null) {
			return null;
		}
		for (final T item : values) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Discription:[根据item获取code]
	 * </p>
	 * 
	 * @param <T>
	 * @param kv
	 * @return
	 * @author:[Simon]
	 * @update:[2016.08.09] [更改人姓名][变更描述]
	 */
	public static <T extends BaseEnum> Object getCode(final T kv) {
		if (kv == null) {
			return null;
		}
		return kv.getCode();
	}

	/**
	 * <p>
	 * Discription:[根据item获取desc]
	 * </p>
	 * 
	 * @param <T>
	 * @param kv
	 * @return
	 * @author:[Simon]
	 * @update:[2016.08.09] [更改人姓名][变更描述]
	 */
	public static <T extends BaseEnum> String getDesc(final T kv) {
		if (kv == null) {
			return null;
		}
		return kv.getDesc();
	}

	public static <T extends BaseEnum> String getDescByCode(final Class<? extends BaseEnum> enumClass,
			final Object code) {
		return getDescByCode(code, enumClass.getEnumConstants());
	}

	/**
	 * 根据code查找得到desc
	 * 
	 * @param code
	 * @param values
	 * @return
	 */
	private static <T extends BaseEnum> String getDescByCode(final Object code, final T[] values) {
		if (code == null) {
			return null;
		}
		for (final T item : values) {
			if (item.getCode().equals(code)) {
				return item.getDesc();
			}
		}
		return null;
	}

	/**
	 * 根据code查找得到Enum
	 * 
	 * @param code
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> T getItemByCode(final Object code, final Class<? extends BaseEnum> enumClass) {
		return (T) getByCode(code, enumClass.getEnumConstants());
	}

	/**
	 * <p>
	 * Discription:[根据item获取name]
	 * </p>
	 * 
	 * @param <T>
	 * @param kv
	 * @return
	 * @author:[Simon]
	 * @update:[2016.08.09] [更改人姓名][变更描述]
	 */
	public static <T extends Enum> String getName(final T kv) {
		if (kv == null) {
			return null;
		}
		return kv.name();
	}

	/**
	 * 根据code查找得到Enum
	 * 
	 * @param code
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> T getRequiredByCode(final Object code,
			final Class<? extends BaseEnum> enumClass) {
		return (T) getRequiredByCode(code, enumClass.getEnumConstants());
	}

	/**
	 * 根据code得到Enum,找不到则抛异常
	 * 
	 * @param <T>
	 * @param code
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 *             根据code得到Enum,找不到则抛异常
	 */
	public static <T extends BaseEnum> T getRequiredByCode(final Object code, final T[] values)
			throws IllegalArgumentException {
		final BaseEnum v = getByCode(code, values);
		if (v == null) {
			if (values.length > 0) {
				final String className = values[0].getClass().getName();
				throw new IllegalArgumentException("not found " + className + " value by code:" + code);
			} else {
				throw new IllegalArgumentException("not found Enum by code:" + code);
			}
		}
		return (T) v;
	}

	public static boolean isDefined(final Enum<?>[] ems, final String emStr) {
		for (final Enum<?> em : ems) {
			if (em.toString().equals(emStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 将BaseEnum.getCode()作为Key,BaseEnum.getDesc()作为value,存放在Map中并返回
	 * 
	 * @param <T>
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> LinkedHashMap toMap(final Class<? extends BaseEnum> enumClass) {
		return toMap(enumClass.getEnumConstants());
	}

	/**
	 * 将BaseEnum.getCode()作为Key,BaseEnum.getDesc()作为value,存放在Map中并返回
	 * 
	 * @param <T>
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> LinkedHashMap toMap(final T[] values) {
		final LinkedHashMap map = new LinkedHashMap();
		for (final BaseEnum item : values) {
			map.put(item.getCode(), item.getDesc());
		}
		return map;
	}

}

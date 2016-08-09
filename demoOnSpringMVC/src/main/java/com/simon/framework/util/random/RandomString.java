package com.simon.framework.util.random;

import java.util.Random;

/**
 * 
 * @ClassName: RandomString <br>
 * @Description: 随机字符串操作类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class RandomString {

	private static char[] NUMBER_POOL = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static Random random = new Random(System.currentTimeMillis());

	public static String randomString(int length) {
		if (length <= 0) {
			throw new java.lang.IllegalArgumentException();
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int tmp = random.nextInt(10);
			sb.append(NUMBER_POOL[tmp]);
		}
		return sb.toString();
	}
}

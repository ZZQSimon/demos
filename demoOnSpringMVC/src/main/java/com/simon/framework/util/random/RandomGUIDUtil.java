package com.simon.framework.util.random;

/**
 * 
 * @ClassName: RandomGUIDUtil <br>
 * @Description: 产生唯一的随机字符串 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class RandomGUIDUtil {
	/**
	 * 产生唯一的随机字符串
	 * 
	 * @return
	 */
	public static String generateKey() {
		return new RandomGUID(true).toString();
	}
}
/**
 * Title: SysConstants.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.config;

/**
 * 
 * @ClassName: SysConstants <br>
 * @Description: 系统级别常量类 <br>
 */
public class SysConstants {

	public static final long SECOND = 1000;

	public static final long MINUTE = 60 * SECOND;

	public static final long HOUR = 60 * MINUTE;

	public static final long DAY = 24 * HOUR;

	public static final long WEEK = 7 * DAY;

	public static final String SUB_SYSTEM_KEY = "SUB_SYSTEM_KEY";

	public static final String SUB_FAME_JS_PREFIX = "$(window.parent.document).contents().find('#ibms_main_content_02')[0].contentWindow.";

}
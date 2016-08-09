package com.simon.framework.util.io;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.simon.framework.util.DateUtil;

/**
 * 
 * @ClassName: FileStoreUtils <br>
 * @Description: 文件存储工具类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class FileStoreUtils {

	private static String _dateFormatDirectory(final Date date) {
		return DateUtil.date2String(date, "/yyyy/MM/dd");
	}

	public static FileStoreObject buildFilenamePathByNow(final String ext, String physicalPath, String relativePath) {
		Assert.hasText(ext);
		physicalPath = formatPath(physicalPath);
		relativePath = formatPath(relativePath);
		final String filename = UUID.randomUUID().toString().replaceAll("-", "");
		final String dateFormatDirectory = _dateFormatDirectory(new Date());
		final FileStoreObject fso = new FileStoreObject(physicalPath, relativePath, ext, dateFormatDirectory, filename);
		return fso;
	}

	public static String formatPath(String path) {
		if (StringUtils.isEmpty(path) || StringUtils.isBlank(path) || path.equals("/") || path.equals("\\")) {
			path = "";
		}
		path = path.replaceAll("\\\\", "/");
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}
}

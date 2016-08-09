package com.simon.framework.util;

import java.text.DecimalFormat;

/**
 * @ClassName: FileSizeUtils <br>
 * @Description: 文件大小控制工具类 <br>
 * @date 2015-1-16 上午10:41:34 <br>
 * 
 * @author Dean
 */
public class UnitUtils
{
    public static long ONE_KB = 1024;
    
    public static long ONE_MB = ONE_KB * 1024;
    
    public static long ONE_GB = ONE_MB * 1024;
    
    public static long ONE_TB = ONE_GB * 1024;
    
    public static long ONE_PB = ONE_TB * 1024;
    
    /**
     * Discription:[B to others.]
     * 
     * @param fileSize
     * @return String
  * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

    public static String handleUnit(final long fileSize)
    {
        if (fileSize < 0)
        {
            return String.valueOf(fileSize);
        }
        String result = handleUnit(fileSize, ONE_PB, "PB");
        if (result != null)
        {
            return result;
        }
        
        result = handleUnit(fileSize, ONE_TB, "TB");
        if (result != null)
        {
            return result;
        }
        result = handleUnit(fileSize, ONE_GB, "GB");
        if (result != null)
        {
            return result;
        }
        result = handleUnit(fileSize, ONE_MB, "MB");
        if (result != null)
        {
            return result;
        }
        result = handleUnit(fileSize, ONE_KB, "KB");
        if (result != null)
        {
            return result;
        }
        return String.valueOf(fileSize) + "B";
    }
    
    /**
     * Discription:[按比例转换.]
     * 
     * @param fileSize
     * @param unit 比例
     * @param unitName 单位
     * @return string
     * @author:[Dean]
     */
    private static String handleUnit(final long fileSize, final long unit, final String unitName)
    {
        if (fileSize == 0)
        {
            return "0";
        }
        
        if (fileSize / unit >= 1)
        {
            final double value = fileSize / (double)unit;
            final DecimalFormat df = new DecimalFormat("######.##" + unitName);
            return df.format(value);
        }
        return null;
    }
    
    /**
     * Discription:[B to others.]
     * 
     * @param fileSize
     * @return String
     * @author:[Dean]
     */
    public static String handleUnit(final Long fileSize)
    {
        if (fileSize == null)
        {
            return null;
        }
        return handleUnit(fileSize.longValue());
    }
    
    public static void main(final String args[])
    {
        System.out.println(UnitUtils.handleUnit(1024, 10, "美元"));
    }
}

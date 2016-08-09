/**
 * Title: UUIDUtil.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.util;

import java.util.UUID;

/**
 * @ClassName: UUIDUtil <br>
 * @Description: UUID 生成策略类<br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public final class UUIDUtil
{
    /**
     * <p>
     * Discription:[方法功能中文描述]
     * </p>
     * 
     * @return
     * @author:[Freud]
     * @update:[2015-3-9] [更改人姓名][变更描述]
     */
    public static String generateUUID()
    {
        
        return UUID.randomUUID().toString().replace("-", "");
    }
    
}

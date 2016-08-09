/**
 * Title: BaseMapper.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.base;

import java.util.List;

import com.simon.framework.dal.object.AbstractDO;

/**
 * @ClassName: BaseMapper <br>
 * @Description: Mapper 基础类. <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public interface BaseMapper<T extends AbstractDO, PK extends java.io.Serializable> {

	PK insert(T model);

	void delete(PK modelPK);

	T load(PK modelPK);

	void update(T model);

	void updateSelective(T model);

	int countAll();

	List<T> findAll();

	List<PK> findAllIds();

}

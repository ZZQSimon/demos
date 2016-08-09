/**
 * Title: Role.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.authorize.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Role <br>
 * @Description: 角色类 <br>
 */
public class Role implements Serializable {

	private static final long serialVersionUID = -7398033380596098915L;

	private int id;

	private String roleName;

	private List<Resource> resources = new ArrayList<Resource>();

	public Role() {
		super();
	}

	public Role(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Resource> getResources() {
		if (resources == null) {
			resources = new ArrayList<Resource>();
		}
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

}

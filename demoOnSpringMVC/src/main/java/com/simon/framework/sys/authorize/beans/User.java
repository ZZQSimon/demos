/**
 * Title: User.java <br>
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
 * @ClassName: User <br>
 * @Description: 用户类 <br>
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String username;

	private String password;

	private List<Role> roles = new ArrayList<Role>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		if (roles == null) {
			roles = new ArrayList<Role>();
		}
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}

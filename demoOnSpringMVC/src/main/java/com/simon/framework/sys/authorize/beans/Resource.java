/**
 * Title: Navigation.java <br>
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

import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * @ClassName: Navigation <br>
 * @Description: 导航,菜单实体类. <br>
 */
public class Resource implements Serializable {
	private static final long serialVersionUID = -2430523293237252486L;

	private int id;

	private int parentId;

	private int level;

	private String resourceType;

	private String imageUrl;

	private String link;

	private String description;

	private String updateUser;

	private String updateTime;

	private boolean ifValid;

	private boolean hasFloor;

	private boolean flex;

	private boolean ifGroup;

	private boolean flexAuthority;

	private int priority;

	private List<Role> roles;

	private List<Resource> resources;

	public Resource() {
		super();
	}

	public Resource(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isIfValid() {
		return ifValid;
	}

	public void setIfValid(boolean ifValid) {
		this.ifValid = ifValid;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@JsonBackReference
	public List<Role> getRoles() {
		if (roles == null) {
			roles = new ArrayList<Role>();
		}
		return roles;
	}

	@JsonBackReference
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@JsonBackReference
	public List<Resource> getResources() {
		if (resources == null) {
			resources = new ArrayList<Resource>();
		}
		return resources;
	}

	@JsonBackReference
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public boolean isHasFloor() {
		return hasFloor;
	}

	public void setHasFloor(boolean hasFloor) {
		this.hasFloor = hasFloor;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isFlex() {
		return flex;
	}

	public void setFlex(boolean flex) {
		this.flex = flex;
	}

	public boolean isIfGroup() {
		return ifGroup;
	}

	public void setIfGroup(boolean ifGroup) {
		this.ifGroup = ifGroup;
	}

	public boolean isFlexAuthority() {
		return flexAuthority;
	}

	public void setFlexAuthority(boolean flexAuthority) {
		this.flexAuthority = flexAuthority;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

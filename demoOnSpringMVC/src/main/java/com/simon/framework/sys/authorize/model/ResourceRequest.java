/**
 * Title: ResourceRequest.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.authorize.model;

import java.util.ArrayList;
import java.util.List;

public class ResourceRequest {

	private int id;

	private int priority;

	private Integer parentsResource;

	private Integer parentsLevel;

	private String resourceType;

	private String resourceName;

	private String link;

	private String imageUrl;

	private boolean hasFloor;

	private boolean validate;

	private boolean flex;

	private boolean hasGroup;

	private boolean floorCheck;

	private List<ResourceRequest> resources;

	public boolean isFloorCheck() {
		return floorCheck;
	}

	public void setFloorCheck(boolean floorCheck) {
		this.floorCheck = floorCheck;
	}

	public boolean isFlex() {
		return flex;
	}

	public void setFlex(boolean flex) {
		this.flex = flex;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getParentsResource() {
		return parentsResource;
	}

	public void setParentsResource(Integer parentsResource) {
		this.parentsResource = parentsResource;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isHasFloor() {
		return hasFloor;
	}

	public void setHasFloor(boolean hasFloor) {
		this.hasFloor = hasFloor;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public List<ResourceRequest> getResources() {
		if (resources == null) {
			resources = new ArrayList<ResourceRequest>();
		}
		return resources;
	}

	public void setResources(List<ResourceRequest> resources) {
		this.resources = resources;
	}

	public boolean isHasGroup() {
		return hasGroup;
	}

	public void setHasGroup(boolean hasGroup) {
		this.hasGroup = hasGroup;
	}

	public Integer getParentsLevel() {
		return parentsLevel;
	}

	public void setParentsLevel(Integer parentsLevel) {
		this.parentsLevel = parentsLevel;
	}

}

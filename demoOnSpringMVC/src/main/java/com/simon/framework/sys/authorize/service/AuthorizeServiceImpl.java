/**
 * Title: AuthorizeService.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.authorize.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.simon.framework.exception.ServiceAuthorizeException;
import com.simon.framework.exception.ServiceBusinessException;
import com.simon.framework.sys.authorize.beans.Floor;
import com.simon.framework.sys.authorize.beans.Resource;
import com.simon.framework.sys.authorize.beans.Role;
import com.simon.framework.sys.authorize.beans.RoleResource;
import com.simon.framework.sys.authorize.beans.User;
import com.simon.framework.sys.authorize.beans.UserRole;
import com.simon.framework.sys.authorize.mapper.AuthorizeMapper;
import com.simon.framework.sys.authorize.model.ResourceRequest;
import com.simon.framework.sys.authorize.security.SecurityContextUtil;
import com.simon.framework.util.DateUtil;
import com.simon.framework.util.LogUtil;
import com.simon.framework.util.MD5Util;

/**
 * @ClassName: AuthorizeService <br>
 * @Description: 权限信息管理服务实现类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

@Service("authorizeService")
public class AuthorizeServiceImpl implements AuthorizeService {

	private final static Log log = LogFactory.getLog(AuthorizeServiceImpl.class);

	private static final String RESOURCE_TYPE_URL = "url";

	private static final String RESOURCE_TYPE_FLEX = "flex";

	private static final String FLEX_AUTH_EDIT = "编辑";

	private static final String FLEX_AUTH_EDIT_CODE = "edit";

	private static final String FLEX_AUTH_CONTROLLER = "控制";

	private static final String FLEX_AUTH_CONTROLLER_CODE = "controller";

	private static final int LEVEL_ROOT_RESOURCE = 0;

	private static final int LEVEL_FIRST_NAVIGATION_RESOURCE = 1;

	private AuthorizeMapper authorizeMapper;

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param authorizeMapper
	 *            The authorizeMapper to set.
	 */
	@Autowired
	public void setAuthorizeMapper(AuthorizeMapper authorizeMapper) {
		this.authorizeMapper = authorizeMapper;
	}

	public User getUser(String username) throws ServiceAuthorizeException {
		User user = authorizeMapper.getUserByName(username);

		if (user == null) {
			throw new ServiceAuthorizeException("没有用户名为[" + username + "]的用户存在。");
		}
		List<UserRole> userRoles = authorizeMapper.getUserRoleRelationsByUserId(user.getId());
		List<Integer> roleIds = new ArrayList<Integer>();

		for (UserRole userRole : userRoles) {
			roleIds.add(userRole.getRoleId());
		}

		if (!roleIds.isEmpty()) {
			user.getRoles().addAll(authorizeMapper.getRoleByIds(roleIds));
		}

		return user;
	}

	public List<User> getAllUsers(RowBounds rb) {
		List<User> users = authorizeMapper.getAllUsers(rb);
		List<Role> roles = authorizeMapper.getAllRole(new RowBounds());
		List<UserRole> userRoles = authorizeMapper.getUserRoleAllRelations();

		for (User user : users) {
			for (UserRole userRole : userRoles) {
				if (user.getId() == userRole.getUserId()) {
					for (Role role : roles) {
						if (userRole.getRoleId() == role.getId()) {
							user.getRoles().add(role);
						}
					}
				}
			}
		}

		return users;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param roleId
	 * @return
	 * @throws ServiceBusinessException
	 */

	public Role getRoleById(int roleId) throws ServiceBusinessException {
		List<Role> roles = authorizeMapper.getRoleById(roleId);

		if (roles == null || roles.isEmpty()) {
			handleBusinessException("未找到指定的的Role信息");
		}

		Role role = roles.get(0);// 通过ID查到的角色只会有0个或者1个

		List<RoleResource> roleResources = authorizeMapper.getRoleResourceRelations(role.getId());

		List<Integer> resourceIds = new ArrayList<Integer>();

		for (RoleResource roleResource : roleResources) {
			resourceIds.add(roleResource.getResourceId());
		}

		if (!resourceIds.isEmpty()) {
			role.getResources().addAll(authorizeMapper.getResourcesByIds(resourceIds));
		}

		return role;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param rb
	 * @return
	 */

	public List<Role> getAllRoles(RowBounds rb) {
		List<Role> roles = authorizeMapper.getAllRole(rb);
		List<Resource> resources = authorizeMapper.getAllResources(new RowBounds());
		List<RoleResource> roleResources = authorizeMapper.getAllRoleResourceRelations();

		for (Role role : roles) {
			for (RoleResource roleResource : roleResources) {
				if (role.getId() == roleResource.getRoleId()) {
					for (Resource resource : resources) {
						if (resource.getId() == roleResource.getResourceId()) {
							role.getResources().add(resource);
						}
					}
				}
			}
		}
		return roles;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param user
	 * @throws ServiceBusinessException
	 */

	public void insertUser(User user) throws ServiceBusinessException {
		User exist = authorizeMapper.getUserByName(user.getUsername());

		if (exist != null) {
			handleBusinessException(MessageFormat.format("已经存在用户名为[{0}]的用户", user.getUsername()));
		}

		user.setPassword(MD5Util.MD5(user.getPassword()).toLowerCase());

		authorizeMapper.insertUser(user);
		exist = authorizeMapper.getUserByName(user.getUsername());

		UserRole userRole = null;

		for (Role role : user.getRoles()) {
			userRole = new UserRole();
			userRole.setUserId(exist.getId());
			userRole.setRoleId(role.getId());
			authorizeMapper.insertUserRole(userRole);
		}
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param role
	 * @throws ServiceBusinessException
	 */

	public void insertRole(Role role) throws ServiceBusinessException {
		Role exist = authorizeMapper.getRoleByName(role.getRoleName());
		if (exist != null) {
			handleBusinessException(MessageFormat.format("已经存在名为[{0}]的权限", role.getRoleName()));
		}

		authorizeMapper.insertRole(role);

		exist = authorizeMapper.getRoleByName(role.getRoleName());

		RoleResource roleResource = null;
		for (Resource resource : role.getResources()) {
			roleResource = new RoleResource();
			roleResource.setRoleId(exist.getId());
			roleResource.setResourceId(resource.getId());

			authorizeMapper.insertRoleResource(roleResource);
		}

	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param user
	 */

	public void updateUser(User user) throws ServiceBusinessException {
		User exist = authorizeMapper.getUserByName(user.getUsername());
		if (exist == null) {
			handleBusinessException(MessageFormat.format("不存在用户名为[{0}]的用户", user.getUsername()));
		}
		if (user.getPassword().equals(UNIQUE_PASSWORD)) {
			user.setPassword(exist.getPassword());
		} else {
			user.setPassword(MD5Util.MD5(user.getPassword()).toLowerCase());
		}
		authorizeMapper.updateUser(user);

		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			authorizeMapper.removeUserRoleByUserId(user.getId());

			UserRole userRole = null;

			for (Role role : user.getRoles()) {
				userRole = new UserRole();
				userRole.setUserId(exist.getId());
				userRole.setRoleId(role.getId());
				authorizeMapper.insertUserRole(userRole);
			}
		}
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param role
	 */

	public void udpateRole(Role role) throws ServiceBusinessException {
		Role exist = authorizeMapper.getRoleByName(role.getRoleName());
		if (exist == null) {
			handleBusinessException(MessageFormat.format("不存在名为[{0}]的权限", role.getRoleName()));
		}

		authorizeMapper.updateRole(role);
		authorizeMapper.removeRoleResourceByRoleId(role.getId());

		RoleResource roleResource = null;
		for (Resource resource : role.getResources()) {
			roleResource = new RoleResource();
			roleResource.setRoleId(exist.getId());
			roleResource.setResourceId(resource.getId());

			authorizeMapper.insertRoleResource(roleResource);
		}
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param userId
	 */

	public void deleteUser(int userId) {
		authorizeMapper.removeUser(userId);
		authorizeMapper.removeUserRoleByUserId(userId);
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param roleId
	 * @throws ServiceBusinessException
	 */

	public void deleteRole(int roleId) throws ServiceBusinessException {
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(roleId);

		List<UserRole> userRoles = authorizeMapper.getUserRoleRelationsByRoleIds(roleIds);
		if (CollectionUtils.isNotEmpty(userRoles)) {
			handleBusinessException("由于角色绑定了用户，暂时不能删除。");
		}

		authorizeMapper.removeRole(roleId);
		authorizeMapper.removeUserRoleByRoleId(roleId);
		authorizeMapper.removeRoleResourceByRoleId(roleId);
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param resourceId
	 * @return
	 * @throws ServiceAuthorizeException
	 */

	public List<Resource> getNavigationResources(String resourceId) throws ServiceAuthorizeException {
		String username = SecurityContextUtil.getUserName();
		List<Role> currentroles = new ArrayList<Role>();

		currentroles = SecurityContextUtil.getRoleByUser(username);

		List<Integer> currentroleIds = new ArrayList<Integer>();
		for (Role role : currentroles) {
			currentroleIds.add(role.getId());
		}
		if (CollectionUtils.isEmpty(currentroleIds))
			currentroleIds = null;
		List<Resource> resources = authorizeMapper.getNavigationResources(Integer.valueOf(resourceId), currentroleIds);

		Set<Integer> resourceIds = new HashSet<Integer>();

		for (Resource resource : resources) {
			resourceIds.add(resource.getId());
		}

		List<RoleResource> roleResources = new ArrayList<RoleResource>();

		if (!resourceIds.isEmpty()) {
			roleResources
					.addAll(authorizeMapper.getRoleResourceRelationsByResourceIds(new ArrayList<Integer>(resourceIds)));
		}

		Set<Integer> roleIds = new HashSet<Integer>();

		for (RoleResource roleResource : roleResources) {
			roleIds.add(roleResource.getRoleId());
		}

		List<Role> roles = new ArrayList<Role>();

		if (!roleIds.isEmpty()) {
			roles.addAll(authorizeMapper.getRoleByIds(new ArrayList<Integer>(roleIds)));
		}

		for (final Resource resource : resources) {
			for (RoleResource roleResource : roleResources) {
				if (resource.getId() == roleResource.getResourceId()) {
					for (Role role : roles) {
						if (roleResource.getRoleId() == role.getId()) {
							resource.getRoles().add(role);
						}
					}
				}
			}
		}

		return resources;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @return
	 * @throws ServiceAuthorizeException
	 */

	public List<Resource> getMainNavigationResources() throws ServiceAuthorizeException {
		String username = SecurityContextUtil.getUserName();
		List<Role> currentroles = new ArrayList<Role>();

		currentroles = SecurityContextUtil.getRoleByUser(username);

		List<Integer> currentroleIds = new ArrayList<Integer>();
		for (Role role : currentroles) {
			currentroleIds.add(role.getId());
		}
		if (CollectionUtils.isEmpty(currentroleIds))
			currentroleIds = null;
		List<Resource> resources = authorizeMapper.getMainNavigationResources(currentroleIds);

		Set<Integer> resourceIds = new HashSet<Integer>();

		for (Resource resource : resources) {
			resourceIds.add(resource.getId());
		}

		List<RoleResource> roleResources = new ArrayList<RoleResource>();

		if (CollectionUtils.isNotEmpty(resourceIds)) {
			roleResources
					.addAll(authorizeMapper.getRoleResourceRelationsByResourceIds(new ArrayList<Integer>(resourceIds)));
		}

		Set<Integer> roleIds = new HashSet<Integer>();

		for (RoleResource roleResource : roleResources) {
			roleIds.add(roleResource.getRoleId());
		}

		List<Role> roles = new ArrayList<Role>();

		if (!roleIds.isEmpty()) {
			roles.addAll(authorizeMapper.getRoleByIds(new ArrayList<Integer>(roleIds)));
		}

		for (final Resource resource : resources) {
			for (RoleResource roleResource : roleResources) {
				if (resource.getId() == roleResource.getResourceId()) {
					for (Role role : roles) {
						if (roleResource.getRoleId() == role.getId()) {
							resource.getRoles().add(role);
						}
					}
				}
			}
		}

		return resources;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param rb
	 * @return
	 */

	public List<Resource> getAllResourcesAsTree(RowBounds rb) {
		List<Resource> resources = this.getAllResourcesAsList(rb);

		List<Resource> ret = new ArrayList<Resource>();
		for (Resource resource : resources) {
			if (resource.getLevel() == LEVEL_FIRST_NAVIGATION_RESOURCE) {
				resource.getResources().addAll(this.findNextLevelResources(resource.getId(), resources));
				ret.add(resource);
			}
		}

		return ret;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param rb
	 * @return
	 */

	public List<Resource> getAllResourcesAsTree(Integer resourceId, RowBounds rb) {
		List<Resource> resources = this.getAllResourcesAsList(rb);

		return this.findNextLevelResources(resourceId, resources);
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @return
	 */

	public List<Resource> getAllResourcesAsTreeAndNotValid() {
		List<Resource> resources = authorizeMapper.getResourcesByLevel(LEVEL_ROOT_RESOURCE);

		List<Resource> ret = new ArrayList<Resource>();
		resources.get(0).getResources().addAll(getResourcesAsTreeAndNotValidByParentId(resources.get(0).getId()));
		ret.addAll(resources);

		return ret;
	}

	public List<Resource> getResourcesAsTreeAndNotValidByParentId(int parentId) {
		List<Resource> resources = this.getResourcesAsListWithNotValidByParentId();

		List<Resource> ret = new ArrayList<Resource>();
		for (Resource resource : resources) {
			if (resource.getParentId() == parentId) {
				resource.getResources().addAll(this.findNextLevelResources(resource.getId(), resources));
				ret.add(resource);
			}
		}

		return ret;
	}

	public List<Resource> getResourcesAsListWithNotValidByParentId() {
		List<Resource> resources = authorizeMapper.getAllResourcesWithNotValid();
		List<RoleResource> roleResources = authorizeMapper.getAllRoleResourceRelations();
		List<Role> roles = authorizeMapper.getAllRole(new RowBounds());

		for (final Resource resource : resources) {
			for (RoleResource roleResource : roleResources) {
				if (resource.getId() == roleResource.getResourceId()) {
					for (Role role : roles) {
						if (roleResource.getRoleId() == role.getId()) {
							resource.getRoles().add(role);
						}
					}
				}
			}
		}

		return resources;
	}

	public List<Resource> getAllResourcesAsList(RowBounds rb) {
		List<Resource> resources = authorizeMapper.getAllResources(rb);
		List<RoleResource> roleResources = authorizeMapper.getAllRoleResourceRelations();
		List<Role> roles = authorizeMapper.getAllRole(new RowBounds());

		for (final Resource resource : resources) {
			for (RoleResource roleResource : roleResources) {
				if (resource.getId() == roleResource.getResourceId()) {
					for (Role role : roles) {
						if (roleResource.getRoleId() == role.getId()) {
							resource.getRoles().add(role);
						}
					}
				}
			}
		}

		return resources;
	}

	private List<Resource> findNextLevelResources(int parentId, List<Resource> resources) {
		List<Resource> list = new ArrayList<Resource>();
		for (Resource resource : resources) {
			if (resource.getParentId() == parentId) {
				List<Resource> childResources = this.findNextLevelResources(resource.getId(), resources);
				if (childResources != null && !childResources.isEmpty()) {
					resource.getResources().addAll(childResources);
				}
				list.add(resource);
			}
		}
		return list;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param resourceId
	 * @return
	 * @throws ServiceBusinessException
	 */

	public Resource getResourceById(int resourceId) throws ServiceBusinessException {

		Resource resource = authorizeMapper.getResourcesById(resourceId);
		if (resource == null) {
			handleBusinessException("未找到指定的的Resource信息");
		}

		List<RoleResource> roleResources = authorizeMapper.getRoleResourceRelationsByResourceId(resourceId);

		List<Integer> roleIds = new ArrayList<Integer>();

		for (RoleResource roleResource : roleResources) {
			roleIds.add(roleResource.getRoleId());
		}

		if (!roleIds.isEmpty()) {
			resource.getRoles().addAll(authorizeMapper.getRoleByIds(roleIds));
		}

		return resource;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws ServiceAuthorizeException
	 */

	@Transactional
	public void insertNewResource(ResourceRequest request) throws ServiceAuthorizeException {
		Resource resource = new Resource();

		resource.setParentId(request.getParentsResource());
		resource.setLevel(request.getParentsLevel() + 1);
		resource.setPriority(request.getPriority());
		resource.setResourceType(request.getResourceType());
		resource.setImageUrl(request.getImageUrl());
		resource.setDescription(request.getResourceName());
		resource.setHasFloor(request.isHasFloor());
		resource.setFlex(request.isFlex());
		resource.setFlexAuthority(false);
		resource.setUpdateUser(SecurityContextUtil.getUserName());
		resource.setUpdateTime(DateUtil.currentTimestamp2String(DateUtil.PATTERN_STANDARD));
		resource.setIfValid(request.isValidate());
		resource.setIfGroup(request.isHasGroup());
		resource.setLink(request.getLink());

		authorizeMapper.insertResource(resource);

		Resource insertedResource = authorizeMapper.getResource(resource.getParentId(), resource.getDescription(),
				resource.getLevel());

		if (request.isHasFloor()) {
			int count = 1;
			for (ResourceRequest floor : request.getResources()) {
				if (floor.isFloorCheck()) {
					Resource floorResource = new Resource();
					floorResource.setPriority(count++);
					floorResource.setParentId(insertedResource.getId());
					floorResource.setLink(floor.getLink());
					floorResource.setLevel(insertedResource.getLevel() + 1);
					floorResource.setResourceType(RESOURCE_TYPE_URL);
					// floorResource.setImageUrl(request.getImageUrl());
					floorResource.setDescription(floor.getResourceName());
					floorResource.setUpdateUser(SecurityContextUtil.getUserName());
					floorResource.setUpdateTime(DateUtil.currentTimestamp2String(DateUtil.PATTERN_STANDARD));
					floorResource.setIfValid(true);
					floorResource.setFlexAuthority(false);

					authorizeMapper.insertResource(floorResource);
				}
			}
		}

		if (request.isFlex()) {
			insertFlexPanels(insertedResource.getId(), insertedResource.getLevel() + 1);
		}
	}

	private void insertFlexPanels(int parentId, int level) {
		Resource editResource = new Resource();
		editResource.setPriority(1);
		editResource.setParentId(parentId);
		editResource.setLevel(level);
		editResource.setResourceType(RESOURCE_TYPE_FLEX);
		editResource.setDescription(FLEX_AUTH_EDIT);
		editResource.setHasFloor(false);
		editResource.setFlex(false);
		editResource.setFlexAuthority(true);
		editResource.setUpdateUser(SecurityContextUtil.getUserName());
		editResource.setUpdateTime(DateUtil.currentTimestamp2String(DateUtil.PATTERN_STANDARD));
		editResource.setIfValid(true);
		authorizeMapper.insertResource(editResource);

		Resource controllerResource = new Resource();
		controllerResource.setPriority(2);
		controllerResource.setParentId(parentId);
		controllerResource.setLevel(level);
		controllerResource.setResourceType(RESOURCE_TYPE_FLEX);
		controllerResource.setDescription(FLEX_AUTH_CONTROLLER);
		controllerResource.setHasFloor(false);
		controllerResource.setFlex(false);
		controllerResource.setFlexAuthority(true);
		controllerResource.setUpdateUser(SecurityContextUtil.getUserName());
		controllerResource.setUpdateTime(DateUtil.currentTimestamp2String(DateUtil.PATTERN_STANDARD));
		controllerResource.setIfValid(true);
		authorizeMapper.insertResource(controllerResource);
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws ServiceBusinessException
	 */

	public void updateResource(ResourceRequest request) throws ServiceBusinessException {

		Resource resource = authorizeMapper.getResourcesById(request.getId());
		if (resource == null) {
			handleBusinessException("未找到指定的的Resource信息");
		}
		boolean existFlex = resource.isFlex();
		boolean hasFloor = resource.isHasFloor();

		resource.setPriority(request.getPriority());
		resource.setResourceType(request.getResourceType());
		resource.setImageUrl(request.getImageUrl());
		resource.setLink(request.getLink());
		resource.setDescription(request.getResourceName());
		resource.setUpdateUser(SecurityContextUtil.getUserName());
		resource.setUpdateTime(DateUtil.currentTimestamp2String(DateUtil.PATTERN_STANDARD));
		resource.setIfValid(request.isValidate());
		resource.setHasFloor(request.isHasFloor());
		resource.setFlex(request.isFlex());
		resource.setIfGroup(request.isHasGroup());

		authorizeMapper.updateResource(resource);

		/**
		 * --------FLEX----------
		 */
		if (existFlex && !resource.isFlex()) {
			removeFlexChildrens(resource.getId());
		}

		if (!existFlex && resource.isFlex()) {
			insertFlexPanels(resource.getId(), resource.getLevel() + 1);
		}

		/**
		 * --------Floor---------
		 */
		if (hasFloor && !request.isHasFloor()) {
			removeChildrensByParentId(request.getId());
		}

		if (request.isHasFloor()) {
			int count = 1;
			List<Integer> noExistFloorIds = new ArrayList<Integer>();
			for (ResourceRequest floor : request.getResources()) {
				if (floor.isFloorCheck()) {
					Resource floorResource = new Resource();
					floorResource.setId(floor.getId());
					floorResource.setPriority(count++);
					floorResource.setParentId(resource.getId());
					floorResource.setLink(floor.getLink());
					floorResource.setLevel(resource.getLevel() + 1);
					floorResource.setResourceType(RESOURCE_TYPE_URL);
					// floorResource.setImageUrl(request.getImageUrl());
					floorResource.setDescription(floor.getResourceName());
					floorResource.setUpdateUser(SecurityContextUtil.getUserName());
					floorResource.setUpdateTime(DateUtil.currentTimestamp2String(DateUtil.PATTERN_STANDARD));
					floorResource.setIfValid(true);
					floorResource.setFlexAuthority(false);

					if (floorResource.getId() != 0) {
						authorizeMapper.updateResource(floorResource);
					} else {
						authorizeMapper.insertResource(floorResource);
					}

					noExistFloorIds.add(floorResource.getId());
				}
			}

			List<Integer> notExistAnyMoreFloors = authorizeMapper
					.getFloorResourceByParentIdAndNotInIds(resource.getId(), noExistFloorIds);
			if (CollectionUtils.isNotEmpty(notExistAnyMoreFloors)) {
				removeResourceAndChildrens(notExistAnyMoreFloors.toArray(new Integer[] {}));
			}
		}
	}

	private void removeResourceAndChildrens(Integer... resourceIds) {
		this.removeChildrensByParentId(resourceIds);

		authorizeMapper.removeRoleResourceByResourceIds(Arrays.asList(resourceIds));
		authorizeMapper.removeResourceByIds(Arrays.asList(resourceIds));
	}

	private void removeChildrensByParentId(Integer... parentIds) {
		List<Integer> childrenIds = authorizeMapper.getResourceIdsByParentIds(Arrays.asList(parentIds));

		if (childrenIds != null && !childrenIds.isEmpty()) {
			removeChildrensByParentId(childrenIds.toArray(new Integer[] {}));

			authorizeMapper.removeRoleResourceByResourceIds(childrenIds);
			authorizeMapper.removeResourceByIds(childrenIds);
		}
	}

	private void removeFlexChildrens(int parentId) {
		authorizeMapper.removeResourceByParentId(parentId, 1);
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBusinessException
	 */

	public void removeResource(int id) throws ServiceBusinessException {
		Resource resource = authorizeMapper.getResourcesById(id);
		if (resource == null) {
			handleBusinessException("未找到指定的的Resource信息");
		}

		List<Resource> childResources = authorizeMapper.getResourceByParentId(resource.getId(), null);

		if (childResources != null && !childResources.isEmpty()) {
			handleBusinessException("要删除的资源绑定了子资源，请先确认所有关联子资源已被删除。");
		}

		authorizeMapper.removeResourceById(id);
	}

	private void handleBusinessException(String error) throws ServiceBusinessException {
		LogUtil.error(log, "出现错误：[" + error + "]");
		throw new ServiceBusinessException(error);
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @return
	 */

	public List<Floor> getAllFloors() {
		return authorizeMapper.getFloors();
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param resourceId
	 * @return
	 * @throws ServiceBusinessException
	 */

	public List<Resource> getResourcesByParentId(int parentId) {
		return authorizeMapper.getResourceByParentId(parentId, null);
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param parentId
	 * @return
	 */

	public List<Resource> getResourceByParentIdWithCurrentRole(int parentId) {
		List<Role> roles = SecurityContextUtil.getCurrentRoles();

		List<RoleResource> roleResources = new ArrayList<RoleResource>();
		for (Role role : roles) {
			List<RoleResource> roleResource = authorizeMapper.getRoleResourceRelations(role.getId());
			if (roleResource != null) {
				roleResources.addAll(roleResource);
			}
		}

		List<Integer> validatedResourceIds = new ArrayList<Integer>();

		for (RoleResource roleResource : roleResources) {
			validatedResourceIds.add(roleResource.getResourceId());
		}

		List<Resource> resources = authorizeMapper.getResourceByParentId(parentId, 0);
		List<Resource> ret = new ArrayList<Resource>();

		for (Resource resource : resources) {
			if (validatedResourceIds.contains(resource.getId())) {
				ret.add(resource);
			}
		}

		return ret;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param parentId
	 * @return
	 */

	public List<Resource> getAllResourceWithCurrentRole(int parentId) {
		List<Role> roles = SecurityContextUtil.getCurrentRoles();

		List<RoleResource> roleResources = new ArrayList<RoleResource>();
		for (Role role : roles) {
			List<RoleResource> roleResource = authorizeMapper.getRoleResourceRelations(role.getId());
			if (roleResource != null) {
				roleResources.addAll(roleResource);
			}
		}

		List<Integer> validatedResourceIds = new ArrayList<Integer>();

		for (RoleResource roleResource : roleResources) {
			validatedResourceIds.add(roleResource.getResourceId());
		}

		List<Resource> resources = authorizeMapper.getResourceByParentId(parentId, 0);
		List<Resource> ret = new ArrayList<Resource>();

		for (Resource resource : resources) {
			if (validatedResourceIds.contains(resource.getId())) {
				resource.getResources().addAll(getAllResourceWithCurrentRole(resource.getId()));
				ret.add(resource);
			}
		}

		return ret;
	}

	public ModelAndView getResourceEditPath(ModelAndView mav, int id) {
		Resource resource = authorizeMapper.getResourcesById(id);
		if (resource != null) {
			mav.addObject("parentsLevel", resource.getLevel());
			mav.addObject("parentsResourcePath", getResourceEditPath(resource));
		}
		return mav;
	}

	public String getResourceEditPath(Resource resource) {
		String ret = "/ ";

		if (resource != null) {
			ret = resource.getDescription() + " / ";

			String parent = getResourceEditPath(authorizeMapper.getResourcesById(resource.getParentId()));
			if (parent != null) {
				ret = parent + ret;
			}

		}

		return ret;
	}

	public String[] getFlexAuthorize(int id) {
		List<Role> roles = SecurityContextUtil.getCurrentRoles();

		List<RoleResource> roleResources = new ArrayList<RoleResource>();
		for (Role role : roles) {
			List<RoleResource> roleResource = authorizeMapper.getRoleResourceRelations(role.getId());
			if (roleResource != null) {
				roleResources.addAll(roleResource);
			}
		}

		List<Integer> validatedResourceIds = new ArrayList<Integer>();

		for (RoleResource roleResource : roleResources) {
			validatedResourceIds.add(roleResource.getResourceId());
		}

		List<Resource> resources = authorizeMapper.getResourceByParentId(id, null);

		List<String> ret = new ArrayList<String>();

		if (resources != null && !resources.isEmpty()) {

			for (Resource resource : resources) {
				if (validatedResourceIds.contains(resource.getId())) {
					if (resource.getDescription().equals(FLEX_AUTH_EDIT)) {
						ret.add(FLEX_AUTH_EDIT_CODE);
					} else if (resource.getDescription().equals(FLEX_AUTH_CONTROLLER)) {
						ret.add(FLEX_AUTH_CONTROLLER_CODE);
					}
				}
			}
		}
		return ret.toArray(new String[] {});
	}

}

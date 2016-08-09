/**
 * Title: AuthorizeMapper.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.authorize.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.simon.framework.sys.authorize.beans.Floor;
import com.simon.framework.sys.authorize.beans.Resource;
import com.simon.framework.sys.authorize.beans.Role;
import com.simon.framework.sys.authorize.beans.RoleResource;
import com.simon.framework.sys.authorize.beans.User;
import com.simon.framework.sys.authorize.beans.UserRole;

/**
 * @ClassName: AuthorizeMapper <br>
 * @Description: 登陆认证管理的数据库操作接口 <br>
 */
public interface AuthorizeMapper {
	/**
	 * ----------------USER----------------------
	 */
	User getUserByName(@Param("username") String username);

	List<User> getAllUsers(RowBounds rb);

	void insertUser(User user);

	void updateUser(User user);

	void removeUser(@Param("userId") int userId);

	/**
	 * ----------------ROLE----------------------
	 */
	List<Role> getRoleById(@Param("roleId") int roleId);

	List<Role> getRoleByIds(@Param("roleIds") List<Integer> roleIds);

	Role getRoleByName(@Param("roleName") String roleName);

	List<Role> getAllRole(RowBounds rb);

	void insertRole(Role role);

	void updateRole(Role role);

	void removeRole(@Param("roleId") int roleId);

	/**
	 * ----------------USER_ROLE----------------------
	 */
	List<UserRole> getUserRoleRelationsByUserId(@Param("userId") int userId);

	List<UserRole> getUserRoleRelationsByUserIds(@Param("userIds") List<Integer> userIds);

	List<UserRole> getUserRoleRelationsByRoleId(@Param("roleId") int roleId);

	List<UserRole> getUserRoleRelationsByRoleIds(@Param("roleIds") List<Integer> roleIds);

	List<UserRole> getUserRoleAllRelations();

	void insertUserRole(UserRole userRole);

	void removeUserRoleByUserId(@Param("userId") int userId);

	void removeUserRoleByRoleId(@Param("roleId") int roleId);

	/**
	 * ----------------ROLE_RESOURCE----------------------
	 */
	List<RoleResource> getRoleResourceRelations(@Param("roleId") int roleId);

	List<RoleResource> getRoleResourceRelationsByResourceId(@Param("resourceId") int resourceId);

	List<RoleResource> getRoleResourceRelationsByResourceIds(@Param("resourceIds") List<Integer> resourceIds);

	List<RoleResource> getAllRoleResourceRelations();

	void insertRoleResource(RoleResource roleResource);

	void removeRoleResourceByRoleId(@Param("roleId") int roleId);

	void removeRoleResourceByResourceId(@Param("resourceId") int resourceId);

	void removeRoleResourceByResourceIds(@Param("resourceIds") List<Integer> resourceIds);

	/**
	 * ----------------RESOURCE----------------------
	 */

	/**
	 * 
	 * <p>
	 * Discription:[通过一级导航ID获得左侧导航信息]
	 * </p>
	 * 
	 * @return
	 */
	public List<Resource> getNavigationResources(@Param("resourceId") int resourceId,
			@Param("roleIds") List<Integer> roleIds);

	/**
	 * 
	 * <p>
	 * Discription:[获得所有的一级导航]
	 * </p>
	 * 
	 * @return
	 */
	public List<Resource> getMainNavigationResources(@Param("roleIds") List<Integer> roleIds);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @param rb
	 * @return
	 */
	public List<Resource> getAllResources(RowBounds rb);

	public List<Resource> getAllResourcesWithNotValid();

	public Resource getResourcesById(@Param("resourceId") int resourceId);

	public List<Resource> getResourcesByLevel(@Param("level") int level);

	public List<Resource> getResourcesByIds(@Param("resourceIds") List<Integer> resourceIds);

	public Resource getResource(@Param("parentId") int parentId, @Param("resourceName") String resourceName,
			@Param("resourceLevel") int resourceLevel);

	public void insertResource(Resource resource);

	public void updateResource(Resource resource);

	public void removeResourceById(@Param("resourceId") int resourceId);

	public void removeResourceByIds(@Param("resourceIds") List<Integer> resourceIds);

	public void removeResourceByParentId(@Param("parentId") int parentId,
			@Param("flexAuthority") Integer flexAuthority);

	public void removeResourceByParentIds(@Param("parentIds") List<Integer> parentIds);

	public List<Resource> getResourceByParentId(@Param("parentId") int parentId,
			@Param("flexAuthority") Integer flexAuthority);

	public List<Integer> getResourceIdsByParentId(@Param("parentId") int parentId);

	public List<Integer> getResourceIdsByParentIds(@Param("parentIds") List<Integer> parentIds);

	public List<Integer> getFloorResourceByParentIdAndNotInIds(@Param("parentId") int parentId,
			@Param("resourceIds") List<Integer> resourceIds);

	public List<Floor> getFloors();

}

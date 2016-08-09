/**
 * Title: SecurityContextUtil.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.authorize.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.simon.framework.exception.ServiceAuthorizeException;
import com.simon.framework.spring.SpringContextHolder;
import com.simon.framework.sys.authorize.beans.Role;
import com.simon.framework.sys.authorize.service.AuthorizeService;

/**
 * @ClassName: SecurityContextUtil <br>
 * @Description: 权限和认证信息获取工具类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public final class SecurityContextUtil {

	private static final String ANONYMOUS = "ANONYMOUS";

	/**
	 * 
	 * <p>
	 * Discription:[获取当前用户详细信息]
	 * </p>
	 * 
	 * @return
	 */
	private static UserDetails getUserDetails() throws AccessDeniedException {

		UserDetails userDetails = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (null != auth) {
			if (auth.getPrincipal() instanceof UserDetails) {
				userDetails = (UserDetails) auth.getPrincipal();
			}
		}

		return userDetails;
	}

	/**
	 * 
	 * <p>
	 * Discription:[获取当前用户名]
	 * </p>
	 * 
	 * @return
	 */
	public static String getUserName() {
		return getUserDetails() == null ? ANONYMOUS : getUserDetails().getUsername();
	}

	/**
	 * 
	 * <p>
	 * Discription:[获取当前用户密码]
	 * </p>
	 * 
	 * @return
	 */
	public static String getPassword() {
		return getUserDetails() == null ? ANONYMOUS : getUserDetails().getPassword();
	}

	/**
	 * 
	 * <p>
	 * Discription:[获取当前用户权限列表]
	 * </p>
	 * 
	 * @return
	 */
	public static List<String> getCurrentUserAuthorities() {

		List<String> list = null;
		if (getUserDetails() != null) {
			list = new ArrayList<String>();
			Collection<? extends GrantedAuthority> authorities = getUserDetails().getAuthorities();
			for (GrantedAuthority authoritie : authorities) {
				list.add(authoritie.getAuthority());
			}
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * Discription:[通过Spring获取AuthorizeService对象]
	 * </p>
	 * 
	 * @return
	 */
	private static AuthorizeService getAuthorizeService() {
		return SpringContextHolder.getBean("authorizeService");
	}

	/**
	 * 
	 * <p>
	 * Discription:[通过用户名获取用户权限信息]
	 * </p>
	 * 
	 * @param username
	 * @return
	 * @throws ServiceAuthorizeException
	 */
	public static List<Role> getRoleByUser(String username) throws ServiceAuthorizeException {
		return getAuthorizeService().getUser(username).getRoles();
	}

	public static List<Role> getCurrentRoles() {
		String username = SecurityContextUtil.getUserName();
		List<Role> currentroles = new ArrayList<Role>();
		try {
			currentroles = SecurityContextUtil.getRoleByUser(username);
		} catch (ServiceAuthorizeException e) {
			e.printStackTrace();
		}
		return currentroles;
	}

	public static boolean isAdmin() {
		boolean flag = false;
		List<Role> roles = getCurrentRoles();
		for (Role role : roles) {
			if (role.getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}

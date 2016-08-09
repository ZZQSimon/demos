/**
 * Title: CustomUserDetailsService.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.authorize.security;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.simon.framework.sys.authorize.beans.Role;
import com.simon.framework.sys.authorize.beans.User;
import com.simon.framework.sys.authorize.service.AuthorizeService;

/**
 * @ClassName: CustomUserDetailsService <br>
 * @Description: 给Spring提供的用户信息的获取类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class CustomUserDetailsService implements UserDetailsService {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsService.class);

	/**
	 * The authorizeService.
	 */
	private AuthorizeService authorizeService;

	/**
	 * 
	 * <p>
	 * Discription:[通过UserName加载整个User对象]
	 * </p>
	 * 
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		UserDetails userDetails = null;

		try {

			User user = authorizeService.getUser(username);

			userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					getAuthorities(user.getRoles()));

			LOGGER.info(MessageFormat.format("User [{0}] login the page at {1}", username, new Date()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Error in retrieving user");
		}

		return userDetails;
	}

	/**
	 * 
	 * <p>
	 * Discription:[匹配Role]
	 * </p>
	 * 
	 * @param authorizes
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities(List<Role> authorizes) {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(authorizes.size());
		for (Role authorize : authorizes) {
			if (null != authorize)
				authList.add(new SimpleGrantedAuthority(authorize.getRoleName()));
		}
		return authList;
	}

	public AuthorizeService getAuthorizeService() {
		return authorizeService;
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

}

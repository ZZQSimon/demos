/**
 * Title: SecurityMetadataSourceService.java <br>
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

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.simon.framework.sys.authorize.beans.Resource;
import com.simon.framework.sys.authorize.beans.Role;
import com.simon.framework.sys.authorize.service.AuthorizeService;
import com.simon.framework.util.StringUtils;

/**
 * @ClassName: SecurityMetadataSourceService <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

public class SecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	private AuthorizeService authorizeService;

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		List<Resource> resources = authorizeService.getAllResourcesAsList(new RowBounds());
		// List<Role> roles = resources.;

		String url = ((FilterInvocation) object).getRequestUrl();
		List<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>();
		for (Resource resource : resources) {
			if (StringUtils.isEmpty(resource.getLink())) {
				continue;
			}
			if (new AntPathMatcher().match(resource.getLink() + "*", url)) {
				for (Role role : resource.getRoles()) {
					attrs.add(new SecurityConfig(role.getRoleName()));
				}
				// 该资源没有分配角色的时候，默认只有 admin的用户才能访问
				if (attrs.isEmpty()) {
					attrs.add(new SecurityConfig("ROLE_ADMIN"));
				}
				return attrs;
			}
		}
		// attrs.add(new SecurityConfig("ROLE_ADMIN"));
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

}

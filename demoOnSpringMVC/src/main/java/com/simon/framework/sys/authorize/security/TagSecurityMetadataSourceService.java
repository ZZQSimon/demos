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

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.simon.framework.exception.ServiceBusinessException;
import com.simon.framework.sys.authorize.beans.Resource;
import com.simon.framework.sys.authorize.beans.Role;
import com.simon.framework.sys.authorize.service.AuthorizeService;

/**
 * @ClassName: SecurityMetadataSourceService <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */

@Deprecated
public class TagSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
	private AuthorizeService authorizeService;

	// According to a URL, Find out permission configuration of this URL.
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		List<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>();
		Resource resourcetmp = null;
		String id = (String) object;
		try {
			resourcetmp = authorizeService.getResourceById(Integer.parseInt(id));
		} catch (ServiceBusinessException e) {
			e.printStackTrace();
		}
		if (resourcetmp != null && resourcetmp.getRoles() != null && !resourcetmp.getRoles().isEmpty()) {
			for (Role role : resourcetmp.getRoles()) {
				attrs.add(new SecurityConfig(role.getRoleName()));
			}
		}
		return attrs;
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

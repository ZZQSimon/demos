package com.simon.framework.sys.authorize.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class DatabaseRoleVoter implements AccessDecisionVoter<Object> {
    public boolean supports(ConfigAttribute arg0) {
        return true;
    }

    @SuppressWarnings("rawtypes")
	public boolean supports(Class arg0) {
        System.out.println(this.getClass().getName() + ".supports(Class arg0)");
        return true;
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;
        
        for (ConfigAttribute configAttribute : attributes)
        {
            String needRole = ((SecurityConfig)configAttribute).getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities())
            {
                if (needRole.equals(ga.getAuthority()))
                { // ga is user's role.
                    result = ACCESS_GRANTED;
                }
            }
        }
        return result;
    }
}
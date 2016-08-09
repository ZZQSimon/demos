/**
 * Title: AuthorizeService.java <br>
 * Description:[For Neusoft IBMS product] <br>
 * Date: 2015年1月14日 <br>
 * Copyright (c) 2015 Neusoft <br>
 * 
 * @author Freud
 * @version V1.0
 */
package com.simon.framework.sys.authorize.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.servlet.ModelAndView;

import com.simon.framework.exception.ServiceAuthorizeException;
import com.simon.framework.exception.ServiceBusinessException;
import com.simon.framework.sys.authorize.beans.Floor;
import com.simon.framework.sys.authorize.beans.Resource;
import com.simon.framework.sys.authorize.beans.Role;
import com.simon.framework.sys.authorize.beans.User;
import com.simon.framework.sys.authorize.model.ResourceRequest;

/**
 * @ClassName: AuthorizeService <br>
 * @Description: 权限信息管理服务接口层 <br>
 * @date 2015年1月14日 上午11:24:09 <br>
 * 
 * @author Freud
 */
public interface AuthorizeService
{
    
    public static final String UNIQUE_PASSWORD = "9535B91966CFA19E013F6A0BECF37328001";
    
    public User getUser(String username)
        throws ServiceAuthorizeException;
    
    public List<User> getAllUsers(RowBounds rb);
    
    public Role getRoleById(int roleId)
        throws ServiceBusinessException;
    
    public List<Role> getAllRoles(RowBounds rb);
    
    public void insertUser(User user)
        throws ServiceBusinessException;
    
    public void insertRole(Role role)
        throws ServiceBusinessException;
    
    public void updateUser(User user)
        throws ServiceBusinessException;
    
    public void udpateRole(Role role)
        throws ServiceBusinessException;
    
    public void deleteUser(int userId)
        throws ServiceBusinessException;
    
    public void deleteRole(int roleId)
        throws ServiceBusinessException;
    
    public List<Resource> getNavigationResources(String resourceId)
        throws ServiceAuthorizeException;
    
    public List<Resource> getMainNavigationResources()
        throws ServiceAuthorizeException;
    
    public List<Resource> getResourcesAsTreeAndNotValidByParentId(int parentId);
    
    public List<Resource> getAllResourcesAsList(RowBounds rb);
    
    public List<Resource> getAllResourcesAsTree(RowBounds rb);
    
    public List<Resource> getAllResourcesAsTreeAndNotValid();
    
    public Resource getResourceById(int resourceId)
        throws ServiceBusinessException;
    
    public void insertNewResource(ResourceRequest request)
        throws ServiceAuthorizeException;
    
    public void updateResource(ResourceRequest request)
        throws Exception;
    
    public void removeResource(int id)
        throws Exception;
    
    public List<Floor> getAllFloors();
    
    public List<Resource> getResourcesByParentId(int parentId);
    
    public List<Resource> getResourceByParentIdWithCurrentRole(int parentId);
    
    public List<Resource> getAllResourceWithCurrentRole(int parentId);
    
    public ModelAndView getResourceEditPath(ModelAndView mav, int id);
    
    public String[] getFlexAuthorize(int id);
    
    List<Resource> getAllResourcesAsTree(Integer resourceId, RowBounds rb);
}

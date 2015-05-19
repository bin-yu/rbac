/**
 * 
 */
package org.binyu.rbac.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.binyu.rbac.daos.ResourceMapper;
import org.binyu.rbac.daos.ResourcePermissionMapper;
import org.binyu.rbac.daos.RoleMapper;
import org.binyu.rbac.dtos.Resource;
import org.binyu.rbac.dtos.ResourcePermission;
import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.exceptions.ServiceInputValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class RoleManagementService
{

  private static final Logger LOG = LoggerFactory
      .getLogger(RoleManagementService.class);
  @Autowired
  private ResourcePermissionMapper permRepo;
  @Autowired
  private RoleMapper roleRepo;

  @Autowired
  private ResourceMapper resRepo;

  public ResourcePermission getPermissionByRolesAndResource(
      List<String> roleList, String res)
  {
    LOG.info("Getting permission for " + roleList + " and resource[" + res
        + "]...");
    if (roleList.size() == 0)
    {
      LOG.info("Return no permission because the user has no role.");
      return new ResourcePermission(res, ResourcePermission.NONE);
    }
    List<ResourcePermission> permissions = permRepo
        .getResourcePermissionByRoleNamesAndResourceName(roleList, res);
    LOG.info("Permissions we get : " + permissions);
    return ResourcePermission.mergePermissions(res, permissions);
  }

  public Role[] getAllRoles()
  {
    return roleRepo.getAllRoles();
  }

  public Role addRole(Role role) throws ServiceInputValidationException
  {
    try
    {
      roleRepo.addRole(role);
      return role;
    }
    catch (DataIntegrityViolationException e)
    {
      String msg = "invalid input cause failure to insert the role.";
      LOG.info(msg, e);
      throw new ServiceInputValidationException(msg);
    }
  }

  public void deleteRole(String name)
  {
    roleRepo.deleteRole(name);
  }

  public Role getRoleByName(String roleName)
  {
    return roleRepo.getRoleByName(roleName);
  }

  public void deleteResourcePermissionsByRoleId(int roleId)
  {
    permRepo.deleteResourcePermissionsByRoleId(roleId);
  }

  public void addResourcePermissionToRole(int roleId, int resId,
      int permission)
  {
    permRepo.addResourcePermissionToRole(roleId, resId, permission);
  }

  public ResourcePermission[] getResourcePermissionByRoleName(String roleName)
  {
    return permRepo.getResourcePermissionByRoleName(roleName);
  }

  public String[] getRoleNamesByUsername(String username)
  {
    return roleRepo.getRoleNamesByUsername(username);
  }

  public void deleteRoleById(int id)
  {
    roleRepo.deleteRoleById(id);
  }

  public Role getRoleById(int roleId)
  {
    return roleRepo.getRoleById(roleId);
  }

  public ResourcePermission[] getResourcePermissionByRoleId(int id)
  {
    return permRepo.getResourcePermissionByRoleId(id);
  }

  public void setRolePermissions(int roleId, ResourcePermission[] permissions, boolean overwrite)
      throws ServiceInputValidationException
  {
    Role role = getRoleById(roleId);
    if (role == null)
    {
      throw new ServiceInputValidationException("role [id=" + roleId + "] not exist.");
    }

    if (overwrite)
    {
      deleteResourcePermissionsByRoleId(role.getId());
    }
    if (permissions != null && permissions.length > 0)
    {
      if (!overwrite)
      {
        // if not overwrite, merge existing array with new array, and delete existing array.
        ResourcePermission[] existPermArray = getResourcePermissionByRoleId(roleId);
        permissions = merge(permissions, existPermArray);
        deleteResourcePermissionsByRoleId(role.getId());
      }
      // add the new or merged array
      for (ResourcePermission permission : permissions)
      {
        Resource res = resRepo.getResourceByName(permission.getResource());
        if (res != null)
        {
          addResourcePermissionToRole(role.getId(), res.getId(),
              permission.getPermission());
        }
      }
    }
  }

  private ResourcePermission[] merge(ResourcePermission[] permissions, ResourcePermission[] existPermArray)
  {
    Map<String, List<ResourcePermission>> resultMap = new HashMap<String, List<ResourcePermission>>(permissions.length
        + existPermArray.length);
    List<ResourcePermission> all = new ArrayList<ResourcePermission>(permissions.length + existPermArray.length);
    all.addAll(Arrays.asList(permissions));
    all.addAll(Arrays.asList(existPermArray));
    for (ResourcePermission perm : all)
    {
      String resource = perm.getResource();
      List<ResourcePermission> permList = resultMap.get(resource);
      if (permList == null)
      {
        permList = new ArrayList<ResourcePermission>(1);
        resultMap.put(resource, permList);
      }
      permList.add(perm);
    }
    List<ResourcePermission> resultList = new ArrayList<ResourcePermission>(permissions.length + existPermArray.length);
    for (Entry<String, List<ResourcePermission>> entry : resultMap.entrySet())
    {
      resultList.add(ResourcePermission.mergePermissions(entry.getKey(), entry.getValue()));
    }
    return resultList.toArray(new ResourcePermission[0]);
  }

}

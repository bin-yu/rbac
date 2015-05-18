/**
 * 
 */
package org.binyu.rbac.services;

import java.util.List;

import org.binyu.rbac.daos.ResourcePermissionMapper;
import org.binyu.rbac.daos.RoleMapper;
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

  public Role[] getRolesByUserId(int id)
  {
    return roleRepo.getRolesByUserId(id);
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

  public Role getRolesByName(String roleName)
  {
    return roleRepo.getRolesByName(roleName);
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
}

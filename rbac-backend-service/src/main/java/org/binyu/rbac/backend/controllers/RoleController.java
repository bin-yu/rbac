/********************************************************************
 * File Name:    RoleController.java
 *
 * Date Created: Apr 19, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers;

import org.binyu.rbac.backend.controllers.dtos.ExtRole;
import org.binyu.rbac.dtos.Resource;
import org.binyu.rbac.dtos.ResourcePermission;
import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.exceptions.ServiceInputValidationException;
import org.binyu.rbac.services.ResourceManagementService;
import org.binyu.rbac.services.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
@RestController
@RequestMapping(value = "/roles")
public class RoleController
{
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  @Autowired
  private RoleManagementService roleSrv;
  @Autowired
  private ResourceManagementService resSrv;

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------

  @RequestMapping(method = RequestMethod.GET)
  public ExtRole[] getAllRoles()
  {
    Role[] roles = roleSrv.getAllRoles();
    ExtRole[] extRoles = new ExtRole[roles.length];
    for (int i = 0; i < roles.length; i++)
    {
      extRoles[i] = ExtRole.fromInternal(roles[i]);
    }
    return extRoles;
  }

  @RequestMapping(method = RequestMethod.POST)
  @Transactional(rollbackFor = Throwable.class)
  public ExtRole addRole(@RequestBody ExtRole extRole) throws ServiceInputValidationException
  {
    Role iRole = roleSrv.addRole(extRole.toInternal());
    return ExtRole.fromInternal(iRole);
  }

  @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
  @Transactional(rollbackFor = Throwable.class)
  public void deleteRole(@PathVariable String name)
  {
    roleSrv.deleteRole(name);
  }

  @RequestMapping(value = "/{roleName}/permissions", method = RequestMethod.PUT)
  @Transactional(rollbackFor = Throwable.class)
  public void setRolePermissions(
      @PathVariable String roleName,
      @RequestParam(required = false, defaultValue = "true") boolean overwrite,
      @RequestBody ResourcePermission[] permissions) throws Exception
  {
    Role role = roleSrv.getRolesByName(roleName);
    if (role == null)
    {
      throw new Exception("role [" + roleName + "] not exist.");
    }
    if (overwrite)
    {
      roleSrv.deleteResourcePermissionsByRoleId(role.getId());
    }
    for (ResourcePermission permission : permissions)
    {
      Resource res = resSrv.getResourceByName(permission.getResource());
      if (res != null)
      {
        roleSrv.addResourcePermissionToRole(role.getId(), res.getId(),
            permission.getPermission());
      }
    }
  }

  @RequestMapping(value = "/{roleName}/permissions", method = RequestMethod.GET)
  public ResourcePermission[] getRolePermissions(@PathVariable String roleName)
  {
    return roleSrv.getResourcePermissionByRoleName(roleName);
  }
  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

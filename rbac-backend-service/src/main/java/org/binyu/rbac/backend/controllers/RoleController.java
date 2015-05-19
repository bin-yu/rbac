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

import org.binyu.rbac.backend.controllers.dtos.ExtResourcePermission;
import org.binyu.rbac.backend.controllers.dtos.ExtRole;
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
    return ExtRole.fromInternal(roles);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @Transactional(rollbackFor = Throwable.class)
  public ExtRole getRoleById(@PathVariable int id) throws RestResourceNotFoundException
  {
    Role role = roleSrv.getRoleById(id);
    if (role == null)
    {
      throw new RestResourceNotFoundException("role not found for id[" + id + "].");
    }
    return ExtRole.fromInternal(role);
  }

  @RequestMapping(method = RequestMethod.POST)
  @Transactional(rollbackFor = Throwable.class)
  public ExtRole addRole(@RequestBody ExtRole extRole) throws ServiceInputValidationException
  {
    Role iRole = roleSrv.addRole(extRole.toInternal());
    return ExtRole.fromInternal(iRole);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @Transactional(rollbackFor = Throwable.class)
  public void deleteRole(@PathVariable int id)
  {
    roleSrv.deleteRoleById(id);
  }

  @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.PUT)
  @Transactional(rollbackFor = Throwable.class)
  public void setRolePermissions(
      @PathVariable int roleId,
      @RequestBody ExtResourcePermission[] permissions,
      @RequestParam(required = false, defaultValue = "true") boolean overwrite) throws Exception
  {
    ResourcePermission[] ipermissions = new ResourcePermission[permissions.length];
    for (int i = 0; i < permissions.length; i++)
    {
      ipermissions[i] = permissions[i].toInternal();
    }
    roleSrv.setRolePermissions(roleId, ipermissions, overwrite);

  }

  @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.GET)
  public ExtResourcePermission[] getRolePermissions(@PathVariable int roleId)
  {
    ResourcePermission[] permArray = roleSrv.getResourcePermissionByRoleId(roleId);
    return ExtResourcePermission.fromInternal(permArray);
  }

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

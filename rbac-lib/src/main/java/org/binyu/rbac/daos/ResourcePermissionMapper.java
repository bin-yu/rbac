/********************************************************************
 * File Name:    ResourcePermissionMapper.java
 *
 * Date Created: Apr 19, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.binyu.rbac.dtos.ResourcePermission;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */

public interface ResourcePermissionMapper
{
  // CONSTANTS ------------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  // ResourcePermission[] getResourcePermissionsByUser(String username);

  void addResourcePermissionToRole(@Param(value = "roleId") int roleId,
      @Param(value = "resourceId") int resourceId,
      @Param(value = "permission") int permission);

  ResourcePermission[] getResourcePermissionByRoleName(String roleName);

  void deleteResourcePermissionsByRoleId(int roleId);

  List<ResourcePermission> getResourcePermissionByRoleNamesAndResourceName(
      @Param(value = "roleList") List<String> roleList,
      @Param(value = "res") String res);

  ResourcePermission[] getResourcePermissionByRoleId(int roleId);

  ResourcePermission[] getResourcePermissionsByDomainAndUser(String domain, String username);

  ResourcePermission[] getResourcePermissionsByUserId(int userId);
}

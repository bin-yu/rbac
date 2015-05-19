/********************************************************************
 * File Name:    RoleMapper.java
 *
 * Date Created: Apr 20, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.daos;

import org.binyu.rbac.dtos.Role;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public interface RoleMapper
{

  // CONSTANTS ------------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  Role[] getAllRoles();

  Role[] getRolesByUserId(int userId);

  Role[] getRolesByUsername(String username);

  void addRole(Role role);

  Role getRoleByName(String name);

  void deleteRole(String name);

  String[] getRoleNamesByUsername(String username);

  void deleteRoleById(int id);

  Role getRoleById(int id);
}

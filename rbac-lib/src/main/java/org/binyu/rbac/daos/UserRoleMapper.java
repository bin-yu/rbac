/********************************************************************
 * File Name:    UserRoleMapper.java
 *
 * Date Created: Apr 20, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.daos;

import org.binyu.rbac.dtos.UserRole;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public interface UserRoleMapper
{

  // CONSTANTS ------------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  void addUserRole(UserRole userRole);

  void deleteUserRolesByUserId(int userId);

  Integer[] getRoleIdsByUserId(int userId);

}

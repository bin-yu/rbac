/********************************************************************
 * File Name:    UserMapper.java
 *
 * Date Created: Apr 19, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.daos;

import org.binyu.rbac.dtos.User;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public interface UserMapper
{

  // CONSTANTS ------------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  void addUser(User user);

  User getUserByName(String name);

  void deleteUserByName(String name);

  User[] getAllUsers();

  User getUserById(int id);

  void deleteUserById(int id);

  void updateUser(User user);
}

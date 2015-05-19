/********************************************************************
 * File Name:    UserController.java
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
import org.binyu.rbac.backend.controllers.dtos.ExtUser;
import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.dtos.User;
import org.binyu.rbac.exceptions.ServiceInputValidationException;
import org.binyu.rbac.services.UserManagementService;
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
@RequestMapping(value = "/users")
public class UserController
{
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  @Autowired
  private UserManagementService userSrv;

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------

  @RequestMapping(method = RequestMethod.GET)
  public ExtUser[] getAllUsers()
  {
    User[] users = userSrv.getAllUsers();
    ExtUser[] extUsers = new ExtUser[users.length];
    for (int i = 0; i < users.length; i++)
    {
      extUsers[i] = ExtUser.fromInternal(users[i]);
    }
    return extUsers;
  }

  @RequestMapping(method = RequestMethod.POST)
  @Transactional(rollbackFor = Throwable.class)
  public ExtUser addUser(@RequestBody ExtUser user) throws ServiceInputValidationException
  {
    User internalUser = user.toInternal();
    userSrv.addUser(internalUser);
    return ExtUser.fromInternal(internalUser);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @Transactional(rollbackFor = Throwable.class)
  public void deleteUser(@PathVariable int id)
  {
    userSrv.deleteUserById(id);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @Transactional(rollbackFor = Throwable.class)
  public ExtUser updateUser(@PathVariable int id, @RequestBody ExtUser extUser)
  {
    extUser.setId(id);
    User iUser = extUser.toInternal();
    userSrv.updateUser(iUser);
    return ExtUser.fromInternal(iUser);
  }

  @RequestMapping(value = "/{userId}/roles", method = RequestMethod.PUT)
  @Transactional(rollbackFor = Throwable.class)
  public void assignRoles(
      @PathVariable int userId,
      @RequestBody int[] roleIds,
      @RequestParam(required = false, defaultValue = "true") boolean overwrite) throws Exception
  {
    userSrv.assignRoles(userId, roleIds, overwrite);
  }

  @RequestMapping(value = "/{userId}/roles", method = RequestMethod.GET)
  public ExtRole[] getUserRoles(@PathVariable int userId) throws ServiceInputValidationException
  {
    Role[] roles = userSrv.getRolesByUserId(userId);
    return ExtRole.fromInternal(roles);
  }
  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

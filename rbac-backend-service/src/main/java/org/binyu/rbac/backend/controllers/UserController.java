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

import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.dtos.User;
import org.binyu.rbac.dtos.UserRole;
import org.binyu.rbac.services.RoleManagementService;
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
public class UserController {
	// CONSTANTS ------------------------------------------------------

	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------
	@Autowired
	private UserManagementService userSrv;
	@Autowired
	private RoleManagementService roleSrv;

	// CONSTRUCTORS ---------------------------------------------------

	// PUBLIC METHODS -------------------------------------------------
	@RequestMapping(method = RequestMethod.POST)
	@Transactional(rollbackFor = Throwable.class)
	public void addUser(@RequestBody User user) {
		userSrv.addUser(user);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
	@Transactional(rollbackFor = Throwable.class)
	public void deleteUser(@PathVariable String name) {
		userSrv.deleteUserByName(name);
	}

	@RequestMapping(value = "/{username}/roles", method = RequestMethod.PUT)
	@Transactional(rollbackFor = Throwable.class)
	public void assignRoles(
			@PathVariable String username,
			@RequestParam(required = false, defaultValue = "true") boolean overwrite,
			@RequestBody String[] roleNames) throws Exception {
		User user = userSrv.getUserByName(username);
		if (user == null) {
			throw new Exception("User [" + username + "] not exist.");
		}
		if (overwrite) {
			userSrv.deleteUserRolesByUserId(user.getId());
		}
		for (String roleName : roleNames) {
			Role role = roleSrv.getRolesByName(roleName);
			if (role != null) {
				userSrv.addUserRole(new UserRole(user.getId(), role.getId()));
			}
		}
	}

	@RequestMapping(value = "/{username}/roles", method = RequestMethod.GET)
	public String[] getUserRoles(@PathVariable String username) {
		return roleSrv.getRoleNamesByUsername(username);
	}
	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

}

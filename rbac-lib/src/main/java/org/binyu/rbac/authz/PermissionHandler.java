/********************************************************************
 * File Name:    PermissionHandler.java
 *
 * Date Created: Mar 11, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.authz;

import java.util.ArrayList;
import java.util.List;

import org.binyu.rbac.services.RoleManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * Entry Class to do permission check.
 */
@Component
public class PermissionHandler {
	// CONSTANTS ------------------------------------------------------

	private static final Logger LOG = LoggerFactory
			.getLogger(PermissionHandler.class);
	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------
	@Autowired
	RoleManagementService roleMgrService;
	@Autowired
	ResourcePermissionHandler handler;

	// CONSTRUCTORS ---------------------------------------------------

	// PUBLIC METHODS -------------------------------------------------
	public boolean checkPermission(Authentication authentication, String res,
			FilterInvocation invocation) {
		List<String> roleList = new ArrayList<String>();
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			roleList.add(auth.getAuthority());
		}
		LOG.info("checking permission with roles " + roleList
				+ " and resource[" + res + "]");
		return handler.checkPermission(
				roleMgrService.getPermissionByRolesAndResource(roleList, res),
				invocation);
	}

	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

}

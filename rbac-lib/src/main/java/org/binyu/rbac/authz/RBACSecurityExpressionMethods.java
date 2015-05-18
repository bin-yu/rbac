/********************************************************************
 * File Name:    RBACSecurityExpressionMethods.java
 *
 * Date Created: Mar 8, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.authz;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

/**
 * Expression method for rbac framework
 *
 */
public class RBACSecurityExpressionMethods {

	private final Authentication authentication;
	private PermissionHandler permissionHandler;
	private FilterInvocation invocation;

	public RBACSecurityExpressionMethods(PermissionHandler permissionHandler,
			Authentication authentication, FilterInvocation invocation) {
		this.permissionHandler = permissionHandler;
		this.authentication = authentication;
		this.invocation = invocation;
	}

	// CONSTANTS ------------------------------------------------------

	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------

	// CONSTRUCTORS ---------------------------------------------------

	// PUBLIC METHODS -------------------------------------------------
	public boolean accessRes(String res) {
		return permissionHandler.checkPermission(authentication, res,
				invocation);

	}

	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

}

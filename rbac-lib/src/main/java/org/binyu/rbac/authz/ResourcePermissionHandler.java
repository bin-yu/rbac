/********************************************************************
 * File Name:    ResourcePermissionHandler.java
 *
 * Date Created: Mar 11, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.authz;

import org.binyu.rbac.dtos.ResourcePermission;
import org.springframework.security.web.FilterInvocation;

/**
 * Interface for Resource's PermissionHandler
 */
public interface ResourcePermissionHandler {

	// CONSTANTS ------------------------------------------------------

	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------

	// CONSTRUCTORS ---------------------------------------------------

	// PUBLIC METHODS -------------------------------------------------
	boolean checkPermission(ResourcePermission permissionOfUser,
			FilterInvocation invocation);
	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

}

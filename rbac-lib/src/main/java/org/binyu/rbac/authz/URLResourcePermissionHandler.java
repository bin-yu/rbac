/********************************************************************
 * File Name:    URLResourcePermissionHandler.java
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * PermissionHandler for URL Resources
 *
 */
@Component("URLResourcePermissionHandler")
public class URLResourcePermissionHandler implements ResourcePermissionHandler {

	// CONSTANTS ------------------------------------------------------
	// CLASS VARIABLES ------------------------------------------------
	private static final Logger LOG = LoggerFactory
			.getLogger(URLResourcePermissionHandler.class);

	// INSTANCE VARIABLES ---------------------------------------------

	// CONSTRUCTORS ---------------------------------------------------

	// PUBLIC METHODS -------------------------------------------------

	public boolean checkPermission(ResourcePermission permissionOfUser,
			FilterInvocation invocation) {
		String method = invocation.getHttpRequest().getMethod();
		LOG.info("checkPermission>>myPermission=" + permissionOfUser
				+ ",http method=" + method);
		return permissionOfUser.grant(getRequiredPermissionByHttpMethod(
				permissionOfUser.getResource(), method));
	}

	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	private static ResourcePermission getRequiredPermissionByHttpMethod(
			String res, String method) {
		if (method.equalsIgnoreCase(HttpMethod.GET.name())
				|| method.equalsIgnoreCase(HttpMethod.HEAD.name())) {
			return new ResourcePermission(res, ResourcePermission.READ);
		} else {
			return new ResourcePermission(res, ResourcePermission.FULL);
		}
	}
	// ACCESSOR METHODS -----------------------------------------------

}

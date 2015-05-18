/********************************************************************
 * File Name:    UserProfile.java
 *
 * Date Created: Mar 13, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth.dtos;

import java.util.Arrays;

import org.binyu.rbac.dtos.ResourcePermission;

/**
 * User's Profile
 */
public class ExternalUserProfile {
	// CONSTANTS ------------------------------------------------------

	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------
	private String username;
	private ResourcePermission[] permissions;

	// CONSTRUCTORS ---------------------------------------------------

	public ExternalUserProfile() {
		super();
	}

	public ExternalUserProfile(String username, ResourcePermission[] permissions) {
		super();
		this.username = username;
		this.permissions = permissions;
	}

	// PUBLIC METHODS -------------------------------------------------

	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return "ExternalUserProfile [username=" + username + ", permissions="
				+ Arrays.toString(permissions) + "]";
	}

	public ResourcePermission[] getPermissions() {
		return permissions;
	}

	public void setPermissions(ResourcePermission[] permissions) {
		this.permissions = permissions;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}

/********************************************************************
 * File Name:    ExternalAuthenticationObject.java
 *
 * Date Created: Mar 18, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth.dtos;

/**
 * Object sent from user in http body for authentication
 */
public class ExternalAuthenticationObject {
	// CONSTANTS ------------------------------------------------------

	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------
	private String user = "";
	private String password = "";

	// CONSTRUCTORS ---------------------------------------------------

	public ExternalAuthenticationObject(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}

	public ExternalAuthenticationObject() {
	}

	// PUBLIC METHODS -------------------------------------------------

	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		if (user != null) {
			this.user = user.trim();
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

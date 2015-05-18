/********************************************************************
 * File Name:    ResourcePermission.java
 *
 * Date Created: Apr 19, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.dtos;

import java.io.Serializable;
import java.util.List;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class ResourcePermission implements Serializable {
	// CONSTANTS ------------------------------------------------------
	public static final int READ = 0x01;
	public static final int FULL = 0xFF;
	public static final int NONE = 0x00;
	// CLASS VARIABLES ------------------------------------------------

	/**
   * 
   */
	private static final long serialVersionUID = -8773251255205188990L;
	// INSTANCE VARIABLES ---------------------------------------------
	private String resource;
	private int permission;

	// CONSTRUCTORS ---------------------------------------------------

	public ResourcePermission() {
		super();
	}

	public ResourcePermission(String resource, int permission) {
		super();
		this.resource = resource;
		this.permission = permission;
	}

	// PUBLIC METHODS -------------------------------------------------
	/**
	 * check whether we have the target permission
	 * 
	 * @param target
	 * @return
	 */
	public boolean grant(ResourcePermission target) {
		return resource.equalsIgnoreCase(target.getResource())
				&& ((this.permission & target.getPermission()) == target
						.getPermission());
	}

	/**
	 * Merge a list of ResourcePermissions into one. the result
	 * ResourcePermission's permission should represent all the permissions that
	 * the original list have on the target res.
	 * 
	 * @param res
	 * @param permissions
	 * @return
	 */
	public static ResourcePermission mergePermissions(String res,
			List<ResourcePermission> permissions) {
		int iMerged = 0;
		for (ResourcePermission p : permissions) {
			if (res.equalsIgnoreCase(p.getResource())) {
				iMerged |= p.getPermission();
			}
		}
		return new ResourcePermission(res, iMerged);
	}

	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
}

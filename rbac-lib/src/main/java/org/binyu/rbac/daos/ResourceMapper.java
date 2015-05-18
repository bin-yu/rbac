/********************************************************************
 * File Name:    ResourceMapper.java
 *
 * Date Created: Apr 20, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.daos;

import org.binyu.rbac.dtos.Resource;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public interface ResourceMapper
{

  Resource getResourceByName(String name);

  // CONSTANTS ------------------------------------------------------

  Resource[] getAllResources();

  // PUBLIC METHODS -------------------------------------------------
}

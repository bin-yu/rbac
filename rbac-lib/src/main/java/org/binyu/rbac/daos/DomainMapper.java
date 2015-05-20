/********************************************************************
 * File Name:    DomainMapper.java
 *
 * Date Created: Mar 29, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.daos;

import org.binyu.rbac.dtos.Domain;

/**
 * Mapper for Domain information access
 */
public interface DomainMapper
{
  // CONSTANTS ------------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  Domain[] getAllDomains();
}

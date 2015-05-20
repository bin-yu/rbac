/********************************************************************
 * File Name:    AuthenticationProviderFactory.java
 *
 * Date Created: Mar 29, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;

/**
 * Interface for AuthenticationProvider's Factory
 *
 */
public interface AuthenticationProviderFactory
{
  // CONSTANTS ------------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  /**
   * Call this method to create a new AuthenticationProvider using the given properties
   * @param properties
   * @return
   */
  public AuthenticationProvider createInstance(Map<String, Object> properties);
}

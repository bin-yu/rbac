/********************************************************************
 * File Name:    LocalAuthenticationProviderFactory.java
 *
 * Date Created: Mar 29, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth.local;

import java.util.Map;

import org.binyu.rbac.auth.AuthenticationProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;

/**
 * Factory to create instance of AuthenticationProvider for local db authentication.
 */
@Component(value = "LocalAuthenticationProviderFactory")
public class LocalAuthenticationProviderFactory implements AuthenticationProviderFactory
{

  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  @Autowired
  private LocalAuthenticationProvider localProvider;

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------

  @Override
  public AuthenticationProvider createInstance(Map<String, Object> properties)
  {
    return localProvider;
  }
  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

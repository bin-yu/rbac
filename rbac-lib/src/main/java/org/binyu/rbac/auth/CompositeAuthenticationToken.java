/********************************************************************
 * File Name:    LdapAuthenticationToken.java
 *
 * Date Created: Mar 25, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth;

import org.binyu.rbac.dtos.Domain;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Authentication token class that is designed for CompositeAuthenticationProvider
 */
public class CompositeAuthenticationToken extends UsernamePasswordAuthenticationToken
{

  // CONSTANTS ------------------------------------------------------
  private static final long serialVersionUID = 7488221235992526258L;

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  // which domain that this token should authentication against
  private String domain = Domain.LOCAL_DOMAIN;

  // CONSTRUCTORS ---------------------------------------------------

  public CompositeAuthenticationToken(String principal, String credentials)
  {
    super(readPrincipal(principal), credentials);
    this.domain = readDomain(principal);
  }

  // PUBLIC METHODS -------------------------------------------------

  /**
   * @return the domain of this token
   */
  public String getDomain()
  {
    return domain;
  }

  @Override
  public String toString()
  {
    return "CompositeAuthenticationToken [domain=" + domain + ", getPrincipal()=" + getPrincipal() + "]";
  }

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------
  private static String readDomain(String principal)
  {
    String domain = Domain.LOCAL_DOMAIN;
    int index = principal.indexOf("\\");
    if (index >= 0)
    {
      domain = principal.substring(0, index);
    }
    return domain;
  }

  private static String readPrincipal(String principal)
  {
    String user = principal;
    int index = principal.indexOf("\\");
    if (index >= 0)
    {
      user = principal.substring(index + 1);
    }
    return user;
  }

  // ACCESSOR METHODS -----------------------------------------------

}

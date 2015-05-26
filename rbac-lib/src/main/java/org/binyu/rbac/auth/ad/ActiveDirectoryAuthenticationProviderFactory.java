/********************************************************************
 * File Name:    ActiveDirectoryAuthenticationProviderFactory.java
 *
 * Date Created: Mar 29, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth.ad;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.binyu.rbac.auth.AuthenticationProviderFactory;
import org.binyu.rbac.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Component;

/**
 * Factory to create AD type authentication providers
 */
@Component("ActiveDirectoryAuthenticationProviderFactory")
public class ActiveDirectoryAuthenticationProviderFactory implements AuthenticationProviderFactory
{

  // CONSTANTS ------------------------------------------------------
  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------

  @Autowired
  private UserManagementService userMgr;

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------

  @Override
  public AuthenticationProvider createInstance(Map<String, Object> properties)
  {
    String domain = (String) properties.get("domain");
    String controller = (String) properties.get("controller");
    String userSearchFilter = (String) properties.get("userSearchFilter");
    if (StringUtils.isBlank(domain) || StringUtils.isBlank(controller))
    {
      throw new RuntimeException("domain or controller is required for creating an AD provider.");
    }

    ActiveDirectoryLdapAuthenticationProvider adProvider = new ActiveDirectoryLdapAuthenticationProvider(
        domain, controller);
    if (!StringUtils.isBlank(userSearchFilter))
    {
      adProvider.setSearchFilter(userSearchFilter);
    }
    return new ExternalAuthenticationProviderAdapter(adProvider, userMgr);
  }
  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

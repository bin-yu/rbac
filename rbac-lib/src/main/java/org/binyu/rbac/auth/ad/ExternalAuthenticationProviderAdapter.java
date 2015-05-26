/********************************************************************
 * File Name:    LdapAuthenticationProviderWrapper.java
 *
 * Date Created: Mar 25, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth.ad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.binyu.rbac.auth.CompositeAuthenticationToken;
import org.binyu.rbac.auth.LocalUserDetails;
import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.dtos.User;
import org.binyu.rbac.exceptions.ServiceInputValidationException;
import org.binyu.rbac.services.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Adapter for external AuthenticationProviders(such as LDAP/AD) ,which could map external principals into local roles
 */
public class ExternalAuthenticationProviderAdapter implements AuthenticationProvider
{

  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------
  private static final Logger LOG = LoggerFactory.getLogger(ExternalAuthenticationProviderAdapter.class);
  // INSTANCE VARIABLES ---------------------------------------------

  private AuthenticationProvider delegate;
  private UserManagementService userMgr;

  // CONSTRUCTORS ---------------------------------------------------

  public ExternalAuthenticationProviderAdapter(AuthenticationProvider delegate,
      UserManagementService userMgr)
  {
    this.delegate = delegate;
    this.userMgr = userMgr;
  }

  // PUBLIC METHODS -------------------------------------------------

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException
  {
    try
    {
      CompositeAuthenticationToken token = new CompositeAuthenticationToken((String) authentication.getPrincipal(),
          (String) authentication.getCredentials());
      LOG.info("Token to authentication:" + token);
      String domain = token.getDomain();
      Authentication authToken = delegate.authenticate(token);

      UserDetails user = (UserDetails) authToken.getPrincipal();
      User localUser = userMgr.getOrCreateUser(domain, user.getUsername());

      List<String> dlNameList = new ArrayList<String>();
      dlNameList.add(user.getUsername());
      for (GrantedAuthority auth : authToken.getAuthorities())
      {
        dlNameList.add(auth.getAuthority());
      }
      LOG.info("User and group names that this token belongs to : " + dlNameList);
      Role[] roles = userMgr.getRolesByDomainAndUserNames(domain, dlNameList);

      LOG.info("Local roles that this token maps to : " + Arrays.toString(roles));
      LocalUserDetails userDetails = new LocalUserDetails(localUser, roles);
      return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    catch (ServiceInputValidationException e)
    {
      throw new InternalAuthenticationServiceException("failed to get or create local user", e);
    }
  }

  @Override
  public boolean supports(Class<?> authentication)
  {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------
}

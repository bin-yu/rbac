/********************************************************************
 * File Name:    MyUserDetailsService.java
 *
 * Date Created: Mar 10, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth.local;

import java.util.Arrays;

import org.binyu.rbac.dtos.Domain;
import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.dtos.User;
import org.binyu.rbac.exceptions.ServiceInputValidationException;
import org.binyu.rbac.services.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Custom UserDetailsService for spring security authentication
 * 
 * @author Administrator
 *
 */
@Service
public class LocalUserDetailsService implements UserDetailsService
{
  // CONSTANTS ------------------------------------------------------
  // CLASS VARIABLES ------------------------------------------------

  private static final Logger LOG = LoggerFactory
      .getLogger(LocalUserDetailsService.class);
  // INSTANCE VARIABLES ---------------------------------------------

  @Autowired
  UserManagementService userSrv;

  // CONSTRUCTORS ---------------------------------------------------
  // PUBLIC METHODS ----------------------------------------------
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException
  {

    try
    {
      if (StringUtils.isEmpty(username))
      {
        throw new UsernameNotFoundException(
            "the username can not be empty.");
      }
      User user = userSrv.getUserByDomainAndName(Domain.LOCAL_DOMAIN, username);
      if (user == null)
      {
        throw new UsernameNotFoundException("the username " + username
            + " not found.");
      }
      LOG.info("User found :" + user.toString());
      Role[] roleArray;
      roleArray = userSrv.getRolesByUserId(user.getId());

      LOG.info("User found :" + user.toString() + ", roles = "
          + Arrays.toString(roleArray));
      return new LocalUserDetails(user, roleArray);
    }
    catch (ServiceInputValidationException e)
    {
      // SHOULD never goes here
      throw new UsernameNotFoundException("the username " + username
          + " not found.");
    }
  }

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------
}

/********************************************************************
 * File Name:    RestAuthenticationFilter.java
 *
 * Date Created: Mar 18, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.binyu.rbac.auth.dtos.ExternalAuthenticationObject;
import org.binyu.rbac.auth.dtos.ExternalUserProfile;
import org.binyu.rbac.services.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;

/**
 * Custom authentication filter to retrieve authentication from request body as
 * JSON object.
 */
public class RestAuthenticationFilter extends
    AbstractAuthenticationProcessingFilter
{
  private static final Logger LOG = LoggerFactory
      .getLogger(RestAuthenticationFilter.class);
  private static final ObjectMapper mapper = new ObjectMapper();

  private UserManagementService userSrv;

  private class MyAuthenticationSuccessHandler implements
      AuthenticationSuccessHandler
  {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException
    {
      LocalUserDetails user = (LocalUserDetails) SecurityContextHolder
          .getContext().getAuthentication().getPrincipal();
      CsrfToken token = (CsrfToken) request
          .getAttribute("org.springframework.security.web.csrf.CsrfToken");
      LOG.info("Login succeed, user name is " + user.getUsername()
          + ", csrf token is " + token.getToken());

      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.addHeader(token.getHeaderName(), token.getToken());
      mapper.writeValue(
          response.getOutputStream(),
          new ExternalUserProfile(user.getUsername(), userSrv
              .getResourcePermissionsByUserId(user.getId())));
    }

  }

  public RestAuthenticationFilter()
  {
    super("/login");
    this.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
  }

  public void setUserManagementService(UserManagementService userSrv)
  {
    this.userSrv = userSrv;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException,
      IOException, ServletException
  {
    if (!request.getMethod().equals("POST"))
    {
      throw new AuthenticationServiceException(
          "Authentication method not supported: "
              + request.getMethod());
    }
    try
    {
      ExternalAuthenticationObject authObj = mapper.readValue(
          request.getInputStream(),
          ExternalAuthenticationObject.class);
      Authentication authRequest = new UsernamePasswordAuthenticationToken(authObj.getUser(),
          authObj.getPassword());
      return this.getAuthenticationManager().authenticate(authRequest);
    }
    catch (IOException e)
    {
      throw new AuthenticationServiceException(
          "Fail to read authentication information from the body", e);
    }

  }
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

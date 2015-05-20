/********************************************************************
 * File Name:    CompositeAuthenticationProvider.java
 *
 * Date Created: Mar 25, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.binyu.rbac.dtos.Domain;
import org.binyu.rbac.services.DomainManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * authentication provider that can authentication users from multiple domain using different authentication provider.
 */
@Component
public class CompositeAuthenticationProvider implements AuthenticationProvider, SmartInitializingSingleton
{
  // CONSTANTS ------------------------------------------------------
  // CLASS VARIABLES ------------------------------------------------
  private static final Logger LOG = LoggerFactory.getLogger(CompositeAuthenticationProvider.class);
  // INSTANCE VARIABLES ---------------------------------------------
  // map from domain name to authentication provider
  private Map<String, AuthenticationProvider> delegateProviderMap = new HashMap<String, AuthenticationProvider>(2);
  @Autowired
  private DomainManagementService domainMgr;
  @Autowired
  private ApplicationContext springCtx;

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------

  protected void addProvider(String domain, AuthenticationProvider provider)
  {
    delegateProviderMap.put(domain, provider);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException
  {
    CompositeAuthenticationToken token = (CompositeAuthenticationToken) authentication;
    LOG.info("Authenticating against token " + token);
    String domain = token.getDomain();
    AuthenticationProvider authenticationProvider = delegateProviderMap.get(domain);
    if (authenticationProvider == null)
    {
      throw new ProviderNotFoundException("Can't find authentication provider for domain : " + domain);
    }
    LOG.info("Authentication provider : " + authenticationProvider.getClass().getName());
    Authentication authResult = authenticationProvider.authenticate(authentication);
    // only allow those users who has been assigned at least one role to login
    /*if (authResult.getAuthorities() == null || authResult.getAuthorities().size() == 0)
    {
      throw new InsufficientAuthenticationException(token + " is not allowed to login.");
    }*/
    return authResult;
  }

  @Override
  public boolean supports(Class<?> authentication)
  {
    return CompositeAuthenticationToken.class.isAssignableFrom(authentication);
  }

  /**
   * This initialize method will be invoked right at the end of the singleton pre-instantiation phase,
   * with a guarantee that all regular singleton beans have been created already.
   * This is to guarantee that the database has been initialized
   * (during unit testing we init the embedded db in a DataSourceInitializer's @PostConstruct method) before we initialize this
   * provider bean,
   * 
   */
  @Override
  public void afterSingletonsInstantiated()
  {
    Domain[] domains = domainMgr.getAllDomains();
    if (domains != null)
    {
      LOG.info("Loading authentication providers for all domains :" + Arrays.toString(domains));
      for (Domain domain : domains)
      {
        AuthenticationProviderFactory fac = getFactory(domain.getProviderFactory());
        LOG.info("Found AuthenticationProviderFactory for " + domain.getName() + ":" + fac.getClass().getName());
        addProvider(domain.getName(), fac.createInstance(domain.getParams()));
      }
    }
  }

  // PROTECTED METHODS ----------------------------------------------
  // PRIVATE METHODS ------------------------------------------------
  private AuthenticationProviderFactory getFactory(String name)
  {
    Object facObj = null;
    try
    {
      facObj = springCtx.getBean(name);
    }
    catch (NoSuchBeanDefinitionException e)
    {
      facObj = null;
    }
    if (facObj == null || !(facObj instanceof AuthenticationProviderFactory))
    {
      throw new RuntimeException("fail to load AuthenticationProviderFactory for " + name);
    }
    return (AuthenticationProviderFactory) facObj;
  }

  // ACCESSOR METHODS -----------------------------------------------

}

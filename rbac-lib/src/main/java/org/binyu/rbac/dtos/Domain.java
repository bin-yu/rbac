/********************************************************************
 * File Name:    Domain.java
 *
 * Date Created: Mar 29, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * This object represents the domain information for users
 */
public class Domain implements Serializable
{
  // CONSTANTS ------------------------------------------------------
  private static final long serialVersionUID = -3220092103601240235L;
  public static final String LOCAL_DOMAIN = "LOCAL";
  // CLASS VARIABLES ------------------------------------------------
  private static final ObjectMapper mapper = new ObjectMapper();
  // INSTANCE VARIABLES ---------------------------------------------
  private String name;
  private String providerFactory;
  private Map<String, Object> params;

  // CONSTRUCTORS ---------------------------------------------------

  public Domain(String name, String providerFactory, Map<String, Object> params)
  {
    this.name = name;
    this.providerFactory = providerFactory;
    this.params = params;
  }

  public Domain()
  {
    super();
  }

  // PUBLIC METHODS -------------------------------------------------

  @Override
  public String toString()
  {
    return "Domain [name=" + name + ", providerFactory=" + providerFactory + ", params=" + params + "]";
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((params == null) ? 0 : params.hashCode());
    result = prime * result + ((providerFactory == null) ? 0 : providerFactory.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Domain other = (Domain) obj;
    if (name == null)
    {
      if (other.name != null)
        return false;
    }
    else if (!name.equals(other.name))
      return false;
    if (params == null)
    {
      if (other.params != null)
        return false;
    }
    else if (!params.equals(other.params))
      return false;
    if (providerFactory == null)
    {
      if (other.providerFactory != null)
        return false;
    }
    else if (!providerFactory.equals(other.providerFactory))
      return false;
    return true;
  }

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getProviderFactory()
  {
    return providerFactory;
  }

  public void setProviderFactory(String providerFactory)
  {
    this.providerFactory = providerFactory;
  }

  public Map<String, Object> getParams()
  {
    return params;
  }

  public void setParams(String params)
  {
    try
    {
      this.params = mapper.readValue(params, Map.class);
    }
    catch (IOException e)
    {
      throw new RuntimeException("Fail to parse params for this domain:" + name, e);
    }
  }

}

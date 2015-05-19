/********************************************************************
 * File Name:    ExtResourcePermission.java
 *
 * Date Created: May 19, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers.dtos;

import org.binyu.rbac.dtos.ResourcePermission;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class ExtResourcePermission
{
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  private String resource;
  private int permission;

  // CONSTRUCTORS ---------------------------------------------------
  public ExtResourcePermission(String resource, int permission)
  {
    super();
    this.resource = resource;
    this.permission = permission;
  }

  public ExtResourcePermission()
  {
    super();
  }

  public static ExtResourcePermission fromInternal(ResourcePermission perm)
  {
    return new ExtResourcePermission(perm.getResource(), perm.getPermission());
  }

  public static ExtResourcePermission[] fromInternal(ResourcePermission[] permArray)
  {
    ExtResourcePermission[] extPermArray = new ExtResourcePermission[permArray.length];
    for (int i = 0; i < permArray.length; i++)
    {
      extPermArray[i] = ExtResourcePermission.fromInternal(permArray[i]);
    }
    return extPermArray;
  }

  public ResourcePermission toInternal()
  {
    return new ResourcePermission(resource, permission);
  }

  // PUBLIC METHODS -------------------------------------------------

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + permission;
    result = prime * result + ((resource == null) ? 0 : resource.hashCode());
    return result;
  }

  @Override
  public String toString()
  {
    return "ExtResourcePermission [resource=" + resource + ", permission=" + permission + "]";
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
    ExtResourcePermission other = (ExtResourcePermission) obj;
    if (permission != other.permission)
      return false;
    if (resource == null)
    {
      if (other.resource != null)
        return false;
    }
    else if (!resource.equals(other.resource))
      return false;
    return true;
  }

  public String getResource()
  {
    return resource;
  }

  public void setResource(String resource)
  {
    this.resource = resource;
  }

  public int getPermission()
  {
    return permission;
  }

  public void setPermission(int permission)
  {
    this.permission = permission;
  }

}

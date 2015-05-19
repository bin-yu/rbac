/********************************************************************
 * File Name:    Role.java
 *
 * Date Created: Apr 19, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers.dtos;

import org.binyu.rbac.dtos.Role;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class ExtRole
{

  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  private int id;
  private String name;

  // CONSTRUCTORS ---------------------------------------------------

  public ExtRole()
  {
    super();
  }

  public ExtRole(int id, String name)
  {
    super();
    this.id = id;
    this.name = name;
  }

  public static ExtRole fromInternal(Role role)
  {
    return new ExtRole(role.getId(), role.getName());
  }

  public static ExtRole[] fromInternal(Role[] roles)
  {
    ExtRole[] extRoles = new ExtRole[roles.length];
    for (int i = 0; i < roles.length; i++)
    {
      extRoles[i] = ExtRole.fromInternal(roles[i]);
    }
    return extRoles;
  }

  public Role toInternal()
  {
    return new Role(this.name);
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public String toString()
  {
    return "ExtRole [id=" + id + ", name=" + name + "]";
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    ExtRole other = (ExtRole) obj;
    if (id != other.id)
      return false;
    if (name == null)
    {
      if (other.name != null)
        return false;
    }
    else if (!name.equals(other.name))
      return false;
    return true;
  }

  // PUBLIC METHODS -------------------------------------------------

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

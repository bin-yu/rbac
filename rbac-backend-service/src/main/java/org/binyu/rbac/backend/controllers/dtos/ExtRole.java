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

  // PUBLIC METHODS -------------------------------------------------

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

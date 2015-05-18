/********************************************************************
 * File Name:    UserRole.java
 *
 * Date Created: Apr 20, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.dtos;

import java.io.Serializable;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class UserRole implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -8034044158270516601L;
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  private int userId;
  private int roleId;

  public UserRole(int userId, int roleId)
  {
    super();
    this.userId = userId;
    this.roleId = roleId;
  }

  // CONSTRUCTORS ---------------------------------------------------

  public int getUserId()
  {
    return userId;
  }

  public void setUserId(int userId)
  {
    this.userId = userId;
  }

  public int getRoleId()
  {
    return roleId;
  }

  public void setRoleId(int roleId)
  {
    this.roleId = roleId;
  }

  // PUBLIC METHODS -------------------------------------------------

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

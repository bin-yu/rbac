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
package org.binyu.rbac.dtos;

import java.io.Serializable;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class Role implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -4822745373583930295L;
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  private int id;
  private String name;

  // CONSTRUCTORS ---------------------------------------------------

  public Role()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  public Role(String name)
  {
    super();
    this.name = name;
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

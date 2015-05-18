/********************************************************************
 * File Name:    Resource.java
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
public class Resource implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -7325607545660222972L;
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  private int id;
  private String name;

  // CONSTRUCTORS ---------------------------------------------------

  public Resource()
  {
    super();
    // TODO Auto-generated constructor stub
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

/********************************************************************
 * File Name:    ExtResource.java
 *
 * Date Created: May 18, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers.dtos;

import org.binyu.rbac.dtos.Resource;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class ExtResource
{
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  private int id;
  private String name;

  // CONSTRUCTORS ---------------------------------------------------

  public ExtResource(int id, String name)
  {
    super();
    this.id = id;
    this.name = name;
  }

  public ExtResource()
  {
    super();
  }

  // PUBLIC METHODS -------------------------------------------------
  public static ExtResource fromInternal(Resource rs)
  {
    return new ExtResource(rs.getId(), rs.getName());
  }

  @Override
  public String toString()
  {
    return "ExtResource [id=" + id + ", name=" + name + "]";
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

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

}

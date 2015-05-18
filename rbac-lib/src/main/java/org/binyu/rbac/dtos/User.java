/********************************************************************
 * File Name:    User.java
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
import java.util.Date;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class User implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 2553753600891920212L;
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  private int id;
  private String name;

  private String password;
  private Date createTime;
  private Date updateTime;
  // whether this role has been disabled
  private int deleted;
  // CONSTRUCTORS ---------------------------------------------------

  public User()
  {
    super();
  }

  public User(int id, String name)
  {
    super();
    this.id = id;
    this.name = name;
  }
  public User(User copy)
  {
    this.id = copy.id;
    this.name = copy.name;
    this.password = copy.password;
    this.createTime = copy.createTime;
    this.updateTime = copy.updateTime;
    this.deleted = copy.deleted;
  }
public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public Date getCreateTime() {
	return createTime;
}

public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}

public Date getUpdateTime() {
	return updateTime;
}

public void setUpdateTime(Date updateTime) {
	this.updateTime = updateTime;
}

public int getDeleted() {
	return deleted;
}

public void setDeleted(int deleted) {
	this.deleted = deleted;
}


  // PUBLIC METHODS -------------------------------------------------

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

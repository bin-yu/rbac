/********************************************************************
 * File Name:    ExtUser.java
 *
 * Date Created: May 19, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers.dtos;

import java.util.Date;

import org.binyu.rbac.dtos.Domain;
import org.binyu.rbac.dtos.User;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class ExtUser
{
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

  public ExtUser()
  {
    super();
  }

  public ExtUser(int id, String name, String password, Date createTime, Date updateTime, int deleted)
  {
    super();
    this.id = id;
    this.name = name;
    this.password = password;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.deleted = deleted;
  }

  public static ExtUser fromInternal(User user)
  {
    return new ExtUser(user.getId(), user.getName(), null, user.getCreateTime(), user.getUpdateTime(), user.getDeleted());
  }

  public User toInternal()
  {
    return new User(getId(), Domain.LOCAL_DOMAIN, getName(), getPassword(), getCreateTime(), getUpdateTime(), getDeleted());
  }

  // PUBLIC METHODS -------------------------------------------------

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

  @Override
  public String toString()
  {
    return "ExtUser [id=" + id + ", name=" + name + ", createTime=" + createTime + ", updateTime="
        + updateTime + ", deleted=" + deleted + "]";
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
    result = prime * result + deleted;
    result = prime * result + id;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
    ExtUser other = (ExtUser) obj;
    if (createTime == null)
    {
      if (other.createTime != null)
        return false;
    }
    else if (!createTime.equals(other.createTime))
      return false;
    if (deleted != other.deleted)
      return false;
    if (id != other.id)
      return false;
    if (name == null)
    {
      if (other.name != null)
        return false;
    }
    else if (!name.equals(other.name))
      return false;
    if (password == null)
    {
      if (other.password != null)
        return false;
    }
    else if (!password.equals(other.password))
      return false;
    if (updateTime == null)
    {
      if (other.updateTime != null)
        return false;
    }
    else if (!updateTime.equals(other.updateTime))
      return false;
    return true;
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

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public Date getCreateTime()
  {
    return createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getUpdateTime()
  {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime)
  {
    this.updateTime = updateTime;
  }

  public int getDeleted()
  {
    return deleted;
  }

  public void setDeleted(int deleted)
  {
    this.deleted = deleted;
  }

}

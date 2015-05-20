/********************************************************************
 * File Name:    LocalUserDetails.java
 *
 * Date Created: Apr 8, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth.local;

import java.util.ArrayList;
import java.util.List;

import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.dtos.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetails for local db users.
 */
public class LocalUserDetails extends User implements UserDetails
{
  // CONSTANTS ------------------------------------------------------

  private static final long serialVersionUID = 7163533952242719002L;

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  private List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

  // CONSTRUCTORS ---------------------------------------------------
  public LocalUserDetails(User u, Role[] roleArray)
  {
    super(u);
    if (roleArray != null)
    {
      for (Role r : roleArray)
      {
        authList.add(new SimpleGrantedAuthority(r.getName()));
      }
    }
  }

  // PUBLIC METHODS -------------------------------------------------

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((authList == null) ? 0 : authList.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    LocalUserDetails other = (LocalUserDetails) obj;
    if (authList == null)
    {
      if (other.authList != null)
        return false;
    }
    else if (!authList.equals(other.authList))
      return false;
    return true;
  }

  @Override
  public List<GrantedAuthority> getAuthorities()
  {
    return authList;
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return super.getDeleted() == 0;
  }

@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return super.getName();
}

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

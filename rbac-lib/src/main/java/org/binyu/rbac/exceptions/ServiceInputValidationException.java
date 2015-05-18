/********************************************************************
 * File Name:    ValidationException.java
 *
 * Date Created: Mar 20, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.exceptions;

/**
 * Exception for invalid input cases for all service methods
 */
public class ServiceInputValidationException extends Exception
{

  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  /**
   * 
   */
  private static final long serialVersionUID = 2483445925353348798L;

  // INSTANCE VARIABLES ---------------------------------------------

  // CONSTRUCTORS ---------------------------------------------------

  /**
   * @param msg
   */
  public ServiceInputValidationException(String msg)
  {
    super(msg);
  }
  // PUBLIC METHODS -------------------------------------------------

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

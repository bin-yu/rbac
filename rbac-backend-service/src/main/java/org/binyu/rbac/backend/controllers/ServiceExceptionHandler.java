/********************************************************************
 * File Name:    ServiceExceptionHandler.java
 *
 * Date Created: Mar 20, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.binyu.rbac.exceptions.ServiceInputValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Uniform Handler class to handling all our custom checked exceptions and
 * return proper response code and content for controllers.
 */
@ControllerAdvice("org.binyu.rbac.backend.controllers")
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler
{

  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  /*  
   * For ConstraintViolationException exceptions, return BAD_REQUEST status code
   * 
   * @param ex
   * @param request
   * @return
   */
  @ExceptionHandler({ ConstraintViolationException.class })
  public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
      ConstraintViolationException ex, HttpServletRequest request)
  {
    return createErrorResponse(HttpStatus.BAD_REQUEST, request, getMessage(ex));
  }

  /**
   * For service validation exceptions, return BAD_REQUEST status code
   * @param ex
   * @param request
   * @return
   */
  @ExceptionHandler({ ServiceInputValidationException.class })
  public ResponseEntity<Map<String, Object>> handleValidationException(
      ServiceInputValidationException ex, HttpServletRequest request)
  {
    return createErrorResponse(HttpStatus.BAD_REQUEST, request,
        ex.getMessage());
  }

  /**
   * For RestResourceNotFoundException exceptions, return NOT_FOUND status code
   * @param ex
   * @param request
   * @return
   */
  @ExceptionHandler({ RestResourceNotFoundException.class })
  public ResponseEntity<Map<String, Object>> handleRestResourceNotFoundException(
      RestResourceNotFoundException ex, HttpServletRequest request)
  {
    return createErrorResponse(HttpStatus.NOT_FOUND, request,
        ex.getMessage());
  }

  /**
   * For other exceptions, return internal server error
   * 
   * @param ex
   * @param request
   * @return
   */
  @ExceptionHandler({ Throwable.class })
  public ResponseEntity<Map<String, Object>> handleAllOtherException(
      Exception ex, HttpServletRequest request)
  {
    return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request,
        ex.getMessage());
  }

  private ResponseEntity<Map<String, Object>> createErrorResponse(
      HttpStatus status, HttpServletRequest request, String message)
  {
    Map<String, Object> errorAttributes = new LinkedHashMap<String, Object>();
    errorAttributes.put("timestamp", new Date());
    errorAttributes.put("status", status.toString());
    errorAttributes.put("message", message);
    errorAttributes.put("path", request.getRequestURI());
    return new ResponseEntity<Map<String, Object>>(errorAttributes, status);
  }

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------
  private String getMessage(ConstraintViolationException e)
  {
    StringBuilder sb = new StringBuilder();
    for (ConstraintViolation<?> violation : e.getConstraintViolations())
    {
      sb.append(violation.getMessage() + ";");
    }
    return sb.toString();
  }
  // ACCESSOR METHODS -----------------------------------------------

}

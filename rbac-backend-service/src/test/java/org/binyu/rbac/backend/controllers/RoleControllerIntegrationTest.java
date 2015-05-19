/********************************************************************
 * File Name:    RoleControllerIntegrationTest.java
 *
 * Date Created: May 18, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers;

import java.util.Map;

import org.binyu.rbac.backend.AbstractWebIntegrationTest;
import org.binyu.rbac.backend.controllers.dtos.ExtRole;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class RoleControllerIntegrationTest extends AbstractWebIntegrationTest
{
  // CONSTANTS ------------------------------------------------------

  private static final String ADMIN_USER = "admin";
  private static final String ADMIN_PWD = "admin";
  private static final String TEST_USER = "test";
  private static final String TEST_PWD = "test";
  private static final String TEST1_USER = "test1";
  private static final String TEST1_PWD = "test";

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  /**
   * Given user "test" without permission on resource "roles";
   * When send request to GET /roles with "test" user's auth;
   * Then the response should have forbidden error code.
   * @throws Exception
   */
  @Test
  public void testGetRolesWithNoPermission() throws Exception
  {
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(doLogin(
        TEST_USER, TEST_PWD));
    ResponseEntity<Map> roleResp = restTemplate.exchange(
        constructFullURL("/roles"), HttpMethod.GET, requestEntity,
        Map.class);
    Assert.assertEquals(roleResp.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  /**
   * Given user "admin" with GET permission on resource "roles"
   * When send request to GET /roles with "admin" user's auth
   * Then the response should return the resource list
   * 
   * @throws Exception
   */
  @Test
  public void testGetRolesWithPermission() throws Exception
  {
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(doLogin(
        ADMIN_USER, ADMIN_PWD));
    ResponseEntity<ExtRole[]> roleResp = restTemplate.exchange(
        constructFullURL("/roles"), HttpMethod.GET, requestEntity,
        ExtRole[].class);
    Assert.assertEquals(roleResp.getStatusCode(), HttpStatus.OK);
    Assert.assertTrue(roleResp.getBody().length > 0);
  }

  /**
   * Given user "test" without permission on resource "roles";
   * When send request to add role with "test" user's auth;
   * Then the response should have forbidden error code.
   * @throws Exception
   */
  @Test
  public void testAddRoleWithNoPermission() throws Exception
  {
    ExtRole toAddRole = new ExtRole(0, "added-role");
    HttpEntity<ExtRole> requestEntity = new HttpEntity<ExtRole>(toAddRole, doLogin(
        TEST_USER, TEST_PWD));
    ResponseEntity<Map> roleResp = restTemplate.exchange(
        constructFullURL("/roles"), HttpMethod.POST, requestEntity,
        Map.class);
    Assert.assertEquals(roleResp.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  /**
   * Given user "test1" with readonly permission on resource "roles";
   * When send request to add role with "test1" user's auth;
   * Then the response should have forbidden error code.
   * @throws Exception
   */
  @Test
  public void testAddRoleWithLowerPermission() throws Exception
  {
    ExtRole toAddRole = new ExtRole(0, "added-role");
    HttpEntity<ExtRole> requestEntity = new HttpEntity<ExtRole>(toAddRole, doLogin(
        TEST1_USER, TEST1_PWD));
    ResponseEntity<Map> roleResp = restTemplate.exchange(
        constructFullURL("/roles"), HttpMethod.POST, requestEntity,
        Map.class);
    Assert.assertEquals(roleResp.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  /**
   * Given user "admin" with FULL permission on resource "roles";
   * When send request to add role with "admin" user's auth;
   * Then the response should be success.
   * @throws Exception
   */
  @Test
  public void testAddRoleWithPermission() throws Exception
  {
    ExtRole toAddRole = new ExtRole(0, "added-role");
    HttpEntity<ExtRole> requestEntity = new HttpEntity<ExtRole>(toAddRole, doLogin(
        ADMIN_USER, ADMIN_PWD));
    ResponseEntity<ExtRole> roleResp = restTemplate.exchange(
        constructFullURL("/roles"), HttpMethod.POST, requestEntity,
        ExtRole.class);
    Assert.assertEquals(roleResp.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(roleResp.getBody().getName(), toAddRole.getName());
    Assert.assertTrue(roleResp.getBody().getId() > 0);
  }
  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

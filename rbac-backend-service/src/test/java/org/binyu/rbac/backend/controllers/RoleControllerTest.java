/********************************************************************
 * File Name:    RoleControllerTest.java
 *
 * Date Created: May 18, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.binyu.rbac.backend.AbstractIntegrationTest;
import org.binyu.rbac.backend.controllers.dtos.ExtRole;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class RoleControllerTest extends AbstractIntegrationTest
{
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------

  @Test
  public void testAddRole() throws Exception
  {
    ExtRole toAddRole = new ExtRole(0, "added-role");
    MvcResult result = this.mockMvc
        .perform(post("/roles").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(serialize(toAddRole)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
    ExtRole role = deserialize(result, ExtRole.class);
    Assert.assertNotNull(role);
    Assert.assertNotEquals(role.getId(), 0);
    Assert.assertEquals(role.getName(), toAddRole.getName());
  }

  /**
   * Given there is already a role named 'test'
   * When send request to add a role with name 'test'
   * Then the response should be
   * @throws Exception
   */
  @Test
  public void testAddRoleWithExistingName() throws Exception
  {
    ExtRole toAddRole = new ExtRole(0, "ROLE_ADMIN");
    MvcResult result = this.mockMvc
        .perform(post("/roles").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(serialize(toAddRole)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
  }

  @Test
  public void testGetRoles() throws Exception
  {
    MvcResult result = this.mockMvc
        .perform(get("/roles").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
    ExtRole[] roleArray = deserialize(result, ExtRole[].class);
    Assert.assertNotNull(roleArray);
    Assert.assertEquals(roleArray.length, 2);
  }
  // PROTECTED METHODS ----------------------------------------------
  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

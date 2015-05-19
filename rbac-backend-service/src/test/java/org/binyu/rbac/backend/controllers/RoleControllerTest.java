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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Comparator;

import org.binyu.rbac.backend.AbstractIntegrationTest;
import org.binyu.rbac.backend.controllers.dtos.ExtResourcePermission;
import org.binyu.rbac.backend.controllers.dtos.ExtRole;
import org.binyu.rbac.dtos.ResourcePermission;
import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.services.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class RoleControllerTest extends AbstractIntegrationTest
{
  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  @Autowired
  RoleManagementService roleSrv;

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------

  /**
   * When send request to add a non-exist role
   * Then the role should be added.
   * @throws Exception
   */
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
    Role dbRole = roleSrv.getRoleById(role.getId());
    Assert.assertNotNull(dbRole);
    Assert.assertEquals(dbRole.getName(), dbRole.getName());
  }

  /**
   * Given there is already a role named 'ROLE_ADMIN'
   * When send request to add a role with name 'test'
   * Then the response should be
   * @throws Exception
   */
  @Test
  public void testAddRoleWithExistingName() throws Exception
  {
    String roleName = ROLE_ADMIN;
    Assert.assertNotNull(roleSrv.getRoleByName(roleName));

    ExtRole toAddRole = new ExtRole(0, roleName);
    MvcResult result = this.mockMvc
        .perform(post("/roles").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(serialize(toAddRole)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
  }

  /**
   * Given we have 2 roles in db
   * When send request to get all role list
   * Then the response should contains all the roles
   * @throws Exception
   */
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
    Assert.assertEquals(roleArray.length, 3);
  }

  /**
   * Given there is a role with id=1;
   * When send request to delete role id 1
   * Then the role should be deleted.
   * @throws Exception
   */
  @Test
  public void testDeleteRole() throws Exception
  {
    int roleId = 1;
    Assert.assertNotNull(roleSrv.getRoleById(roleId));
    this.mockMvc
        .perform(delete("/roles/" + roleId).accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    Assert.assertNull(roleSrv.getRoleById(roleId));
  }

  /**
   * Given we have role with the given id
   * When send request to get this role
   * Then the response should be as expected
   * @throws Exception
   */
  @Test(dataProvider = "getRoleByIdData")
  public void testGetRoleById(int roleId, ExtRole expRole) throws Exception
  {
    MvcResult result = this.mockMvc
        .perform(
            get("/roles/" + roleId).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
    ExtRole extRole = deserialize(result, ExtRole.class);
    Assert.assertEquals(extRole, expRole);
  }

  /**
   * When send request to get role by invalid roleId
   * Then the response CODE should be BAD_REQUEST
   * @throws Exception
   */
  @Test
  public void testGetRoleByIdWithInvalidRoleId() throws Exception
  {
    this.mockMvc
        .perform(
            get("/roles/-1").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

  /**
   * Given we have role with the given id
   * When send request to get this role's permissions
   * Then the response should be as expected
   * @throws Exception
   */
  @Test(dataProvider = "getRolePermissionsData")
  public void testGetRolePermissions(int roleId, int permCnt) throws Exception
  {
    MvcResult result = this.mockMvc
        .perform(
            get("/roles/" + roleId + "/permissions").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
    ExtResourcePermission[] permArray = deserialize(result, ExtResourcePermission[].class);
    Assert.assertNotNull(permArray);
    Assert.assertEquals(permArray.length, permCnt);
  }

  /**
   * Given we have role with the given id
   * When send request to set this role's permissions
   * Then the response should be as expected
   * @throws Exception
   */
  @Test(dataProvider = "setRolePermissionsData")
  public void testSetRolePermissions(int roleId, ResourcePermission[] permArray, boolean overwrite,
      ResourcePermission[] expPermArray) throws Exception
  {
    ExtResourcePermission[] extPermArray = new ExtResourcePermission[permArray.length];
    for (int i = 0; i < permArray.length; i++)
    {
      extPermArray[i] = ExtResourcePermission.fromInternal(permArray[i]);
    }
    this.mockMvc
        .perform(
            put("/roles/" + roleId + "/permissions").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .param("overwrite", String.valueOf(overwrite))
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(extPermArray))
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    ResourcePermission[] rtpermArray = roleSrv.getResourcePermissionByRoleId(roleId);
    sort(rtpermArray);
    sort(expPermArray);
    Assert.assertEquals(rtpermArray, expPermArray);
  }

  /**
   * When send request to set role's permissions with non-existing role id
   * Then the response code should be BAD_REQUEST
   * @throws Exception
   */
  public void testSetRolePermissionsWithInvalidRoleId() throws Exception
  {
    this.mockMvc
        .perform(
            put("/roles/-1/permissions").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(new ResourcePermission[0]))
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest());
  }

  // PROTECTED METHODS ----------------------------------------------
  // PRIVATE METHODS ------------------------------------------------

  private void sort(ResourcePermission[] permArray)
  {
    Arrays.sort(permArray, new Comparator<ResourcePermission>()
    {

      @Override
      public int compare(ResourcePermission perm1, ResourcePermission perm2)
      {
        return perm1.getResource().compareToIgnoreCase(perm2.getResource());
      }

    });
  }

  private int getRoleIdByName(String roleName)
  {
    Role dbRole = roleSrv.getRoleByName(roleName);
    Assert.assertNotNull(dbRole);
    return dbRole.getId();
  }

  @DataProvider(name = "getRolePermissionsData")
  private Object[][] getRolePermissionsData()
  {
    return new Object[][] {
      { getRoleIdByName(ROLE_ADMIN), 3 },
      { getRoleIdByName("ROLE_PLAIN_USER"), 0 },
      { -1, 0 } // not exist id
    };
  }

  @DataProvider(name = "getRoleByIdData")
  private Object[][] getRoleByIdData()
  {
    int adminRoleId = getRoleIdByName(ROLE_ADMIN);
    return new Object[][] {
      { adminRoleId, new ExtRole(adminRoleId, ROLE_ADMIN) },
    // { -1, null } // not exist id
    };
  }

  @DataProvider(name = "setRolePermissionsData")
  private Object[][] setRolePermissionsData()
  {
    ResourcePermission[] normalPermArray = { new ResourcePermission("users", 1), new ResourcePermission("roles", 2) };
    ResourcePermission[] fullPermArray = roleSrv.getResourcePermissionByRoleName(ROLE_ADMIN);
    ResourcePermission[] invalidResPermArray = { new ResourcePermission("xxx", 1), new ResourcePermission("roles", 2) };
    return new Object[][] {
      // role with no existing perm array, nooverwrite
      { getRoleIdByName("ROLE_PLAIN_USER"), normalPermArray, false, normalPermArray },
      // role with existing perm array, no overwrite
      { getRoleIdByName(ROLE_ADMIN), normalPermArray, false, fullPermArray },
      // role with existing perm array, overwrite
      { getRoleIdByName(ROLE_ADMIN), normalPermArray, true, normalPermArray },
      // role with no existing perm array, no overwrite, empty perm array to set
      { getRoleIdByName("ROLE_PLAIN_USER"), new ResourcePermission[0], false, new ResourcePermission[0] },
      // role with no existing perm array, no overwrite, perm array to set with invalid resource
      { getRoleIdByName("ROLE_PLAIN_USER"), invalidResPermArray, false,
        new ResourcePermission[] { new ResourcePermission("roles", 2) } },
    };
  }
  // ACCESSOR METHODS -----------------------------------------------

}

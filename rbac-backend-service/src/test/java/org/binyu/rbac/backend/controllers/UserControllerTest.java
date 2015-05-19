/********************************************************************
 * File Name:    UserControllerTest.java
 *
 * Date Created: May 19, 2015
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.util.Date;

import org.binyu.rbac.auth.EncryptionUtil;
import org.binyu.rbac.backend.AbstractIntegrationTest;
import org.binyu.rbac.backend.controllers.dtos.ExtRole;
import org.binyu.rbac.backend.controllers.dtos.ExtUser;
import org.binyu.rbac.daos.UserRoleMapper;
import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.dtos.User;
import org.binyu.rbac.services.RoleManagementService;
import org.binyu.rbac.services.UserManagementService;
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
public class UserControllerTest extends AbstractIntegrationTest
{
  private static final String ROLE_PLAIN_USER = "ROLE_PLAIN_USER";
  // CONSTANTS ------------------------------------------------------
  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  @Autowired
  UserManagementService userSrv;
  @Autowired
  RoleManagementService roleSrv;

  @Autowired
  private UserRoleMapper userRoleRepo;

  @Autowired
  private EncryptionUtil encryptionUtil;

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  /**
   * Given we have 3 users in db
   * When send request to get all user list
   * Then the response should contains all the users
   * @throws Exception
   */
  @Test
  public void testGetAllUsers() throws Exception
  {
    MvcResult result = this.mockMvc
        .perform(get("/users").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
    ExtUser[] userArray = deserialize(result, ExtUser[].class);
    Assert.assertNotNull(userArray);
    Assert.assertEquals(userArray.length, 4);
  }

  /**
   * When send request to add a non-exist user
   * Then the user should be added.
   * @throws Exception
   */
  @Test
  public void testAddUser() throws Exception
  {
    Date date = new Date();
    ExtUser toAddUser = new ExtUser(0, "added", "pwd", date, date, 0);
    MvcResult result = this.mockMvc
        .perform(post("/users").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(serialize(toAddUser)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
    // 1. assert response
    ExtUser rtUser = deserialize(result, ExtUser.class);
    Assert.assertNotNull(rtUser);
    Assert.assertNotEquals(rtUser.getId(), 0);
    Assert.assertEquals(rtUser.getName(), toAddUser.getName());
    Assert.assertNull(rtUser.getPassword());
    Assert.assertEquals(rtUser.getDeleted(), toAddUser.getDeleted());

    // 2. assert db
    User dbUser = userSrv.getUserById(rtUser.getId());
    Assert.assertNotNull(dbUser);
    Assert.assertEquals(dbUser.getName(), toAddUser.getName());
    Assert.assertTrue(encryptionUtil.matchesHMAC(toAddUser.getPassword(), dbUser.getPassword()));
    Assert.assertEquals(dbUser.getCreateTime(), rtUser.getCreateTime());
    Assert.assertEquals(dbUser.getUpdateTime(), dbUser.getCreateTime());
    Assert.assertEquals(dbUser.getDeleted(), toAddUser.getDeleted());
  }

  /**
   * Given we have a user named 'test' in db
   * When send request to add a user with exist name
   * Then the response code should be BAD_REQUEST
   * @throws Exception
   */
  @Test
  public void testAddUserWithExistName() throws Exception
  {
    Date date = new Date();
    ExtUser toAddUser = new ExtUser(0, "test", "pwd", date, date, 0);
    this.mockMvc
        .perform(post("/users").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(serialize(toAddUser)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest());
  }

  /**
   * Given there is a user with id=1;
   * When send request to delete user id 1
   * Then the user should be deleted.
   * @throws Exception
   */
  @Test
  public void testDeleteUser() throws Exception
  {
    int roleId = 1;
    Assert.assertNotNull(userSrv.getUserById(roleId));
    this.mockMvc
        .perform(delete("/users/" + roleId).accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    Assert.assertNull(userSrv.getUserById(roleId));
  }

  /**
   * Given we have a user named 'test' in db
   * When send request to update this user
   * Then the user should be updated.
   * @throws Exception
   */
  @Test
  public void testUpdateUser() throws Exception
  {
    User iUser = userSrv.getUserByName("test");
    Assert.assertNotNull(iUser);
    ExtUser toUpdateUser = ExtUser.fromInternal(iUser);
    toUpdateUser.setName("newName");
    toUpdateUser.setPassword("xxx");
    toUpdateUser.setDeleted(1);
    MvcResult result = this.mockMvc
        .perform(put("/users/" + toUpdateUser.getId()).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(serialize(toUpdateUser)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
    // 1. assert the response
    assertReturnedUser(toUpdateUser, result);

    // 2. assert the db
    User dbUser = userSrv.getUserById(iUser.getId());
    Assert.assertNotNull(dbUser);
    // password should be encoded
    Assert.assertTrue(encryptionUtil.matchesHMAC(toUpdateUser.getPassword(), dbUser.getPassword()));
    dbUser.setPassword(toUpdateUser.getPassword());
    // returned update time should be different from create time
    Assert.assertNotEquals(dbUser.getCreateTime(), dbUser.getUpdateTime());
    // update time may be different from input
    dbUser.setUpdateTime(toUpdateUser.getUpdateTime());
    // all other fields should be equals
    Assert.assertEquals(dbUser, toUpdateUser.toInternal());
  }

  /**
   * Given we have the given user with some roles
   * When send request to get this user's roles
   * Then the response should contains its roles
   * @throws Exception
   */
  @Test(dataProvider = "getUserRolesData")
  public void testGetUserRoles(int userId, ExtRole[] expRoles) throws Exception
  {
    MvcResult result = this.mockMvc
        .perform(get("/users/" + userId + "/roles").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andReturn();
    ExtRole[] roleArray = deserialize(result, ExtRole[].class);
    Assert.assertNotNull(roleArray);
    Assert.assertEquals(roleArray, expRoles);
  }

  /**
   * When send request to get user's roles with invalid userId
   * Then the response code should be BAD_REQUEST
   * @throws Exception
   */
  @Test
  public void testGetUserRolesWithInvalidUserId() throws Exception
  {
    this.mockMvc
        .perform(get("/users/" + -1 + "/roles").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest());
  }

  /**
   * Given we the given user with some roles
   * When send request to get this user's roles
   * Then the response should contains its roles
   * @throws Exception
   */
  @Test(dataProvider = "assignUserRolesData")
  public void testAssignUserRoles(int userId, int[] roleIds, boolean overwrite, int[] expRoleIds) throws Exception
  {
    this.mockMvc
        .perform(put("/users/" + userId + "/roles")
            .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
            .param("overwrite", String.valueOf(overwrite))
            .contentType(MediaType.APPLICATION_JSON)
            .content(serialize(roleIds))
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    Integer[] dbRoleIds = userRoleRepo.getRoleIdsByUserId(userId);
    Assert.assertEquals(dbRoleIds, expRoleIds);
  }

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------
  private ExtUser assertReturnedUser(ExtUser input, MvcResult result) throws IOException, JsonParseException, JsonMappingException
  {
    ExtUser rtUser = deserialize(result, ExtUser.class);
    Assert.assertNotNull(rtUser);
    // returned password is null
    Assert.assertNull(rtUser.getPassword());
    rtUser.setPassword(input.getPassword());
    // returned update time may be different
    rtUser.setUpdateTime(input.getUpdateTime());
    // all other fields should be equals
    Assert.assertEquals(rtUser, input);
    return rtUser;
  }

  @DataProvider(name = "getUserRolesData")
  private Object[][] getUserRolesData()
  {
    User admin = userSrv.getUserByName("admin");
    User userwoRole = userSrv.getUserByName("user_wo_role");
    ExtRole adminRole = ExtRole.fromInternal(roleSrv.getRoleByName(ROLE_ADMIN));
    return new Object[][] {
      { admin.getId(), new ExtRole[] { adminRole } },
      { userwoRole.getId(), new ExtRole[0] },
    };
  }

  @DataProvider(name = "assignUserRolesData")
  private Object[][] assignUserRolesData()
  {
    User admin = userSrv.getUserByName("admin");
    User userwoRole = userSrv.getUserByName("user_wo_role");
    Role adminRole = roleSrv.getRoleByName(ROLE_ADMIN);
    Role userRole = roleSrv.getRoleByName(ROLE_PLAIN_USER);
    return new Object[][] {
      // a user without role, two valid role ids, no overwrite
      { userwoRole.getId(), new int[] { adminRole.getId(), userRole.getId() }, false,
        new int[] { adminRole.getId(), userRole.getId() } },
      // a user with role, one new role ids, no overwrite
      { admin.getId(), new int[] { userRole.getId() }, false,
        new int[] { adminRole.getId(), userRole.getId() } },
      // a user with role, two role ids(one is exist), no overwrite
      { admin.getId(), new int[] { adminRole.getId(), userRole.getId() }, false,
        new int[] { adminRole.getId(), userRole.getId() } },
      // a user with role, empty role ids, no overwrite
      { admin.getId(), new int[0], false, new int[] { adminRole.getId() } },
      // a user with role, one new role ids, overwrite
      { admin.getId(), new int[] { userRole.getId() }, true,
        new int[] { userRole.getId() } },
    };
  }

  // ACCESSOR METHODS -----------------------------------------------

}

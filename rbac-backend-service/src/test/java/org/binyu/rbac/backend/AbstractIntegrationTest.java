/********************************************************************
 * File Name:    AbstractIntegrationTest.java
 *
 * Date Created: May 18, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */

@SpringApplicationConfiguration(classes = RootConfiguration.class)
@ActiveProfiles({ "integration" })
@WebAppConfiguration
public class AbstractIntegrationTest extends AbstractTransactionalTestNGSpringContextTests
{
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------

  @Autowired
  private WebApplicationContext wac;
  protected MockMvc mockMvc;

  protected ObjectMapper mapper = new ObjectMapper();

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  @BeforeClass
  public void setup()
  {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  // PROTECTED METHODS ----------------------------------------------

  protected String serialize(Object obj) throws JsonProcessingException
  {
    return mapper.writeValueAsString(obj);
  }

  protected <T> T deserialize(MvcResult result, Class<T> cls) throws IOException, JsonParseException, JsonMappingException
  {
    return mapper.readValue(result.getResponse().getContentAsByteArray(), cls);
  }
  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

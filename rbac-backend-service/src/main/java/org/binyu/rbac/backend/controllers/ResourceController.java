/********************************************************************
 * File Name:    ResourceController.java
 *
 * Date Created: Apr 20, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.backend.controllers;

import org.binyu.rbac.backend.controllers.dtos.ExtResource;
import org.binyu.rbac.dtos.Resource;
import org.binyu.rbac.services.ResourceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
@RestController
@RequestMapping("/resources")
public class ResourceController
{
  // CONSTANTS ------------------------------------------------------

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------
  @Autowired
  private ResourceManagementService resSrv;

  // CONSTRUCTORS ---------------------------------------------------

  // PUBLIC METHODS -------------------------------------------------
  @RequestMapping(method = RequestMethod.GET)
  public ExtResource[] getAllResourceNames()
  {
    Resource[] resources = resSrv.getAllResources();
    ExtResource[] extResources = new ExtResource[resources.length];
    for (int i = 0; i < resources.length; i++)
    {
      extResources[i] = ExtResource.fromInternal(resources[i]);
    }
    return extResources;
  }
  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  // ACCESSOR METHODS -----------------------------------------------

}

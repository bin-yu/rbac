/**
 * 
 */
package org.binyu.rbac.services;

import org.binyu.rbac.daos.ResourceMapper;
import org.binyu.rbac.dtos.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class ResourceManagementService
{

  @Autowired
  private ResourceMapper resRepo;

  public Resource[] getAllResources()
  {
    return resRepo.getAllResources();
  }

  public Resource getResourceByName(String resource)
  {
    return resRepo.getResourceByName(resource);
  }

}

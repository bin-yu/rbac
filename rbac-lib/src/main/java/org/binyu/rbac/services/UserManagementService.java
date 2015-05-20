/**
 * 
 */
package org.binyu.rbac.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.binyu.rbac.auth.local.EncryptionUtil;
import org.binyu.rbac.daos.ResourcePermissionMapper;
import org.binyu.rbac.daos.RoleMapper;
import org.binyu.rbac.daos.UserMapper;
import org.binyu.rbac.daos.UserRoleMapper;
import org.binyu.rbac.dtos.ResourcePermission;
import org.binyu.rbac.dtos.Role;
import org.binyu.rbac.dtos.User;
import org.binyu.rbac.dtos.UserRole;
import org.binyu.rbac.exceptions.ServiceInputValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 *
 */
@Service
public class UserManagementService
{
  private static final Logger LOG = LoggerFactory
      .getLogger(UserManagementService.class);
  @Autowired
  private ResourcePermissionMapper permRepo;
  @Autowired
  private UserMapper userRepo;
  @Autowired
  private RoleMapper roleRepo;
  @Autowired
  private UserRoleMapper userRoleRepo;
  @Autowired
  private EncryptionUtil encryptionUtil;

  public ResourcePermission[] getResourcePermissionsByUser(String username)
  {
    return permRepo.getResourcePermissionsByUser(username);
  }

  public User getUserByDomainAndName(String domain, String username)
  {
    return userRepo.getUserByDomainAndName(domain, username);
  }

  @Transactional(rollbackFor = Exception.class)
  public User addUser(User user) throws ServiceInputValidationException
  {
    try
    {
      // user server date
      Date d = new Date();
      user.setCreateTime(d);
      user.setUpdateTime(d);
      user.setPassword(encryptionUtil.encryptHMAC(user.getPassword()));
      userRepo.addUser(user);
      return user;
    }
    catch (DataIntegrityViolationException e)
    {
      String msg = "invalid input cause failure to insert the user.";
      LOG.info(msg, e);
      throw new ServiceInputValidationException(msg);
    }
  }

  /*public void deleteUserByName(String name)
  {
    userRepo.deleteUserByName(name);
  }*/
  @Transactional(rollbackFor = Exception.class)
  public void deleteUserRolesByUserId(int userId)
  {
    userRoleRepo.deleteUserRolesByUserId(userId);
  }

  @Transactional(rollbackFor = Exception.class)
  public void addUserRole(UserRole userRole)
  {
    userRoleRepo.addUserRole(userRole);
  }

  public User[] getAllUsers()
  {
    return userRepo.getAllUsers();
  }

  public User getUserById(int id)
  {
    return userRepo.getUserById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteUserById(int id)
  {
    userRepo.deleteUserById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public void updateUser(User user)
  {
    // user server date
    Date d = new Date();
    user.setUpdateTime(d);
    user.setPassword(encryptionUtil.encryptHMAC(user.getPassword()));
    userRepo.updateUser(user);
  }

  public Role[] getRolesByUserId(int userId) throws ServiceInputValidationException
  {
    User user = userRepo.getUserById(userId);
    if (user == null)
    {
      throw new ServiceInputValidationException("User [id=" + userId + "] not exist.");
    }
    return roleRepo.getRolesByUserId(userId);
  }

  @Transactional(rollbackFor = Exception.class)
  public void assignRoles(int userId, int[] roleIds, boolean overwrite) throws ServiceInputValidationException
  {

    User user = userRepo.getUserById(userId);
    if (user == null)
    {
      throw new ServiceInputValidationException("User [id=" + userId + "] not exist.");
    }
    if (overwrite)
    {
      userRoleRepo.deleteUserRolesByUserId(userId);
    }
    if (roleIds != null && roleIds.length > 0)
    {
      Integer[] existRoles = userRoleRepo.getRoleIdsByUserId(userId);
      Set<Integer> roleSet = new HashSet<Integer>(roleIds.length);
      for (int id : roleIds)
      {
        roleSet.add(id);
      }
      for (int id : existRoles)
      {
        roleSet.remove(id);
      }
      for (int roleId : roleSet)
      {
        Role role = roleRepo.getRoleById(roleId);
        if (role != null)
        {
          userRoleRepo.addUserRole(new UserRole(user.getId(), role.getId()));
        }
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public User getOrCreateUser(String domain, String username) throws ServiceInputValidationException
  {
    User localUser = userRepo.getUserByDomainAndName(domain, username);
    if (localUser == null)
    {
      LOG.info("User " + domain + "\\" + username + " not exist in local db, so trying to create it...");

      localUser = addUser(new User(domain, username, null));
    }
    LOG.info("User " + domain + "\\" + username + " maps to local db : " + localUser);
    return localUser;
  }

  public Role[] getRolesByDomainAndUserNames(String domain, List<String> userNameList)
  {
    return roleRepo.getRolesByDomainAndUserNames(domain, userNameList);
  }

}

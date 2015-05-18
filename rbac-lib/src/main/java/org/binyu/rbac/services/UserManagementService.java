/**
 * 
 */
package org.binyu.rbac.services;

import org.binyu.rbac.daos.ResourcePermissionMapper;
import org.binyu.rbac.daos.UserMapper;
import org.binyu.rbac.daos.UserRoleMapper;
import org.binyu.rbac.dtos.ResourcePermission;
import org.binyu.rbac.dtos.User;
import org.binyu.rbac.dtos.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service
public class UserManagementService {
	@Autowired
	private ResourcePermissionMapper permRepo;
	@Autowired
	private UserMapper userRepo;
	@Autowired
	private UserRoleMapper userRoleRepo;

	public ResourcePermission[] getResourcePermissionsByUser(String username) {
		return permRepo.getResourcePermissionsByUser(username);
	}

	public User getUserByName(String username) {
		return userRepo.getUserByName(username);
	}

	public void addUser(User user) {
		userRepo.addUser(user);
	}

	public void deleteUserByName(String name) {
		userRepo.deleteUserByName(name);
	}

	public void deleteUserRolesByUserId(int userId) {
		userRoleRepo.deleteUserRolesByUserId(userId);
	}

	public void addUserRole(UserRole userRole) {
		userRoleRepo.addUserRole(userRole);
	}

}

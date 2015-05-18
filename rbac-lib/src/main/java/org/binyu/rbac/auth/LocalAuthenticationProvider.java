/**
 * 
 */
package org.binyu.rbac.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component
public class LocalAuthenticationProvider extends DaoAuthenticationProvider {
	@Autowired
	public LocalAuthenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder myPasswordEncoder) {
		super.setUserDetailsService(userDetailsService);
		super.setPasswordEncoder(myPasswordEncoder);
	}
}

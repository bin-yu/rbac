/**
 * 
 */
package org.binyu.rbac.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.binyu.rbac.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component
public class RestAuthConfigure extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private UserManagementService userSrv;

	public void init(HttpSecurity http) throws Exception {
		registerDefaultAuthenticationEntryPoint(http);
	}

	@SuppressWarnings("unchecked")
	private void registerDefaultAuthenticationEntryPoint(HttpSecurity http) {
		ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling = http
				.getConfigurer(ExceptionHandlingConfigurer.class);
		if (exceptionHandling == null) {
			return;
		}
		exceptionHandling.defaultAuthenticationEntryPointFor(
				postProcess(new AuthenticationEntryPoint() {

					public void commence(HttpServletRequest request,
							HttpServletResponse response,
							AuthenticationException authException)
							throws IOException, ServletException {
						// for any authentication exception, send 401
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								authException.getMessage());
					}

				}), AnyRequestMatcher.INSTANCE);

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		RestAuthenticationFilter authFilter = new RestAuthenticationFilter();
		AuthenticationManager authenticationManager = http
				.getSharedObject(AuthenticationManager.class);
		authFilter.setAuthenticationManager(authenticationManager);
		authFilter.setSessionAuthenticationStrategy(http
				.getSharedObject(SessionAuthenticationStrategy.class));
		authFilter.setUserManagementService(userSrv);
		// authFilter.setRememberMeServices(rememberMeServices);
		authFilter = postProcess(authFilter);
		http.addFilterAfter(authFilter,
				AbstractPreAuthenticatedProcessingFilter.class);
	}
}

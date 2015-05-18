/**
 * 
 */
package org.binyu.rbac.backend.config;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.binyu.rbac.auth.LocalAuthenticationProvider;
import org.binyu.rbac.auth.RestAuthConfigure;
import org.binyu.rbac.authz.RBACWebSecurityExpressionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author Administrator
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LocalAuthenticationProvider authProvider;
	@Autowired
	private RBACWebSecurityExpressionHandler expHandler;
	@Autowired
	private RestAuthConfigure restAuthConfigure;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authProvider);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().requireCsrfProtectionMatcher(
				new AndRequestMatcher(new UpdateRequestMatcher(),
						new NegatedRequestMatcher(new AntPathRequestMatcher(
								"/login"))));

		http.authorizeRequests().expressionHandler(expHandler);
		http.authorizeRequests().antMatchers("/users/**")
				.access("authenticated and #rbac.accessRes('users')")
				.antMatchers("/roles/**")
				.access("authenticated and #rbac.accessRes('roles')")
				.antMatchers("/resources/**")
				.access("authenticated and #rbac.accessRes('resources')")
				.anyRequest().authenticated();
		http.apply(restAuthConfigure);
	}

	static class UpdateRequestMatcher implements RequestMatcher {
		// CONSTANTS ------------------------------------------------------
		private Pattern allowedMethods = Pattern
				.compile("^(GET|HEAD|TRACE|OPTIONS)$");

		// CLASS VARIABLES ------------------------------------------------

		// INSTANCE VARIABLES ---------------------------------------------

		// CONSTRUCTORS ---------------------------------------------------

		// PUBLIC METHODS -------------------------------------------------

		public boolean matches(HttpServletRequest request) {
			return !allowedMethods.matcher(request.getMethod()).matches();
		}
		// PROTECTED METHODS ----------------------------------------------

		// PRIVATE METHODS ------------------------------------------------

		// ACCESSOR METHODS -----------------------------------------------

	}
}

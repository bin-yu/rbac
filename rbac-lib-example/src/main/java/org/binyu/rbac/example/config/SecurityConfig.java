/**
 * 
 */
package org.binyu.rbac.example.config;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.binyu.rbac.RBACContextLoader;
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
	RBACContextLoader rbac;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(rbac.getLocalAuthenticationProvider());
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().requireCsrfProtectionMatcher(
				new AndRequestMatcher(new UpdateRequestMatcher(),
						new NegatedRequestMatcher(new AntPathRequestMatcher(
								"/login"))));

		http.authorizeRequests().expressionHandler(rbac.getExpressionHandler());
		http.authorizeRequests().antMatchers("/hello/**")
				.access("authenticated and #rbac.accessRes('users')")
				.anyRequest().authenticated();
		http.apply(rbac.getRestAuthConfigure());
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

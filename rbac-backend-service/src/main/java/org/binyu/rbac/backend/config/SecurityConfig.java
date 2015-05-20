/**
 * 
 */
package org.binyu.rbac.backend.config;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.binyu.rbac.auth.CompositeAuthenticationProvider;
import org.binyu.rbac.auth.RestAuthConfigure;
import org.binyu.rbac.authz.RBACWebSecurityExpressionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

  @Autowired
  private CompositeAuthenticationProvider authProvider;
  @Autowired
  private RBACWebSecurityExpressionHandler expHandler;
  @Autowired
  private RestAuthConfigure restAuthConfigure;

  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception
  {
    auth.authenticationProvider(authProvider);
  }

  protected void configure(HttpSecurity http) throws Exception
  {
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
    http.logout().logoutSuccessHandler(new LogoutSuccessHandler()
    {

      @Override
      public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
          throws IOException, ServletException
      {
        // do nothing
      }

    });
  }

  static class UpdateRequestMatcher implements RequestMatcher
  {
    // CONSTANTS ------------------------------------------------------
    private Pattern allowedMethods = Pattern
        .compile("^(GET|HEAD|TRACE|OPTIONS)$");

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------

    public boolean matches(HttpServletRequest request)
    {
      return !allowedMethods.matcher(request.getMethod()).matches();
    }
    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

  }
}

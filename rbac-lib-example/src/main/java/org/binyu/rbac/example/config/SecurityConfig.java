/**
 * 
 */
package org.binyu.rbac.example.config;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.binyu.rbac.RBACContextLoader;
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
  RBACContextLoader rbac;

  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception
  {
    auth.authenticationProvider(rbac.getCompositeAuthenticationProvider());
  }

  protected void configure(HttpSecurity http) throws Exception
  {
    http.csrf().requireCsrfProtectionMatcher(
        new AndRequestMatcher(new UpdateRequestMatcher(),
            new NegatedRequestMatcher(new AntPathRequestMatcher(
                "/login")),
            new NegatedRequestMatcher(new AntPathRequestMatcher(
                "/error"))));
    // register our custom SPEL expression handler
    http.authorizeRequests().expressionHandler(rbac.getExpressionHandler());
    // define the access expression for each URL.
    http.authorizeRequests().antMatchers("/hello/**")
        .access("authenticated and #rbac.accessRes('users')")
        .anyRequest().authenticated();
    // use our custom RESTfule authentication filter
    http.apply(rbac.getRestAuthConfigure());
    http.logout().logoutSuccessHandler(new LogoutSuccessHandler()
    {

      @Override
      public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
          throws IOException, ServletException
      {
        // do nothing
      }

    });
    http.rememberMe().userDetailsService(rbac.getUserDetailsService());
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

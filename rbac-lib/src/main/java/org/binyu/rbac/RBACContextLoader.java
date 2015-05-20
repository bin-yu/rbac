/**
 * 
 */
package org.binyu.rbac;

import javax.sql.DataSource;

import org.binyu.rbac.auth.CompositeAuthenticationProvider;
import org.binyu.rbac.auth.RestAuthConfigure;
import org.binyu.rbac.auth.local.LocalAuthenticationProvider;
import org.binyu.rbac.authz.RBACWebSecurityExpressionHandler;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Administrator
 *
 */
public class RBACContextLoader
{
  private ApplicationContext ctx;

  public RBACContextLoader(DataSource ds)
  {
    ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext();
    ConfigurableListableBeanFactory beanFactory = ctx.getBeanFactory();
    beanFactory.registerSingleton("dataSource", ds);
    ((AnnotationConfigApplicationContext) ctx).register(RBACConfig.class);
    ctx.refresh();
    this.ctx = ctx;
  }

  public RestAuthConfigure getRestAuthConfigure()
  {
    return ctx.getBean(RestAuthConfigure.class);
  }

  public LocalAuthenticationProvider getLocalAuthenticationProvider()
  {
    return ctx.getBean(LocalAuthenticationProvider.class);
  }

  public CompositeAuthenticationProvider getCompositeAuthenticationProvider()
  {
    return ctx.getBean(CompositeAuthenticationProvider.class);
  }

  public RBACWebSecurityExpressionHandler getExpressionHandler()
  {
    return ctx.getBean(RBACWebSecurityExpressionHandler.class);
  }

  public UserDetailsService getUserDetailsService()
  {
    return ctx.getBean(UserDetailsService.class);
  }

}

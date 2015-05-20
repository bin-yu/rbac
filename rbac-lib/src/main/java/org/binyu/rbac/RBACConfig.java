/**
 * 
 */
package org.binyu.rbac;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 *
 */
@Configuration
@ComponentScan({ "org.binyu.rbac.auth", "org.binyu.rbac.auth.ad", "org.binyu.rbac.auth.local", "org.binyu.rbac.authz",
  "org.binyu.rbac.services" })
@MapperScan(basePackages = "org.binyu.rbac.daos")
public class RBACConfig
{
  @Bean
  public SqlSessionFactoryBean createSessionFactory(DataSource dataSource)
  {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
    Properties p = new Properties();
    // p.setProperty("Oracle", "oracle");
    p.setProperty("HSQL", "hsql");
    databaseIdProvider.setProperties(p);
    sessionFactory.setDatabaseIdProvider(databaseIdProvider);
    return sessionFactory;
  }
}

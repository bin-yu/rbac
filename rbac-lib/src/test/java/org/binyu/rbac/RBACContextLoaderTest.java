/**
 * 
 */
package org.binyu.rbac;

import org.binyu.rbac.auth.RestAuthConfigure;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Administrator
 *
 */
public class RBACContextLoaderTest
{
  public EmbeddedDatabase createDataSource()
  {
    return new EmbeddedDatabaseBuilder().setName("rbac")
        .setType(EmbeddedDatabaseType.HSQL)
        .addScripts("schema-hsqldb.sql").addScript("data-hsqldb.sql")
        .build();
  }

  EmbeddedDatabase dataSource;

  RBACContextLoader loader;

  @BeforeClass
  public void init()
  {
    dataSource = createDataSource();
    loader = new RBACContextLoader(dataSource);
  }

  @AfterClass
  public void destroy()
  {
    dataSource.shutdown();
  }

  @Test
  public void testGetRestAuthConfigure()
  {
    RestAuthConfigure configure = loader.getRestAuthConfigure();
    Assert.assertNotNull(configure);
  }

  @Test
  public void testGetCompositeAuthenticationProvider()
  {
    AuthenticationProvider provider = loader.getCompositeAuthenticationProvider();
    Assert.assertNotNull(provider);
  }

  @Test
  public void testGetLocalAuthenticationProvider()
  {
    AuthenticationProvider provider = loader.getLocalAuthenticationProvider();
    Assert.assertNotNull(provider);
  }
}

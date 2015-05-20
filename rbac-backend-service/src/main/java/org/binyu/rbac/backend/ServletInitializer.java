package org.binyu.rbac.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

public class ServletInitializer extends SpringBootServletInitializer
{

  @Override
  protected SpringApplicationBuilder configure(
      SpringApplicationBuilder application)
  {
    return application.sources(RootConfiguration.class);
  }

  @Override
  protected WebApplicationContext run(SpringApplication
      application)
  { // removing ErrorPageFilter, we don't need it
    application.getSources().remove(ErrorPageFilter.class);
    return super.run(application);
  }

}

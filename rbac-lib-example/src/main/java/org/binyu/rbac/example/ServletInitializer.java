package org.binyu.rbac.example;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(RootConfiguration.class);
	}

	/*
	 * @Override protected WebApplicationContext run(SpringApplication
	 * application) { // removing ErrorPageFilter, we don't need it
	 * application.getSources().remove(ErrorPageFilter.class); return
	 * super.run(application); }
	 */
}

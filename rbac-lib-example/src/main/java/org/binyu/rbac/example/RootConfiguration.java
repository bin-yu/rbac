/********************************************************************
 * File Name:    RootConfiguration.java
 *
 * Date Created: Apr 19, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.example;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.binyu.rbac.RBACContextLoader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
@Configuration
@EnableAutoConfiguration(exclude = { AuthenticationManagerConfiguration.class,
		SecurityAutoConfiguration.class, WebSocketAutoConfiguration.class })
@ComponentScan
public class RootConfiguration {
	// CONSTANTS ------------------------------------------------------

	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------

	// CONSTRUCTORS ---------------------------------------------------

	// PUBLIC METHODS -------------------------------------------------
	@Bean
	public RBACContextLoader rbac(DataSource dataSource) {
		return new RBACContextLoader(dataSource);
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public FilterRegistrationBean configureAjaxCorsFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setName("AjaxCorsFilter");
		bean.addUrlPatterns("/*");
		bean.setFilter(new Filter() {

			@Override
			public void init(FilterConfig filterConfig) throws ServletException {

			}

			@Override
			public void doFilter(ServletRequest req, ServletResponse res,
					FilterChain chain) throws IOException, ServletException {
				HttpServletRequest request = (HttpServletRequest) req;
				HttpServletResponse response = (HttpServletResponse) res;
				this.allowAjaxCros(response);
				if (!"OPTIONS".equals(request.getMethod())) {
					chain.doFilter(req, res);
				}
			}

			private void allowAjaxCros(HttpServletResponse response) {
				response.setHeader("Access-Control-Allow-Origin",
						"http://localhost:9000");
				response.setHeader("Access-Control-Expose-Headers",
						"X-CSRF-TOKEN");
				response.setHeader("Access-Control-Allow-Methods",
						"GET, POST, OPTIONS, PUT, DELETE");
				response.setHeader("Access-Control-Allow-Headers",
						"Content-Type, Authorization, Accept, X-Requested-With, X-CSRF-TOKEN");
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader("Access-Control-Max-Age", "3600");
			}

			@Override
			public void destroy() {

			}
		});
		return bean;
	}
	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

}

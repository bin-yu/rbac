Role Based Access Control Library
=================================

**This is an authentication and authorization library implementing role based access control, which is to be plugged into Spring Security Framework.**

Features
--------

 1. Role based authorization. Can be configured using SPEL expression.
 2. Local authentication provider. Password is encoded using HMAC-SHA256. Can be used w/wo 1).
 3. Active Directory authentication provider. Can be only be used with 4)
 4. Composite authentication provider. Support mixed authentication mechanism in a single app. Can be used w/wo 1).
 5. Restful authentication filter. Support authenticate via a JSON-format REST request. Can be used w/wo above all.

Usage
-----
 1. add rbac-lib to your maven dependency
 
	    <dependency>
			<groupId>org.binyu</groupId>
			<artifactId>rbac-lib</artifactId>
			<version>0.0.1</version>
	    </dependency>
 2. prepare a db for persisting RBAC objects: for MySQL, you can use rbac-lib-example/src/main/resources/schema-mysql.sql,data-mysql.sql to initialize the database.
 3. register a DataSource to Spring container.
 4. initialize a RBACContextLoader:
 
		RBACContextLoader rbac=new RBACContextLoader(dataSource);
 5. to use Local|Composite authenticatio provider in your spring security config
 
		Configuration
		@EnableWebSecurity
		public class SecurityConfig extends WebSecurityConfigurerAdapter
		{

		  @Autowired
		  RBACContextLoader rbac;

		  @Override
		  protected void configure(AuthenticationManagerBuilder auth)
			  throws Exception
		  {
			auth.authenticationProvider(rbac.getLocalAuthenticationProvider()); //or rbac.getCompositeAuthenticationProvider()
		  }
 6. to use Role based authorization:
 
		@Configuration
		@EnableWebSecurity
		public class SecurityConfig extends WebSecurityConfigurerAdapter
		{

		  @Autowired
		  RBACContextLoader rbac;
		 ...
		  protected void configure(HttpSecurity http) throws Exception
		  {
			...
			//register our custom SPEL expression handler
			http.authorizeRequests().expressionHandler(rbac.getExpressionHandler());
			//define the access expression for each URL.
			http.authorizeRequests().antMatchers("/hello/**").access("authenticated and #rbac.accessRes('users')");
 7. to use Restful authentication filter:
 
		protected void configure(HttpSecurity http) throws Exception
		  {
			...
			//use our custom RESTfule authentication filter
			http.apply(rbac.getRestAuthConfigure());
 8. use Spring security remember-me authentication with our authentication providers:
 
		http.rememberMe().userDetailsService(rbac.getUserDetailsService());
# CRUD-operations


===================== BASIC AUTHORIZATION IMPLEMENTATION ========================


1. directly set username and password in application.property file (just for testing purpose)
	
	spring.security.user.name=user
	spring.security.user.password=1234

----------------------------------------------------------------------------------

2. Basic auth for any request (inMemoryAuthenication)


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	     http
	      .csrf()
	      .disable()
	      .authorizeHttpRequests(authorize -> authorize
	        .anyRequest()
	        .fullyAuthenticated()
	      )
	      .httpBasic();
	    return http.build();
	 }
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.inMemoryAuthentication()
	      .withUser("user")
	      .password(passwordEncoder.encode("password"))  
	      .roles("USER");
	 }
}

----------------------------------------------------------------------------

3. Role based basic auth (inMemoryAuthenication)


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	// Authentication
		
	@Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User
        		.withUsername("admin")
                .password(passwordEncoder.encode("admin1234"))
                .roles("ADMIN")
                .build();
        UserDetails user = User
        		.withUsername("user")
                .password(passwordEncoder.encode("user1234"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
		// Authorization 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	return http.csrf().disable()
    		.authorizeHttpRequests()
    		.requestMatchers("/admin/**").hasRole("ADMIN")
    		.requestMatchers("/users/**").hasRole("USER")
    		.requestMatchers("/public/**").permitAll()
    		.anyRequest()
    		.authenticated()
    		.and()
    		.httpBasic()
            .and()
            .build();
    }

}

-----------------------------------------------------------------------------------

4. Basic auth using DB


================== JSON WEB TOKEN (JWT) AUTHENTICATION ================================


============================ OAuth 2 =============================================
 # This is a protocol that allows users to grant access to their resources to third-party applications without giving them their passwords


======================= OpenID Connect ===========================================
# This is a simple identity layer on top of the OAuth2 protocol. It allows clients to verify the identity of the end user based on the authentication performed by an authorization server.

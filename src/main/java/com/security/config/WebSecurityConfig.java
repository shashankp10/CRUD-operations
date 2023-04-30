package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
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

/**
 * 
 */
package com.opencodez.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	CustomLoginSuccessHandler customLoginSuccessHandler;
	
	@Autowired
	CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
        http
        	.authorizeRequests()
        		.antMatchers("/login**").permitAll()
        		.antMatchers("/static/**","/webjars/**").permitAll()
        		.antMatchers("/about").permitAll()
        		.antMatchers("/admin/**").fullyAuthenticated()
            	.antMatchers("/").permitAll()
            	.and()
            .formLogin()
            	.loginPage("/login")
            	.failureUrl("/login?error")
            	.successHandler(customLoginSuccessHandler)
            	.permitAll()
            	.and()
            .logout()
            	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            	.logoutSuccessHandler(customLogoutSuccessHandler)
            	.invalidateHttpSession(true)
            	.deleteCookies("JSESSIONID")
            	.permitAll();
        
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .inMemoryAuthentication()
			.withUser("julia").password("julia123").roles("USER").and()
			.withUser("lane").password("lane123").roles("USER").and()
			.withUser("jackie").password("jackie123").roles("USER");
	}
	
}
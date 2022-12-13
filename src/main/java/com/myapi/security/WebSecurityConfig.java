/**
 * Created by : Alan Nascimento on 12/09/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()

			.authorizeRequests()
			.antMatchers("/api/noauth/**").permitAll();

	}


	public void configure(WebSecurity webSecurity){
		webSecurity.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html", "/webjars/**");
	}

}

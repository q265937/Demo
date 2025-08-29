package com.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.user.filter.JwtFilter;
import com.user.service.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
    private JwtFilter jwtFilter;
    private final CustomUserService cuServ;

    public SecurityConfig(CustomUserService cuServ) {
        this.cuServ = cuServ;
    }

	
	@Bean
	SecurityFilterChain secChain(HttpSecurity http) throws Exception{
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("/user/register/**").permitAll()
						.requestMatchers("/user/login/**").permitAll()
						.requestMatchers("/user/**").permitAll()
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	 @Bean
	    AuthenticationProvider authProvider(){
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(cuServ);
	        provider.setPasswordEncoder(new BCryptPasswordEncoder());
	        return provider;
	    }

	    @Bean
	    PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception{
	        return config.getAuthenticationManager();
	    }
}

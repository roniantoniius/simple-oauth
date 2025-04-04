package com.roniantonius.simpleoauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final WebClient userInfoClient;
	
	public SecurityConfig(WebClient userInfoClient) {
		this.userInfoClient = userInfoClient;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.exceptionHandling(customizer -> customizer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
			.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(HttpMethod.GET, "/").permitAll()
					.requestMatchers(HttpMethod.GET, "/public/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
					.anyRequest().authenticated()
				)
			.oauth2ResourceServer(c -> c.opaqueToken(Customizer.withDefaults()));
		return http.build();
	}
	
	@Bean
	public OpaqueTokenIntrospector introspector() {
		return new GoogleOpaqueTokenIntrospector(userInfoClient);
	}
}
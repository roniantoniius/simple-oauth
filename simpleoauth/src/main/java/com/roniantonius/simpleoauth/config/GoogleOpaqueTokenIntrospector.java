package com.roniantonius.simpleoauth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import com.roniantonius.simpleoauth.dtos.UserInfo;

public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

	private final WebClient userInfoClient;
	
	public GoogleOpaqueTokenIntrospector(WebClient userInfoClient) {
		this.userInfoClient = userInfoClient;
	}
	
	@Override
	public OAuth2AuthenticatedPrincipal introspect(String token) {
		// TODO Auto-generated method stub
		UserInfo user = userInfoClient.get()
			.uri(uriBuilder -> uriBuilder.path("/oauth2/v3/userinfo").queryParam("access_token", token).build())
			.retrieve()
			.bodyToMono(UserInfo.class)
			.block();
		
		Map<String, Object> atribut = new HashMap<>();
		atribut.put("sub", user.sub());
		atribut.put("name", user.name());
		return new OAuth2IntrospectionAuthenticatedPrincipal(user.name(), atribut, null);
	}

}

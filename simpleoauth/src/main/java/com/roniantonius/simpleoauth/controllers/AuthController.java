package com.roniantonius.simpleoauth.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.roniantonius.simpleoauth.dtos.TokenDto;
import com.roniantonius.simpleoauth.dtos.UrlDto;

@RestController
public class AuthController {
	@Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}")
	private String clientId;
	
	@Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}")
	private String clientSecret;
	
	@GetMapping(path = "/auth/url")
	public ResponseEntity<UrlDto> auth(){
		String url = new GoogleAuthorizationCodeRequestUrl(
				clientId, // id client
				"http://localhost:4200", // redirect url yaitu Frontend
				Arrays.asList("email", "profile", "openid")) // list of scopes yang kita inginkan
		.build();
		
		return ResponseEntity.ok(new UrlDto(url));
	}
	
	@GetMapping(path = "/auth/callback")
	public ResponseEntity<TokenDto> callback(@RequestParam("code") String code){
		String accessToken;
		try {
			accessToken = new GoogleAuthorizationCodeTokenRequest(
					new NetHttpTransport(),
					new GsonFactory(),
					clientId,
					clientSecret,
					code,
					"http://localhost:4200").execute().getAccessToken();
		} catch (IOException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(new TokenDto(accessToken));
	}
}

package com.roniantonius.simpleoauth.dtos;

// we respect google api format on access token that contain user information
public record UserInfo(
		String sub,
		String name,
		String given_name,
		String family_name,
		String picture,
		String email,
		boolean email_verified,
		String locale) {
	
}
package com.roniantonius.simpleoauth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.simpleoauth.dtos.PesanDto;

@RestController
public class PrivateController {
	
	@GetMapping(path = "/pesans")
	public ResponseEntity<PesanDto> privatePesans(
			@AuthenticationPrincipal(expression = "name") String name
	) {
		return ResponseEntity.ok(new PesanDto("private content " + name));
	}
}

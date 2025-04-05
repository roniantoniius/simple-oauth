package com.roniantonius.simpleoauth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.simpleoauth.dtos.PesanDto;

@RestController
public class PublicController {
	
	@GetMapping(path = "/public/messages")
	public ResponseEntity<PesanDto> publicPesans() {
		return ResponseEntity.ok(new PesanDto("public konten"));
	}
}

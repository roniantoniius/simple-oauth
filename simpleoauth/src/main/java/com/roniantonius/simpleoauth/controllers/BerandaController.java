package com.roniantonius.simpleoauth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BerandaController {
	
	// perhaps public page for all people can access
	@GetMapping(path = "/")
	public String beranda() {
		return "index";
	}
}
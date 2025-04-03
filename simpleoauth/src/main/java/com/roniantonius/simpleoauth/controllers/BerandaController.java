package com.roniantonius.simpleoauth.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BerandaController {
	@GetMapping(path = "/")
	public String beranda(Model model, @AuthenticationPrincipal OAuth2User user) {
		model.addAttribute("userName", user.getAttribute("name"));
		return "index";
	}
}

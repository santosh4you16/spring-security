package com.santosh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String getHome() {
		return "<h1>WELCOME</h1>";
	}

	@GetMapping("/user")
	public String getUser() {
		return "<h1>WELCOME USER</h1>";
	}

	@GetMapping("/admin")
	public String getAdmin() {
		return "<h1>WELCOME ADMIN</h1>";
	}
}

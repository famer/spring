package com.timurtatarshaov.demoforum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	public String root() {
		return "redirect:/users/login";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
}

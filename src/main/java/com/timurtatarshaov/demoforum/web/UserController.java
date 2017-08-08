package com.timurtatarshaov.demoforum.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.timurtatarshaov.demoforum.model.User;
import com.timurtatarshaov.demoforum.service.UserService;
import com.timurtatarshaov.demoforum.validator.UserValidator;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
    private UserValidator userValidator;
	
	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "user/login";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "user/registration";
    }
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);
		
		if (bindingResult.hasErrors()) {
            return "user/registration";
        }
		
		userService.save(userForm);
        return "redirect:/users/login";
    }
}

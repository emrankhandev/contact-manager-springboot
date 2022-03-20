package com.springboot.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.contactmanager.helper.Message;
import com.springboot.contactmanager.model.User;
import com.springboot.contactmanager.repository.UserRepository;

@Controller
public class HomeController {


	@Autowired
	private UserRepository userRepository;

	//Home Controller
	@GetMapping("/")
	public String home(Model m) {
		
		m.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}

	//about Controller
	@GetMapping("/about")
	public String about(Model m) {
		
		m.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	//Signup Controller
	@GetMapping("/signup")
	public String signUp(Model m) {
		
		m.addAttribute("title", "Register - Smart Contact Manager");
		return "signup";
	}

	//Login Controller
	@GetMapping("/login")
	public String login(Model m) {
		
		m.addAttribute("title", "Login - Smart Contact Manager");
		return "login";
	}

	//Handler for registering user
	@PostMapping("/do-register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult br, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
		
		try {

			if(!agreement) {
				
				throw new Exception("you have not agreed terms and conditions");
			}
			
			if(br.hasErrors()) {
				model.addAttribute("user", user);
				return "signup";
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setName("default.png");
			User result = this.userRepository.save(user);
			System.out.println(result);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered...!", "alert-success"));
			return "signup";

		} catch(Exception e) {

			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong...!", "alert-danger"));
			return "signup";
		}
		
	}
}

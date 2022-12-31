package com.project.community.user.controller;

import com.project.community.user.model.UserInput;
import com.project.community.user.repository.UserRepository;
import com.project.community.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserRepository userRepository;
	private final UserService userService;

	@GetMapping("/user/register")
	public String register(){
		return "user/register";
	}

	@PostMapping("/user/register")
	public String registerSubmit(Model model, UserInput parameter) {

		boolean result = userService.register(parameter);
		model.addAttribute("result", result);
		return "user/register_complete";
	}

	@GetMapping("/user/email-auth")
	public String emailAuth(Model model, HttpServletRequest request) {

		String uuid = request.getParameter("id");

		boolean result = userService.emailAuth(uuid);
		model.addAttribute("result", result);

		return "user/email_auth";
	}
}

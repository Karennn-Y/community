package com.project.community.user.controller;

import com.project.community.admin.dto.UserDto;
import com.project.community.board.dto.BoardDto;
import com.project.community.board.model.BoardInput;
import com.project.community.board.model.BoardParam;
import com.project.community.board.service.BoardService;
import com.project.community.common.controller.BaseController;
import com.project.community.main.service.ServiceResult;
import com.project.community.user.model.ResetPasswordInput;
import com.project.community.user.model.UserInput;
import com.project.community.user.repository.UserRepository;
import com.project.community.user.service.UserService;
import com.project.community.util.PasswordUtils;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class UserController extends BaseController {

	private final UserRepository userRepository;
	private final UserService userService;

	private final BoardService boardService;

	@RequestMapping("/user/login")
	public String login() {
		return "user/login";
	}

	@GetMapping("/user/find/password")
	public String findPassword() {
		return "user/find_password";
	}

	@PostMapping("/user/find/password")
	public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {

		ServiceResult result = userService.sendResetPassword(parameter);
		if (!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "common/error";
		}

		model.addAttribute("result", result);
		return "user/find_password_result";
	}

	@GetMapping("/user/register")
	public String register(){
		return "user/register";
	}

	@PostMapping("/user/register")
	public String registerSubmit(Model model, UserInput parameter) {

		ServiceResult result = userService.register(parameter);
		if (!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "common/error";
		}
		model.addAttribute("result", result);
		return "user/register_complete";
	}

	@GetMapping("/user/email-auth")
	public String emailAUTH(Model model, HttpServletRequest request) {

		String uuid = request.getParameter("id");

		ServiceResult result = userService.emailAuth(uuid);
		if (!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "common/error";
		}

		model.addAttribute("result", result);
		return "user/email_auth";
	}

	@GetMapping("/user/info")
	public String userINFO(Model model, Principal principal) {

		String userId = principal.getName();
		UserDto detail = userService.detail(userId);

		model.addAttribute("detail", detail);
		return "user/info";
	}

	@PostMapping("/user/info")
	public String userINFOSubmit(Model model
		, UserInput parameter
		, Principal principal) {

		String userId = principal.getName();
		parameter.setUserId(userId);

		ServiceResult result = userService.updateUser(parameter);

		if (!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "common/error";
		}

		return "redirect:/user/info";
	}

	@GetMapping("/user/password")
	public String userPassword(Model model, Principal principal) {

		String userId = principal.getName();
		UserDto detail = userService.detail(userId);

		model.addAttribute("detail", detail);
		return "user/password";
	}

	@PostMapping("/user/password")
	public String userPasswordSubmit(Model model
									, UserInput parameter
									, Principal principal) {

		String userId = principal.getName();
		parameter.setUserId(userId);
		ServiceResult result = userService.updateUserPassword(parameter);

		if (!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "common/error";
		}

		return "redirect:/user/info";
	}

	@GetMapping("/user/withdraw")
	public String userWithdraw(Model model) {

		return "user/withdraw";
	}

	@PostMapping("/user/withdraw")
	public String userWithdrawSubmit(Model model
		, UserInput parameter
		, Principal principal) {

		String userId = principal.getName();

		ServiceResult result = userService.withdraw(userId, parameter.getPassword());

		if (!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			model.addAttribute("url", "");
			return "common/error";
		}
		return "redirect:/user/logout";
	}


	@GetMapping("/user/posts")
	public String userPosts(Model model, Principal principal, BoardParam parameter) {

		parameter.setUserId(principal.getName());

		List<BoardDto> list = boardService.userList(parameter);
		long totalCount = 0;
		if (!CollectionUtils.isEmpty(list)) {
			totalCount = list.get(0).getTotalCount();
		}

		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);;
		return "user/posts";
	}

	@PostMapping("/user/board/delete")
	public String userDEL(Model model, Principal principal, BoardInput parameter) {

		String userId = parameter.getUserId();

		if (!Objects.equals(userId, parameter.getUserId())) {
			model.addAttribute("message", "본인의 게시물만 삭제 가능합니다.");
			return "error/denied";
		}

		boolean result = boardService.userDEL(parameter.getIdList());

		return "redirect:/user/board/posts";
	}


	@GetMapping("/user/reset/password")
	public String resetPassword(Model model, HttpServletRequest request) {

		String uuid = request.getParameter("id");

		ServiceResult result = userService.checkResetPassword(uuid);
		if (!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "common/error";
		}

		model.addAttribute("result", result);
		return "user/reset_password";
	}

	@PostMapping("/user/reset/password")
	public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {

		ServiceResult result = userService.resetPassword(parameter.getId(), parameter.getPassword());
		if (!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "common/error";
		}

		model.addAttribute("result", result);
		return "user/reset_password_result";
	}
}

package com.project.community.admin.controller;

import com.project.community.admin.dto.LoginHistoryDto;
import com.project.community.admin.dto.UserDto;
import com.project.community.admin.model.LoginHistoryParam;
import com.project.community.admin.model.UserParam;
import com.project.community.admin.model.UserInput;
import com.project.community.admin.service.LoginHistoryService;
import com.project.community.user.service.UserService;
import com.project.community.util.PageUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AdminUserController {

	private final UserService userService;
	private final LoginHistoryService loginHistoryService;

	@GetMapping("/admin/user/list.do")
	public String list(Model model, UserParam parameter) {

		parameter.init();

		List<UserDto> users = userService.list(parameter);

		long totalCount = 0;
		if (users != null && users.size() > 0) {
			totalCount = users.get(0).getTotalCount();
		}
		String queryString = parameter.getQueryString();

		PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

		model.addAttribute("list", users);
		model.addAttribute("pager", pageUtil.pager());
		model.addAttribute("totalCount", totalCount);
		return "admin/user/list";
	}

	@GetMapping("/admin/user/detail.do")
	public String detail(Model model
		, UserParam userParameter
		, LoginHistoryParam historyParameter) {

		userParameter.init();
		historyParameter.init();

		UserDto user = userService.detail(userParameter.getUserId());
		List<LoginHistoryDto> logins = loginHistoryService.list(historyParameter);
		model.addAttribute("user", user);
		model.addAttribute("login", logins);

		return "admin/user/detail";
	}

	@PostMapping("/admin/user/status.do")
	public String status(Model model, UserInput parameter) {

		boolean result = userService.updateStatus(parameter.getUserId(), parameter.getUserStatus());

		return "redirect:/admin/user/detail.do?userId=" + parameter.getUserId();
	}

	@PostMapping("/admin/user/password.do")
	public String password(Model model, UserInput parameter) {

		boolean result = userService.updatePassword(parameter.getUserId(), parameter.getPassword());

		return "redirect:/admin/user/detail.do?userId=" + parameter.getUserId();
	}
}

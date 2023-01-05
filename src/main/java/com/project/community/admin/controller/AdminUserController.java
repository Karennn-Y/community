package com.project.community.admin.controller;

import com.project.community.admin.dto.UserDto;
import com.project.community.admin.model.UserParam;
import com.project.community.user.entity.User;
import com.project.community.user.service.UserService;
import com.project.community.util.PageUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminUserController {

	private final UserService userService;
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
}

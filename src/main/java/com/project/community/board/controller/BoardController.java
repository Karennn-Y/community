package com.project.community.board.controller;

import com.project.community.admin.dto.CategoryDto;
import com.project.community.admin.service.CategoryService;
import com.project.community.announcement.model.AnnouncementParam;
import com.project.community.announcement.service.AnnouncementService;
import com.project.community.board.dto.BoardDto;
import com.project.community.board.model.BoardInput;
import com.project.community.board.model.BoardParam;
import com.project.community.board.service.BoardService;
import com.project.community.common.controller.BaseController;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class BoardController extends BaseController {

	private final BoardService boardService;
	private final CategoryService categoryService;

	@GetMapping("/board/list")
	public String list(Model model, BoardParam parameter) {

		parameter.init();
		List<BoardDto> boardList = boardService.list(parameter);

		long totalCount = 0;
		if (!CollectionUtils.isEmpty(boardList)) {
			totalCount = boardList.get(0).getTotalCount();
		}

		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

		model.addAttribute("list", boardList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);

		return "board/list";
	}

	@GetMapping("/board/add")
	public String add(Model model) {
		model.addAttribute("category", categoryService.frontList(CategoryDto.builder().build()));
		return "board/add";
	}

	@PostMapping("/board/add")
	public String addSubmit(Model model, Principal principal, BoardInput parameter) {

		parameter.setUserId(principal.getName());
		boolean result = boardService.add(parameter);

		return "redirect:/board/list";
	}

}

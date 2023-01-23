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
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
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

	@GetMapping(value = {"/board/add", "/user/posts/edit/{id}"})
	public String add(Model model, HttpServletRequest request, BoardInput parameter, Principal principal) {
		String loginId = principal.getName();
		boolean editMode = request.getRequestURI().contains("/edit");
		BoardDto detail = new BoardDto();

		if (editMode) {
			long id = parameter.getId();
			BoardDto existBoard = boardService.getById(id);
			String userId = boardService.getUserId(existBoard);
			if (existBoard == null) {
				model.addAttribute("message", "게시글 정보가 존재하지 않습니다");
				return "common/error";
			}
			if (!Objects.equals(userId, loginId)) {
				model.addAttribute("message", "본인의 게시글만 수정할 수 있습니다.");
				return "common/error";
			}
			detail = existBoard;
		}
		model.addAttribute("detail", detail);
		model.addAttribute("editMode", editMode);
		model.addAttribute("category", categoryService.frontList(CategoryDto.builder().build()));
		return "board/add";
	}

	@PostMapping(value = {"/board/add", "/user/posts/edit/{id}"})
	public String addSubmit(Model model, HttpServletRequest request, Principal principal, BoardInput parameter) {
		String loginId = principal.getName();
		boolean editMode = request.getRequestURI().contains("/edit");
		if (editMode) {
			long id = parameter.getId();
			BoardDto existBoard = boardService.getById(id);
			String userId = boardService.getUserId(existBoard);
			if (existBoard == null) {
				model.addAttribute("message", "게시글 정보가 존재하지 않습니다");
				return "common/error";
			}
			if (!Objects.equals(userId, loginId)) {
				model.addAttribute("message", "본인의 게시글만 수정할 수 있습니다.");
				return "common/error";
			}
			boolean result = boardService.set(parameter);
		}
		parameter.setUserId(loginId);
		boolean result = boardService.add(parameter);

		return "redirect:/board/list";
	}

	@GetMapping("/board/{id}")
	public String detail (Model model, BoardParam parameter) {

		BoardDto board = boardService.detail(parameter.getId());
		model.addAttribute("detail", board);

		return "board/detail";
	}

}

package com.project.community.board.controller;

import com.project.community.admin.dto.CategoryDto;
import com.project.community.admin.service.CategoryService;
import com.project.community.board.dto.BoardDto;
import com.project.community.board.model.BoardInput;
import com.project.community.board.model.BoardParam;
import com.project.community.board.service.BoardService;
import com.project.community.common.controller.BaseController;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AdminBoardController extends BaseController {

	private final BoardService boardService;
	private final CategoryService categoryService;

	@GetMapping("/admin/board/list.do")
	public String list(Model model, BoardParam parameter) {

		parameter.init();

		List<BoardDto> boardList = boardService.adminList(parameter);

		long totalCount = 0;
		if (!CollectionUtils.isEmpty(boardList)) {
			totalCount = boardList.get(0).getTotalCount();
		}

		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

		model.addAttribute("list", boardList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);

		return "admin/board/list";
	}

	@GetMapping("/admin/board/{id}.do")
	public String detail (Model model, BoardParam parameter) {

		BoardDto board = boardService.detail(parameter.getId());
		model.addAttribute("detail", board);

		return "admin/board/detail";
	}

	@PostMapping("/admin/board/delete.do")
	public String del (BoardInput parameter) {
		boolean result = boardService.del(parameter.getIdList());

		return "redirect:/admin/board/list.do";
	}

}

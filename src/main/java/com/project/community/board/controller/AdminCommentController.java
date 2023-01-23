package com.project.community.board.controller;

import com.project.community.board.dto.CommentDto;
import com.project.community.board.model.BoardInput;
import com.project.community.board.model.CommentInput;
import com.project.community.board.model.CommentParam;
import com.project.community.board.service.CommentService;
import com.project.community.common.controller.BaseController;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AdminCommentController extends BaseController {
	private final CommentService commentService;


	@GetMapping("/admin/board/comment")
	public String list(Model model, CommentParam parameter) {
		parameter.init();
		List<CommentDto> commentList = commentService.list(parameter);

		long totalCount = 0;
		if (!CollectionUtils.isEmpty(commentList)) {
			totalCount = commentList.get(0).getTotalCount();
		}

		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

		model.addAttribute("list", commentList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);

		return "admin/board/comment/list";
	}

	@PostMapping("/admin/board/comment/delete.do")
	public String del (BoardInput parameter) {
		boolean result = commentService.del(parameter.getIdList());

		return "redirect:/admin/board/list.do";
	}

}

package com.project.community.announcement.controller;

import com.project.community.announcement.dto.AnnouncementDto;
import com.project.community.announcement.model.AnnouncementInput;
import com.project.community.announcement.model.AnnouncementParam;
import com.project.community.announcement.service.AnnouncementService;
import com.project.community.common.controller.BaseController;
import com.project.community.util.PageUtil;
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
public class AdminAnnouncementController extends BaseController {

	private final AnnouncementService announcementService;

	@GetMapping("/admin/announcement/list.do")
	public String list(Model model, AnnouncementParam parameter) {
		parameter.init();
		List<AnnouncementDto> announcementList = announcementService.list(parameter);

		long totalCount = 0;
		if (!CollectionUtils.isEmpty(announcementList)) {
			totalCount = announcementList.get(0).getTotalCount();
		}

 		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

		model.addAttribute("list", announcementList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);

		return "admin/announcement/list";
	}

	@GetMapping(value = {"/admin/announcement/add.do", "/admin/announcement/edit.do"})
	public String add(Model model
					, HttpServletRequest request
					, AnnouncementInput parameter) {

		boolean editMode = request.getRequestURI().contains("/edit.do");
		AnnouncementDto detail = new AnnouncementDto();

		if (editMode) {
			long id = parameter.getId();
			AnnouncementDto existAnnouncement = announcementService.getById(id);

			if (existAnnouncement == null){
				// error
				model.addAttribute("message", "공지사항 정보가 존재하지 않습니다.");
				return "common/error";
			}
			detail = existAnnouncement;
		}
		model.addAttribute("detail", detail);
		model.addAttribute("editMode", editMode);
		return "admin/announcement/add";
	}

	@PostMapping(value = {"/admin/announcement/add.do", "/admin/announcement/edit.do"})
	public String addSubmit(Model model
			, HttpServletRequest request
			, AnnouncementInput parameter) {
		boolean editMode = request.getRequestURI().contains("/edit.do");

		if (editMode) {
			long id = parameter.getId();
			AnnouncementDto existAnnouncement = announcementService.getById(id);

			if (existAnnouncement == null){
				// error
				model.addAttribute("message", "공지사항 정보가 존재하지 않습니다.");
				return "common/error";
			}
			boolean result = announcementService.set(parameter);
		} else {
			boolean result = announcementService.add(parameter);
		}

		return "redirect:/admin/announcement/list.do";
	}



	@PostMapping("/admin/announcement/delete.do")
	public String del(Model model
		, HttpServletRequest request
		, AnnouncementInput parameter) {

		boolean result = announcementService.del(parameter.getIdList());

		return "redirect:/admin/announcement/list.do";
	}

}

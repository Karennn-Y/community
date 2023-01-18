package com.project.community.announcement.controller;

import com.project.community.announcement.dto.AnnouncementDto;
import com.project.community.announcement.model.AnnouncementParam;
import com.project.community.announcement.service.AnnouncementService;
import com.project.community.common.controller.BaseController;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AnnouncementController extends BaseController {

	private final AnnouncementService announcementService;

	@GetMapping("/announcement/list")
	public String list(Model model, AnnouncementParam parameter) {

		parameter.init();
		List<AnnouncementDto> announcementList = announcementService.frontList(parameter);

		long totalCount = 0;
		if (!CollectionUtils.isEmpty(announcementList)) {
			totalCount = announcementList.get(0).getTotalCount();
		}

		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

		model.addAttribute("list", announcementList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);

		return "announcement/list";
	}

	@GetMapping("/announcement/{id}")
	public String detail (Model model, AnnouncementParam parameter) {

		AnnouncementDto detail = announcementService.frontDetail(parameter.getId());
		model.addAttribute("detail", detail);

		return "announcement/detail";
	}

}

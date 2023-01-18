package com.project.community.announcement.service;

import com.project.community.announcement.dto.AnnouncementDto;
import com.project.community.announcement.model.AnnouncementInput;
import com.project.community.announcement.model.AnnouncementParam;
import java.util.List;

public interface AnnouncementService {

	// 공지사항 등록
	boolean add(AnnouncementInput parameter);

	// 공지사항 수정
	boolean set(AnnouncementInput parameter);

	// 공지사항 목록
	List<AnnouncementDto> list(AnnouncementParam parameter);

	// 공지사항 상세정보
	AnnouncementDto getById(long id);

	// 공지사항 삭제
	boolean del(String idList);

	// 사용자 공지사항 목록
	List<AnnouncementDto> frontList(AnnouncementParam parameter);
	// 사용자 공지사항 상세정보
	AnnouncementDto frontDetail(long id);
}

package com.project.community.announcement.service.impl;

import com.project.community.announcement.dto.AnnouncementDto;
import com.project.community.announcement.entity.Announcement;
import com.project.community.announcement.mapper.AnnouncementMapper;
import com.project.community.announcement.model.AnnouncementInput;
import com.project.community.announcement.model.AnnouncementParam;
import com.project.community.announcement.repository.AnnouncementRepository;
import com.project.community.announcement.service.AnnouncementService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

	private final AnnouncementRepository announcementRepository;
	private final AnnouncementMapper announcementMapper;

	private LocalDate getLocalDate(String value) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try{
			return LocalDate.parse(value, formatter);
		} catch (Exception e){

		}
		return null;
	}


	@Override
	public boolean add(AnnouncementInput parameter) {
		LocalDate postDt = getLocalDate(parameter.getPostDtText());
		Announcement announcement = Announcement.builder()
			.subject(parameter.getSubject())
			.contents(parameter.getContents())
			.regDt(LocalDateTime.now())
			.registrant("관리자")
			.postDt(postDt)
			.build();
		announcementRepository.save(announcement);

		return true;
	}

	@Override
	public boolean set(AnnouncementInput parameter) {

		LocalDate postDt = getLocalDate(parameter.getPostDtText());
		Optional<Announcement> optionalAnnouncement = announcementRepository.findById(parameter.getId());
		if (!optionalAnnouncement.isPresent()) {
			return false;
		}

		Announcement announcement = optionalAnnouncement.get();

		announcement.setSubject(parameter.getSubject());
		announcement.setContents(parameter.getContents());
		announcement.setRegistrant("관리자");
		announcement.setPostDt(postDt);
		announcement.setUdtDt(LocalDateTime.now());

		announcementRepository.save(announcement);

		return true;
	}

	@Override
	public List<AnnouncementDto> list(AnnouncementParam parameter) {

		long totalCount = announcementMapper.selectListCount(parameter);

		List<AnnouncementDto> list = announcementMapper.selectList(parameter);
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (AnnouncementDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount - parameter.getPageStart() - i);
				i++;
			}
		}

		return list;
	}

	@Override
	public AnnouncementDto getById(long id) {
		return announcementRepository.findById(id).map(AnnouncementDto::of).orElse(null);
	}

	@Override
	public boolean del(String idList) {

		if (idList != null && idList.length() > 0) {
			String[] ids = idList.split(",");
			for (String x : ids) {
				long id = 0L;
				try {
					id = Long.parseLong(x);
				} catch (Exception e) {
				}

				if (id > 0) {
					announcementRepository.deleteById(id);
				}
			}
		}
		return true;
	}

}

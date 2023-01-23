package com.project.community.announcement.dto;

import com.project.community.announcement.entity.Announcement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AnnouncementDto {

	Long id;
	long categoryId;
	String subject;
	String contents;
	String registrant;

	LocalDateTime regDt;
	LocalDateTime udtDt;
	LocalDate postDt;

	// indexing 위한 추가컬럼
	long totalCount;
	long seq; // sequence 처리


	public static AnnouncementDto of(Announcement announcement) {
		return AnnouncementDto.builder()
			.id(announcement.getId())
			.subject(announcement.getSubject())
			.contents(announcement.getContents())
			.registrant(announcement.getRegistrant())
			.regDt(announcement.getRegDt())
			.udtDt(announcement.getUdtDt())
			.postDt(announcement.getPostDt())
			.build();
	}

	public static List<AnnouncementDto> of (List<Announcement> announcements) {
		if (announcements == null) {
			return null;
		}

		List<AnnouncementDto> announcementList = new ArrayList<>();
		for (Announcement x : announcements) {
			announcementList.add(of(x));
		}

		return announcementList;
	}

	public String getRegDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return regDt != null ? regDt.format(formatter) : "";
	}

	public String getUdtDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return udtDt != null ? udtDt.format(formatter) : "";
	}

	public String getPostDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		return postDt != null ? postDt.format(formatter) : "";
	}


}

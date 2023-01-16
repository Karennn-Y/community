package com.project.community.announcement.dto;

import com.project.community.announcement.entity.Announcement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Lob;
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

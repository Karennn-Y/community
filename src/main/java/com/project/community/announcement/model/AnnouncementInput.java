package com.project.community.announcement.model;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Data
public class AnnouncementInput {
	long id;
	String subject;
	String Contents;
	String postDtText;

	// for delete
	String idList;
}

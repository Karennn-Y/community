package com.project.community.board.model;

import lombok.Data;

@Data
public class BoardInput {

	long id;
	long categoryId;
	String categoryName;
	String nickname;
	String userId;
	String subject;
	String contents;
	String regDt;

	// for delete
	String idList;

}

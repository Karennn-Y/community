package com.project.community.board.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentInput {

	long id;

	long boardId;

	String nickname;
	String userId;
	String subject;
	String contents;

	LocalDateTime regDt; // registerDate

	String idList; // for delete - admin
}

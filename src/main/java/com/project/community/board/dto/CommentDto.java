package com.project.community.board.dto;

import com.project.community.board.entity.Board;
import com.project.community.board.entity.Comment;
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
public class CommentDto {

	Long id;

	Long boardId;

	String nickname;
	String userId;
	String contents;

	LocalDateTime regDt; // registerDate

	long totalCount;
	long seq;


	public static CommentDto of(Comment comment) {
		return CommentDto.builder()
			.id(comment.getId())
			.boardId(comment.getBoardId())
			.contents(comment.getContents())
			.nickname(comment.getNickname())
			.userId(comment.getUserId())
			.regDt(comment.getRegDt())
			.build();
	}

	public static List<CommentDto> of (List<Comment> comments) {
		if (comments == null) {
			return null;
		}

		List<CommentDto> commentList = new ArrayList<>();
		for (Comment x : comments) {
			commentList.add(of(x));
		}

		return commentList;
	}

	public String getRegDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return regDt != null ? regDt.format(formatter) : "";
	}

}

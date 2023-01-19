package com.project.community.board.dto;

import com.project.community.announcement.dto.AnnouncementDto;
import com.project.community.announcement.entity.Announcement;
import com.project.community.board.entity.Board;
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
public class BoardDto {
	Long id;
	long categoryId;
	String categoryName;
	String subject;
	String contents;
	String nickname;
	String userId;

	LocalDateTime regDt;
	LocalDateTime udtDt;

	long totalCount;
	long seq;


	public static BoardDto of(Board board) {
		return BoardDto.builder()
			.id(board.getId())
			.categoryId(board.getCategoryId())
			.categoryName(board.getCategoryName())
			.subject(board.getSubject())
			.contents(board.getContents())
			.nickname(board.getNickname())
			.userId(board.getUserId())
			.regDt(board.getRegDt())
			.udtDt(board.getUdtDt())
			.build();
	}

	public static List<BoardDto> of (List<Board> boards) {
		if (boards == null) {
			return null;
		}

		List<BoardDto> boardList = new ArrayList<>();
		for (Board x : boards) {
			boardList.add(of(x));
		}

		return boardList;
	}

	public String getRegDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return regDt != null ? regDt.format(formatter) : "";
	}

	public String getUdtDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return udtDt != null ? udtDt.format(formatter) : "";
	}
}

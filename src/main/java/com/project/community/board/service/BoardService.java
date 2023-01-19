package com.project.community.board.service;

import com.project.community.board.dto.BoardDto;
import com.project.community.board.model.BoardInput;
import com.project.community.board.model.BoardParam;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {

	// 게시글 등록
	boolean add(BoardInput parameter);
	// 게시글 목록
	List<BoardDto> list(BoardParam parameter);
}

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
	// 게시글 수정
	boolean set(BoardInput parameter);
	// 게시글 목록
	List<BoardDto> list(BoardParam parameter);
	// 관리자 게시글 목록
	List<BoardDto> adminList(BoardParam parameter);
	// 게시글 상세정보
	BoardDto detail(long id);
	// 삭제
	boolean del(String idList);
	// 사용자 게시글 목록(자신이 작성한 게시글 찾기)
	List<BoardDto> userList(BoardParam parameter);
	// 사용자 게시글 삭제
	boolean userDEL(String idList);
	// 사용자 게시글 상세정보
	BoardDto getById(long id);
	// 사용자 게시글 작성자 아이디
	String getUserId(BoardDto existBoard);

}

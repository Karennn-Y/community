package com.project.community.board.service;

import com.project.community.board.dto.CommentDto;
import com.project.community.board.model.CommentInput;
import com.project.community.board.model.CommentParam;
import java.util.List;

public interface CommentService {
	// 댓글 등록
	boolean add(CommentInput parameter);
	// 댓글 목록
	List<CommentDto> list(CommentParam parameter);
	// 관리자 댓글 삭제
	boolean del(String idList);

}

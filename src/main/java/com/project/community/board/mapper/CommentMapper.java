package com.project.community.board.mapper;

import com.project.community.board.dto.CommentDto;
import com.project.community.board.model.CommentParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
	long selectListCount(CommentParam parameter);
	List<CommentDto> selectList(CommentParam parameter);
}

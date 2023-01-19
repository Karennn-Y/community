package com.project.community.board.mapper;

import com.project.community.announcement.model.AnnouncementParam;
import com.project.community.board.dto.BoardDto;
import com.project.community.board.model.BoardParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	long selectListCount(BoardParam parameter);
	List<BoardDto> selectList(BoardParam parameter);
}

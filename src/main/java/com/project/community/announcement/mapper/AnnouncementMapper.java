package com.project.community.announcement.mapper;

import com.project.community.announcement.dto.AnnouncementDto;
import com.project.community.announcement.model.AnnouncementParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnouncementMapper {

	long selectListCount(AnnouncementParam parameter);
	List<AnnouncementDto> selectList(AnnouncementParam parameter);

}

package com.project.community.admin.mapper;

import com.project.community.admin.dto.UserDto;
import com.project.community.admin.model.UserParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

	long selectListCount(UserParam parameter);
	List<UserDto> selectList(UserParam parameter);
}

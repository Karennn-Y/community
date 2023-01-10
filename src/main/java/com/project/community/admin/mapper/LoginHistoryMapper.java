package com.project.community.admin.mapper;

import com.project.community.admin.dto.LoginHistoryDto;
import com.project.community.admin.model.LoginHistoryParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginHistoryMapper {

    List<LoginHistoryDto> selectList(LoginHistoryParam parameter);

    long selectListCount(LoginHistoryParam parameter);
}

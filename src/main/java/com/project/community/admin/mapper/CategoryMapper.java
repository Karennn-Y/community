package com.project.community.admin.mapper;

import com.project.community.admin.dto.CategoryDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
	List<CategoryDto> select(CategoryDto parameter);
}

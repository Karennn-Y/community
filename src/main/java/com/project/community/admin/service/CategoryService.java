package com.project.community.admin.service;

import com.project.community.admin.dto.CategoryDto;
import com.project.community.admin.model.CategoryInput;
import java.util.List;

public interface CategoryService {

    // 카테고리 신규 추가
    boolean add(String categoryName);
    // 카테고리 수정
    boolean update(CategoryInput parameter);
    // 카테고리 삭제
    boolean del(long id);
    // 카테고리 목록
    List<CategoryDto> list();
    // 사용자 카테고리 목록
    List<CategoryDto> frontList(CategoryDto categoryDto);
}

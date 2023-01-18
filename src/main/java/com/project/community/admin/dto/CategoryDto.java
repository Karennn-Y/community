package com.project.community.admin.dto;

import com.project.community.admin.entity.Category;
import com.project.community.admin.entity.LoginHistory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    Long id;

    String categoryName;
    int sortValue;
    boolean usingYn;


    public static List<CategoryDto> of (List<Category> categories) {
        if (categories != null) {
            List<CategoryDto> categoryList = new ArrayList<>();
            for (Category x : categories) {
                categoryList.add(of(x));
            }

            return categoryList;
        }
        return null;
    }

    public static CategoryDto of (Category category) {
        return CategoryDto.builder()
            .id(category.getId())
            .categoryName(category.getCategoryName())
            .sortValue(category.getSortValue())
            .usingYn(category.isUsingYn())
            .build();
    }

}

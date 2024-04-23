package com.fem.boardserver.category.framework.web.dto;

import com.fem.boardserver.category.domain.dto.CategoryCreateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequest {
    private String name;

    public CategoryCreateDto toModel() {
        return CategoryCreateDto.builder()
                .name(name)
                .build();
    }
}

package com.fem.boardserver.post.framework.web.dto;

import com.fem.boardserver.post.domain.dto.PostCreateDto;
import com.fem.boardserver.post.domain.dto.PostUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private Long categoryId;

    public PostCreateDto toCreateModel(String userId) {
        return PostCreateDto.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .categoryId(categoryId)
                .build();
    }

    public PostUpdateDto toUpdateModel(String userId) {
        return PostUpdateDto.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .categoryId(categoryId)
                .build();
    }
}

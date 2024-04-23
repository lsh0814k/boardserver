package com.fem.boardserver.post.domain;


import com.fem.boardserver.post.domain.dto.PostCreateDto;
import com.fem.boardserver.post.domain.dto.PostUpdateDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id")
public class Post {
    @Id @GeneratedValue
    private Long id;
    @Column(length = 30)
    private String title;
    @Column(length = 500)
    private String content;
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @Column(updatable = false)
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
    private Long categoryId;
    private String userId;

    public static Post create(PostCreateDto postCreateDto) {
        LocalDateTime now = LocalDateTime.now();
        return Post.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .categoryId(postCreateDto.getCategoryId())
                .userId(postCreateDto.getUserId())
                .createdBy(postCreateDto.getUserId())
                .createdDate(now)
                .lastModifiedBy(postCreateDto.getUserId())
                .lastModifiedDate(now)
                .build();
    }

    public void update(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
        this.categoryId = postUpdateDto.getCategoryId();
        this.lastModifiedBy = postUpdateDto.getUserId();
        this.lastModifiedDate = LocalDateTime.now();
    }
}

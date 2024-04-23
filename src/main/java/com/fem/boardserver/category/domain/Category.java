package com.fem.boardserver.category.domain;

import com.fem.boardserver.category.domain.dto.CategoryCreateDto;
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
public class Category {
    @Id @GeneratedValue
    private Long id;
    @Column(length = 30)
    private String name;
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @Column(updatable = false)
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;

    public static Category create(CategoryCreateDto categoryCreateDto, String userId) {
        LocalDateTime now = LocalDateTime.now();

        return Category.builder()
                .name(categoryCreateDto.getName())
                .createdDate(now)
                .createdBy(userId)
                .lastModifiedDate(now)
                .lastModifiedBy(userId)
                .build();
    }
}

package com.fem.boardserver.category.framework.repository;

import com.fem.boardserver.category.application.outputport.CategoryRepository;
import com.fem.boardserver.category.domain.Category;
import com.fem.boardserver.user.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;


    @Override
    public Category save(Category category) {
        return categoryJpaRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryJpaRepository.delete(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("잘못된 카테고리 ID 입니다."));
    }
}

package com.fem.boardserver.category.application;

import com.fem.boardserver.category.application.outputport.CategoryRepository;
import com.fem.boardserver.category.domain.Category;
import com.fem.boardserver.category.domain.dto.CategoryCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void create(String userId, CategoryCreateDto categoryCreateDto) {
        Category category = Category.create(categoryCreateDto, userId);
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = categoryRepository.getById(id);
        categoryRepository.delete(category);
    }
}

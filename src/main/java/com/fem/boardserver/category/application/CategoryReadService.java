package com.fem.boardserver.category.application;

import com.fem.boardserver.category.application.outputport.CategoryRepository;
import com.fem.boardserver.category.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryReadService {
    private final CategoryRepository categoryRepository;

    public Category getById(Long id) {
        return categoryRepository.getById(id);
    }
}

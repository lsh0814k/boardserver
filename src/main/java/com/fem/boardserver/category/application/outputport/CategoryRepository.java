package com.fem.boardserver.category.application.outputport;

import com.fem.boardserver.category.domain.Category;

public interface CategoryRepository {
    Category save(Category category);

    void delete(Category category);

    Category getById(Long id);
}

package com.fem.boardserver.category.framework.repository;

import com.fem.boardserver.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}

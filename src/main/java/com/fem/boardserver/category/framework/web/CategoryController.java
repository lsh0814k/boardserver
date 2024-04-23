package com.fem.boardserver.category.framework.web;

import com.fem.boardserver.category.application.CategoryService;
import com.fem.boardserver.category.framework.web.dto.CategoryRequest;
import com.fem.boardserver.common.annotation.Auth;
import com.fem.boardserver.common.annotation.LoginUserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fem.boardserver.common.annotation.Auth.Role.ADMIN;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Auth(role = ADMIN)
    @PostMapping
    public ResponseEntity<String> create(@LoginUserId String userId, @RequestBody CategoryRequest categoryRequest) {
        categoryService.create(userId, categoryRequest.toModel());
        return ResponseEntity.status(OK)
                .build();
    }

    @Auth(role = ADMIN)
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> delete(@LoginUserId String userId, @PathVariable Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.status(OK)
                .build();
    }
}

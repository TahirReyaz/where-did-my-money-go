package com.tahir.where_did_my_money_go.expense.controller;

import com.tahir.where_did_my_money_go.expense.dto.ExpenseCategoryCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCategoryResponse;
import com.tahir.where_did_my_money_go.expense.service.ExpenseCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class ExpenseCategoryController {

    private final ExpenseCategoryService categoryService;

    @GetMapping
    public List<ExpenseCategoryResponse> getAllCategories() {
        return categoryService.getAll();
    }

    @PostMapping
    public ExpenseCategoryResponse createCategory(
            @Valid @RequestBody ExpenseCategoryCreateRequest request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public ExpenseCategoryResponse updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseCategoryCreateRequest request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.delete(id);
    }
}
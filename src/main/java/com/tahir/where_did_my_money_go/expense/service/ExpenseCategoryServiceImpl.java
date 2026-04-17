package com.tahir.where_did_my_money_go.expense.service;

import com.tahir.where_did_my_money_go.common.exception.ResourceNotFoundException;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCategoryCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCategoryResponse;
import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.expense.mapper.ExpenseCategoryMapper;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final ExpenseCategoryRepository repository;
    private final ExpenseCategoryMapper mapper;

    @Override
    public List<ExpenseCategoryResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER','MODERATOR')")
    public ExpenseCategoryResponse create(ExpenseCategoryCreateRequest request) {
        ExpenseCategory entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER','MODERATOR')")
    public ExpenseCategoryResponse update(UUID id, ExpenseCategoryCreateRequest request) {
        ExpenseCategory category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        mapper.updateEntityFromDto(request, category);

        return mapper.toResponse(repository.save(category));
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER','MODERATOR')")
    public void delete(UUID id) {
        ExpenseCategory category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        repository.delete(category);
    }
}
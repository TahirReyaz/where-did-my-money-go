package com.tahir.where_did_my_money_go.expense.mapper;

import com.tahir.where_did_my_money_go.expense.dto.ExpenseCategoryCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCategoryResponse;
import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ExpenseCategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    ExpenseCategory toEntity(ExpenseCategoryCreateRequest request);

    ExpenseCategoryResponse toResponse(ExpenseCategory entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ExpenseCategoryCreateRequest request, @MappingTarget ExpenseCategory entity);
}
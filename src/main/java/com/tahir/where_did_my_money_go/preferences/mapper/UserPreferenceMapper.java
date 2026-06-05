package com.tahir.where_did_my_money_go.preferences.mapper;

import com.tahir.where_did_my_money_go.preferences.dto.UserPreferenceResponseDTO;
import com.tahir.where_did_my_money_go.preferences.dto.UserPreferenceUpdateRequestDTO;
import com.tahir.where_did_my_money_go.preferences.entity.UserPreference;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserPreferenceMapper {

    @Mapping(target = "defaultExpenseCategoryId", source = "defaultExpenseCategory.id")
    @Mapping(target = "defaultExpenseCategoryName", source = "defaultExpenseCategory.name")
    UserPreferenceResponseDTO toDto(UserPreference preference);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(
            UserPreferenceUpdateRequestDTO request,
            @MappingTarget UserPreference preference);
}
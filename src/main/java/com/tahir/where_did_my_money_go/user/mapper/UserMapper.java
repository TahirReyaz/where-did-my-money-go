package com.tahir.where_did_my_money_go.user.mapper;

import com.tahir.where_did_my_money_go.user.dto.UserResponseDTO;
import com.tahir.where_did_my_money_go.user.dto.UserUpdateRequestDTO;
import com.tahir.where_did_my_money_go.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponse(User user);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "verified", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "recurringPayments", ignore = true)
    @Mapping(target = "trips", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequestDTO dto, @MappingTarget User user);
}
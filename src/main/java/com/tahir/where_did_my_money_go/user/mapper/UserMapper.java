package com.tahir.where_did_my_money_go.user.mapper;

import com.tahir.where_did_my_money_go.user.dto.UserResponseDTO;
import com.tahir.where_did_my_money_go.user.dto.UserUpdateRequestDTO;
import com.tahir.where_did_my_money_go.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequestDTO dto, @MappingTarget User user);
}
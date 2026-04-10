package com.tahir.where_did_my_money_go.user.service;

import java.util.UUID;

import com.tahir.where_did_my_money_go.user.dto.UserResponseDTO;
import com.tahir.where_did_my_money_go.user.dto.UserUpdateRequestDTO;

public interface UserService {

    UserResponseDTO getCurrentUser(UUID userId);

    UserResponseDTO getUserById(UUID currentUserId, UUID targetUserId);

    UserResponseDTO updateCurrentUser(UUID userId, UserUpdateRequestDTO request);
}
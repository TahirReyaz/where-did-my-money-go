package com.tahir.where_did_my_money_go.user.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tahir.where_did_my_money_go.common.exception.ResourceNotFoundException;
import com.tahir.where_did_my_money_go.common.exception.UnauthorizedException;
import com.tahir.where_did_my_money_go.user.dto.UserResponseDTO;
import com.tahir.where_did_my_money_go.user.dto.UserUpdateRequestDTO;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.mapper.UserMapper;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;
import com.tahir.where_did_my_money_go.user.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO getCurrentUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserResponseDTO userResponse = userMapper.toResponse(user);
        return userResponse;

    }

    @Override
    public UserResponseDTO getUserById(UUID currentUserId, UUID targetUserId) {

        if (!currentUserId.equals(targetUserId)) {
            throw new UnauthorizedException("Access denied");
        }

        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateCurrentUser(UUID userId, UserUpdateRequestDTO request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userMapper.updateUserFromDto(request, user);

        userRepository.save(user);

        return userMapper.toResponse(user);
    }
}
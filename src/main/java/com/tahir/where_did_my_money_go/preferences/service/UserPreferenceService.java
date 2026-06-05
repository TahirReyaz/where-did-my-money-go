package com.tahir.where_did_my_money_go.preferences.service;

import com.tahir.where_did_my_money_go.preferences.dto.UserPreferenceResponseDTO;
import com.tahir.where_did_my_money_go.preferences.dto.UserPreferenceUpdateRequestDTO;
import com.tahir.where_did_my_money_go.preferences.entity.UserPreference;
import com.tahir.where_did_my_money_go.preferences.mapper.UserPreferenceMapper;
import com.tahir.where_did_my_money_go.preferences.repository.UserPreferenceRepository;
import com.tahir.where_did_my_money_go.common.exception.ResourceNotFoundException;
import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final UserPreferenceMapper mapper;

    @Transactional(readOnly = true)
    public UserPreferenceResponseDTO getCurrentUserPreferences(UUID userId) {

        UserPreference preference = userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Preferences not found"));

        return mapper.toDto(preference);
    }

    @Transactional
    public UserPreferenceResponseDTO updatePreferences(
            UUID userId,
            UserPreferenceUpdateRequestDTO request) {

        UserPreference preference = userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Preferences not found"));

        mapper.updateEntity(request, preference);

        if (request.getDefaultExpenseCategoryId() != null) {

            ExpenseCategory category = expenseCategoryRepository
                    .findById(request.getDefaultExpenseCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Expense category not found"));

            preference.setDefaultExpenseCategory(category);
        }

        return mapper.toDto(
                userPreferenceRepository.save(preference));
    }
}
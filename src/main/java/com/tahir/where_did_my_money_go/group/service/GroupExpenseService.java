package com.tahir.where_did_my_money_go.group.service;

import java.util.UUID;

import com.tahir.where_did_my_money_go.group.dto.CreateGroupExpenseRequest;

public interface GroupExpenseService {

    void createGroupExpense(CreateGroupExpenseRequest request, UUID currentUser_id);
}
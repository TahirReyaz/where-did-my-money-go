package com.tahir.where_did_my_money_go.group.service;

import java.util.UUID;

import com.tahir.where_did_my_money_go.group.dto.CreateSettlementRequest;

public interface SettlementService {

    void createSettlement(CreateSettlementRequest request, UUID userId);

    void deleteSettlement(UUID settlementId, UUID userId);
}
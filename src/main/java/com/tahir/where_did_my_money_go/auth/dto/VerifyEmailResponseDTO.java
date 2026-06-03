package com.tahir.where_did_my_money_go.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyEmailResponseDTO {

    private String message;
}
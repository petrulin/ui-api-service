package com.otus.uiapiservice.domain.request.billing;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceClientRequest {

    private String username;
    private BigDecimal amount;

}

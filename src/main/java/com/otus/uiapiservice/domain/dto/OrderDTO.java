package com.otus.uiapiservice.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String userName;
    private BigDecimal totalAmount;
    private BigDecimal totalDiscount;
    private Long totalQuantity;

}

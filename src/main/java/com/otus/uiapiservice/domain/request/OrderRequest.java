package com.otus.uiapiservice.domain.request;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String msgId;
    private String userName;
    private BigDecimal totalAmount;
    private BigDecimal totalDiscount;
    private Long totalQuantity;

}

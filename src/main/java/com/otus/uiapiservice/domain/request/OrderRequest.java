package com.otus.uiapiservice.domain.request;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otus.uiapiservice.domain.request.dto.DateDeserializer;
import com.otus.uiapiservice.domain.request.dto.DateSerializer;
import com.otus.uiapiservice.domain.request.dto.DishDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String msgId;
    private BigDecimal amount;
    private BigDecimal discount;
    private Long quantity;
    private Long orderId;
    private String userName;
    private List<DishDTO> dishs;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate deliveryDate;
    private Long deliveryHour;

}

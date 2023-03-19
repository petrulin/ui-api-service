package com.otus.uiapiservice.client;

import com.otus.uiapiservice.config.OpenFeignConfiguration;
import com.otus.uiapiservice.domain.request.billing.BalanceClientRequest;
import com.otus.uiapiservice.domain.request.billing.RegisterClientRequest;
import com.otus.uiapiservice.domain.response.SimpeResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Qualifier("billingServiceClient")
@FeignClient(value = "billing-service"/*, fallback = AuthServiceClientFallback.class*/, configuration = OpenFeignConfiguration.class)
public interface BillingServiceClient {

    @GetMapping(path = "/billing-service/api/v1/internal/balance/add", produces = "application/json")
    SimpeResponse balanceAdd(BalanceClientRequest balanceClientRequest);

    @GetMapping(path = "/billing-service/api/v1/internal/balance/withdraw", produces = "application/json")
    SimpeResponse withdrawAdd(BalanceClientRequest balanceClientRequest);

    @PostMapping(path = "/billing-service/api/v1/internal/clients", produces = "application/json")
    SimpeResponse newClient(RegisterClientRequest client);

}
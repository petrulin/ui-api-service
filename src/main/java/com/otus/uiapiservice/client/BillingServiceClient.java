package com.otus.uiapiservice.client;

import com.otus.uiapiservice.config.OpenFeignConfiguration;
import com.otus.uiapiservice.domain.request.billing.BalanceClientRequest;
import com.otus.uiapiservice.domain.request.billing.RegisterClientRequest;
import com.otus.uiapiservice.domain.response.SimpeResponse;
import com.otus.uiapiservice.domain.response.billing.BalanceClientResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Qualifier("billingServiceClient")
@FeignClient(value = "billing-service"/*, fallback = AuthServiceClientFallback.class*/, configuration = OpenFeignConfiguration.class)
public interface BillingServiceClient {

    @PostMapping(path = "/billing-service/api/v1/internal/balance/add", consumes = "application/json", produces = "application/json")
    SimpeResponse balanceAdd(BalanceClientRequest balanceClientRequest);

    @PostMapping(path = "/billing-service/api/v1/internal/balance/withdraw", consumes = "application/json", produces = "application/json")
    SimpeResponse withdrawAdd(BalanceClientRequest balanceClientRequest);

    @PostMapping(path = "/billing-service/api/v1/internal/clients", consumes = "application/json", produces = "application/json")
    SimpeResponse newClient(RegisterClientRequest client);

    @PostMapping(path = "/billing-service/api/v1/internal/balance/get", consumes = "application/json", produces = "application/json")
    BalanceClientResponse getBalance(BalanceClientRequest balanceClientRequest);

}
package com.otus.uiapiservice.client;

import com.otus.uiapiservice.config.OpenFeignConfiguration;
import com.otus.uiapiservice.domain.request.billing.BalanceClientRequest;
import com.otus.uiapiservice.domain.request.billing.RegisterClientRequest;
import com.otus.uiapiservice.domain.request.notification.CreateMailDTO;
import com.otus.uiapiservice.domain.response.SimpeResponse;
import com.otus.uiapiservice.domain.response.billing.BalanceClientResponse;
import com.otus.uiapiservice.domain.response.notification.MailResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Qualifier("notificationServiceClient")
@FeignClient(value = "notification-service", configuration = OpenFeignConfiguration.class)
public interface NotificationServiceClient {

    @PostMapping(path = "/notification-service/api/v1/internal/findEmail", consumes = "application/json", produces = "application/json")
    MailResponse findEmail(@RequestBody CreateMailDTO mailDTO);

}
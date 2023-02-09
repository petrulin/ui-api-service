package com.otus.uiapiservice.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationError {

    SUCCESS("", 0),
    AUTH_SERVICE_UNAVAILABLE("Auth service unavailable", -700),
    IMAGE_SERVICE_UNAVAILABLE("Image service unavailable", -5600),
    INVENTORY_CONTROL_SERVICE_UNAVAILABLE("InventoryControl service unavailable", -6100),
    QR_CODE_EXCHANGE_UNAVAILABLE("QrCodeExchange service unavailable", -5300),
    CASH_DESK_API_UNAVAILABLE("CashDeskApi service unavailable", -4000),
    PUPIL_SERVICE_UNAVAILABLE("Pupil service unavailable", -8100),
    PAYMENT_SERVICE_UNAVAILABLE("Payment service unavailable", -5700),
    ACCESS_DENIED("Access denied", -8511),
    WRONG_PASSWORD("Wrong password or login", -8512),
    INTERNAL_ERROR("Internal error", -8513);

    private final String message;
    private final int errorCode;
}

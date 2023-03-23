package com.otus.uiapiservice.domain.request.notification;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMailDTO {

    private String username;
    private String message;
    private String email;
}

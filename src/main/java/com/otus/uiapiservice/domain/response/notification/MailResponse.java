package com.otus.uiapiservice.domain.response.notification;

import com.otus.uiapiservice.domain.response.AResponse;
import com.otus.uiapiservice.domain.response.notification.dto.MailDTO;
import com.otus.uiapiservice.error.ApplicationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailResponse extends AResponse {
    private List<MailDTO> emails;

    public MailResponse(ApplicationError applicationError) {
        super(applicationError);
    }

}

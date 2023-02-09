
package com.otus.uiapiservice.domain.response.auth;

import com.otus.uiapiservice.domain.response.AResponse;
import com.otus.uiapiservice.error.ApplicationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends AResponse {

	private String username;
	private String firstName;
	private String middleName;
	private String lastName;

	private String email;
	private String mobilePhone;

	public UserResponse(ApplicationError errorContent) {
		super(errorContent.getErrorCode(), errorContent.getMessage());
	}

}

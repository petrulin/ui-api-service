package com.otus.uiapiservice.client;

import com.otus.uiapiservice.config.OpenFeignConfiguration;
import com.otus.uiapiservice.domain.request.auth.CreateUserRequest;
import com.otus.uiapiservice.domain.response.auth.CreateUserResponse;
import com.otus.uiapiservice.domain.response.auth.UserResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Qualifier("authServiceClient")
@FeignClient(value = "auth-service"/*, fallback = AuthServiceClientFallback.class*/, configuration = OpenFeignConfiguration.class)
public interface AuthServiceClient {

    @PostMapping(path = "/oauth/token",  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String getAuthToken(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String refresh_token,
                        @RequestParam String grant_type
    );
/*
    @GetMapping(path = "/auth-service/api/v1/users", produces = "application/json")
    GetUsersResponse getUsers();

    @GetMapping(path = "/auth-service/api/v1/roles", produces = "application/json")
    GetRolesResponse getRoles();*/

    @GetMapping(path = "/auth-service/api/v1/users/username/{username}", produces = "application/json")
    UserResponse getUserByUserName(@PathVariable("username") String username);

    @PostMapping(path = "/auth-service/api/v1/users", consumes = "application/json", produces = "application/json")
    CreateUserResponse addUser(@RequestBody CreateUserRequest request);

    @PostMapping(path = "/auth-service/api/v1/users/edit", consumes = "application/json", produces = "application/json")
    CreateUserResponse editUser(@RequestBody CreateUserRequest request);
/*
    @PostMapping(path = "/auth-service/api/v1/users/{userId}/{enableAction}", consumes = "application/json", produces = "application/json")
    EnableActionUserResponse userEnableAction(@PathVariable("userId") Long userId, @PathVariable("enableAction") Boolean enableAction);

    @PostMapping(path = "/auth-service/api/v1/password", consumes = "application/json", produces = "application/json")
    ChangePasswordResponse changePassword(@RequestBody ChangePasswordRequest request);

    @GetMapping(value = "/auth-service/api/v1/internal/apiClients", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiClientsResponse getPushClient();

    @PostMapping(value = "/auth-service/api/v1/internal/addApiClient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SimpleResponse addPushClient(@RequestBody AddApiClientRequest apiClient);
    */
}
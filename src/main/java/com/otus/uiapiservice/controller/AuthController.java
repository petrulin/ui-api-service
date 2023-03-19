package com.otus.uiapiservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.uiapiservice.client.AuthServiceClient;
import com.otus.uiapiservice.client.BillingServiceClient;
import com.otus.uiapiservice.domain.dto.RoleData;
import com.otus.uiapiservice.domain.request.RegisterUserRequest;
import com.otus.uiapiservice.domain.request.auth.CreateUserRequest;
import com.otus.uiapiservice.domain.request.billing.RegisterClientRequest;
import com.otus.uiapiservice.domain.response.SimpeResponse;
import com.otus.uiapiservice.domain.response.auth.CreateUserResponse;
import com.otus.uiapiservice.domain.response.auth.UserResponse;
import com.otus.uiapiservice.error.ApplicationError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/ui-api-service/api/v1/auth/")
@RestController
public class AuthController {

    private final ModelMapper modelMapper;


    @Value("${sfp.authorization}")
    private String authorization;

    @Autowired
    AuthServiceClient asc;

    @Autowired
    BillingServiceClient bsc;

    public AuthController() {
        this.modelMapper = new ModelMapper();
    }
    @PostMapping(path = "/registration")
    public ResponseEntity<SimpeResponse> addUser(@RequestBody RegisterUserRequest user) {
        try {
            CreateUserRequest cur = convertToCreateUserRequest(user);
            List<RoleData> roles = new ArrayList<>();

            UserResponse userResponse = asc.getUserByUserName(user.getUsername());
            if (userResponse.getUsername() != null) {
                return ResponseEntity.ok(new SimpeResponse("ERROR", "Duplicate username"));
            }
            roles.add(new RoleData(2L, "ROLE_USER"));
            cur.setRoles(roles);

            CreateUserResponse createUserResponse= asc.addUser(cur);

            SimpeResponse createBillingClientResponse =  bsc.newClient(new RegisterClientRequest(user.getUsername()));

            if ((createUserResponse.getErrorCode() == 0) && (Objects.equals(createBillingClientResponse.getError(), ""))) {
                return ResponseEntity.ok(new SimpeResponse("OK", ""));
            } else {
                return ResponseEntity.ok(new SimpeResponse("ERROR", createUserResponse.getErrorMessage() + createBillingClientResponse.getError()));
            }

        } catch (Exception ex) {
            return ResponseEntity.ok(new SimpeResponse("ERROR", ex.getLocalizedMessage()));
        }
    }

    @PostMapping(path = "/token")
    public ResponseEntity<JsonNode> getToken(@RequestBody RegisterUserRequest user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.createObjectNode();
        try {
            String str = asc.getAuthToken(authorization, user.getUsername(), user.getPassword(), null,"password");
            mapper = new ObjectMapper();
            jsonNode = mapper.readTree(str);
            return ResponseEntity.ok(jsonNode);
        } catch (Exception ex) {
            jsonNode = mapper.convertValue(new SimpeResponse("ERROR", ApplicationError.WRONG_PASSWORD.getMessage()), JsonNode.class);
            return ResponseEntity.ok(jsonNode);
        }
    }

    private CreateUserRequest convertToCreateUserRequest(RegisterUserRequest user) {
        return modelMapper.map(user, CreateUserRequest.class);
    }

}

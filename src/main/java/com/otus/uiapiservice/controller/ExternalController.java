package com.otus.uiapiservice.controller;


import com.otus.uiapiservice.client.AuthServiceClient;
import com.otus.uiapiservice.domain.request.RegisterUserRequest;
import com.otus.uiapiservice.domain.request.auth.CreateUserRequest;
import com.otus.uiapiservice.domain.response.SimpeResponse;
import com.otus.uiapiservice.domain.response.auth.UserResponse;
import com.otus.uiapiservice.error.ApplicationError;
import com.otus.uiapiservice.error.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping(value = "/ui-api-service/api/v1/external/")
@RestController
public class ExternalController {

    private final ModelMapper modelMapper;

    @Autowired
    AuthServiceClient asc;

    public ExternalController() {
        this.modelMapper = new ModelMapper();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/user/edit")
    public ResponseEntity<SimpeResponse> editUser(@RequestBody RegisterUserRequest user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            if (!currentPrincipalName.equals(user.getUsername()))
                throw new ApplicationException(ApplicationError.ACCESS_DENIED);
            CreateUserRequest cur = convertToCreateUserRequest(user);
            asc.editUser(cur);
            return ResponseEntity.ok(new SimpeResponse("OK", ""));
        } catch (Exception ex) {
            return ResponseEntity.ok(new SimpeResponse("ERROR", ex.getLocalizedMessage()));
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/user/find")
    public ResponseEntity<UserResponse> getUser(@RequestBody RegisterUserRequest user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            if (!currentPrincipalName.equals(user.getUsername())) {
                return ResponseEntity.ok(new UserResponse(ApplicationError.ACCESS_DENIED));
            }
            UserResponse userResponse = asc.getUserByUserName(user.getUsername());
            return ResponseEntity.ok(userResponse);
        } catch (Exception ex) {
            return ResponseEntity.ok(new UserResponse(ApplicationError.INTERNAL_ERROR));
        }
    }




    private CreateUserRequest convertToCreateUserRequest(RegisterUserRequest user) {
        return modelMapper.map(user, CreateUserRequest.class);
    }


    public boolean isRoleAdmin() {
        return isRole("ROLE_USER");
    }

    private boolean isRole(String roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .anyMatch(ga -> Objects.equals(ga.getAuthority(), roleName));
    }


}

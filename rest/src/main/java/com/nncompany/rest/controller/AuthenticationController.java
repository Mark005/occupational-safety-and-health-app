package com.nncompany.rest.controller;

import com.nncompany.api.dto.CredentialsDto;
import com.nncompany.api.dto.RequestError;
import com.nncompany.api.model.wrappers.Token;
import com.nncompany.rest.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${app.rest.url}")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;


    @Operation(summary = "Get authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = Token.class))),
            @ApiResponse(responseCode = "400", description = "Invalid json, need add login and pass",
                    content = @Content(schema = @Schema(implementation = RequestError.class))),
            @ApiResponse(responseCode = "401", description = "Invalid pair of login and password",
                    content = @Content(schema = @Schema(implementation = RequestError.class))),
            @ApiResponse(responseCode = "404", description = "Can't find user with this login and password",
                    content = @Content(schema = @Schema(implementation = RequestError.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody CredentialsDto credentialsDto) {
        String token = authenticationService
                .getTokenByLoginAndPass(
                        credentialsDto.getLogin(),
                        credentialsDto.getPassword());

        return ResponseEntity.ok().body(
                Map.of("username", credentialsDto.getLogin(),
                        "token", token));
    }
}

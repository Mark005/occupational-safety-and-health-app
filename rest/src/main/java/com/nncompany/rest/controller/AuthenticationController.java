package com.nncompany.rest.controller;

import com.nncompany.api.model.entities.CredentialsDto;
import com.nncompany.api.model.wrappers.RequestError;
import com.nncompany.api.model.wrappers.Token;
import com.nncompany.rest.security.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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


    @ApiOperation("Login in system  (you can sand json only with login and pass)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login successful", response = Token.class),
            @ApiResponse(code = 400, message = "Invalid json, need add login and pass", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find user with this login and password", response = RequestError.class)
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

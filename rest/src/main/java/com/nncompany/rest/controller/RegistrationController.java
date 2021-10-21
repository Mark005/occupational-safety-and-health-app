package com.nncompany.rest.controller;

import com.nncompany.api.dto.RequestError;
import com.nncompany.api.dto.user.CheckLoginDto;
import com.nncompany.api.dto.user.UserRegistrationDto;
import com.nncompany.api.interfaces.services.UserCredentialsService;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.wrappers.BooleanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${app.rest.url}/registration")
public class RegistrationController {

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Check if the login is already exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful check result",
                    content = @Content(schema = @Schema(implementation = BooleanResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = RequestError.class)))
    })
    @PostMapping("/checkLogin")
    public ResponseEntity<BooleanResponse> login(@RequestBody CheckLoginDto loginDto) {
        return ResponseEntity.ok(
                new BooleanResponse(
                        userCredentialsService.checkLogin(loginDto.getLogin())));
    }

    @Operation(summary = "Registration new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New user registered successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = RequestError.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = RequestError.class))),
            @ApiResponse(responseCode = "409", description = "Login is already exist",
                    content = @Content(schema = @Schema(implementation = RequestError.class)))
    })
    @PostMapping("/user")
    public ResponseEntity<User> registration(@RequestBody UserRegistrationDto requestUserCredentials) {
        return new ResponseEntity<>(userService.registerUser(requestUserCredentials), HttpStatus.CREATED);
    }
}

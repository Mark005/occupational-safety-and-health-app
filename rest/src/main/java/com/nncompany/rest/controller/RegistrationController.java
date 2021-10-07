package com.nncompany.rest.controller;

import com.nncompany.api.dto.user.CheckLoginDto;
import com.nncompany.api.dto.user.UserRegistrationDto;
import com.nncompany.api.interfaces.services.UserCredentialsService;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.wrappers.BooleanResponse;
import com.nncompany.api.dto.RequestError;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Check if the login is already exist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Received response in json", response = BooleanResponse.class),
            @ApiResponse(code = 400, message = "Invalid request", response = RequestError.class)
    })
    @PostMapping("/checkLogin")
    public ResponseEntity<BooleanResponse> login(@RequestBody CheckLoginDto loginDto) {
        return ResponseEntity.ok(
                new BooleanResponse(
                        userCredentialsService.checkLogin(loginDto.getLogin())));
    }

    @ApiOperation(value = "Registration new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New user registered successfully", response = User.class),
            @ApiResponse(code = 400, message = "Invalid input json (check models for more info), " +
                                               "or current login is already exist", response = RequestError.class),
            @ApiResponse(code = 409, message = "New user registered successfully", response = RequestError.class)
    })
    @PostMapping("/user")
    public ResponseEntity<User> registration(@RequestBody UserRegistrationDto requestUserCredentials) {
        return new ResponseEntity<>(userService.registerUser(requestUserCredentials), HttpStatus.CREATED);
    }
}

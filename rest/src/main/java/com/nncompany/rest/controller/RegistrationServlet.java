package com.nncompany.rest.controller;

import com.nncompany.api.interfaces.services.UserCredentialsService;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserCredentials;
import com.nncompany.api.model.wrappers.BooleanResponse;
import com.nncompany.api.model.wrappers.RequestError;
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
@RequestMapping("/api/rest/registration")
public class RegistrationServlet {

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Check if the login is busy")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Received response in json", response = BooleanResponse.class),
            @ApiResponse(code = 400, message = "Invalid or not added login field", response = RequestError.class)
    })
    @PostMapping("/checkLogin")
    public ResponseEntity<Object> login(@RequestBody UserCredentials requestUserCredentials) {
        if(requestUserCredentials.getLogin() == null ){
            return new ResponseEntity<>(new RequestError(400,
                                                        "invalid request json",
                                                        "json must have login"),
                                                        HttpStatus.BAD_REQUEST);
        }
        if (userCredentialsService.checkLogin(requestUserCredentials.getLogin())) {
            return ResponseEntity.ok(new BooleanResponse(true));
        } else {
            return ResponseEntity.ok(new BooleanResponse(false));
        }
    }

    @ApiOperation(value = "Registration new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New user registered successfully", response = User.class),
            @ApiResponse(code = 400, message = "Invalid input json (check models for more info), " +
                                               "or current login is already exist", response = RequestError.class)
    })
    @PostMapping("/user")
    public ResponseEntity registration(@RequestBody UserCredentials requestUserCredentials) {
        if (userCredentialsService.checkLogin(requestUserCredentials.getLogin())) {
            return new ResponseEntity<>(new RequestError(400,
                                                        "login isn't unique",
                                                        "current login is already exist"),
                                                        HttpStatus.BAD_REQUEST);
        }
        /*requestUserCreds.getUser().setAdmin(false);*/
        userService.save(requestUserCredentials.getUser());
        userCredentialsService.save(requestUserCredentials);
        return new ResponseEntity<>(requestUserCredentials.getUser(), HttpStatus.CREATED);
    }
}

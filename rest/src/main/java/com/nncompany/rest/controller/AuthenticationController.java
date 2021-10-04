package com.nncompany.rest.controller;

import com.nncompany.api.interfaces.services.UserCredentialsService;
import com.nncompany.api.model.entities.CredentialsDto;
import com.nncompany.api.model.wrappers.RequestError;
import com.nncompany.api.model.wrappers.Token;
import com.nncompany.rest.security.service.TokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("${app.rest.url}")
public class AuthenticationController {
    @Autowired
    private UserCredentialsService userCredentialsService;

    @Autowired
    private TokenProvider tokenHandler;


    @ApiOperation("Login in system  (you can sand json only with login and pass)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login successful", response = Token.class),
            @ApiResponse(code = 400, message = "Invalid json, need add login and pass", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find user with this login and password", response = RequestError.class)
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody CredentialsDto credentialsDto) {
        var credentials = userCredentialsService
                .getUserCredentialsByLoginAndPass(
                        credentialsDto.getLogin(),
                        credentialsDto.getPassword())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

        return ResponseEntity.ok().body(
                Map.of("username", credentialsDto.getLogin(),
                        "token", tokenHandler.getToken(credentialsDto.getLogin())));
    }

    @ApiOperation("Login in system  (you can sand json only with login and pass)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login successful", response = Token.class),
            @ApiResponse(code = 400, message = "Invalid json, need add login and pass", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find user with this login and password", response = RequestError.class)
    })
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        var securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}

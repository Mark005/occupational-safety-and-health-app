package com.nncompany.rest.controller;

import com.nncompany.api.dto.user.UserUpdateDto;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.dto.RequestError;
import com.nncompany.api.model.wrappers.ResponseList;
import com.nncompany.impl.util.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.rest.url}")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get current logged user")
    @ApiResponse(code = 200, message = "User returned successfully", response = User.class)
    @GetMapping("/user/current")
    public ResponseEntity<User> getLoggedUser() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
    }

    @ApiOperation(value = "Get users with pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users returned successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid request params", response = RequestError.class)
    })
    @GetMapping("/user")
    public ResponseEntity<Object> getUsers(@RequestParam Integer page,
                                           @RequestParam Integer pageSize) {
        Page<User> withPagination = userService.findAllUsers(page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        withPagination.getContent(),
                        withPagination.getTotalElements()));
    }

    @ApiOperation(value = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User returned successfully", response = User.class),
            @ApiResponse(code = 400, message = "Invalid path variables", response = RequestError.class),
            @ApiResponse(code = 404, message = "User with current id not found", response = RequestError.class)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Get user by (name/surname)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users returned successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid request params", response = RequestError.class),
            @ApiResponse(code = 404, message = "Users with this criteria not found", response = RequestError.class)
    })
    @GetMapping("/user/search")
    public ResponseEntity<Object> findUsers(@RequestParam Integer page,
                                            @RequestParam Integer pageSize,
                                            @RequestParam String searchString) {
        Page<User> usersPage = userService.search(searchString, page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        usersPage.getContent(),
                        usersPage.getTotalElements()));
    }

    @ApiOperation(value = "Update user by id except admin status " +
            "(only admin can update someone else except itself, " +
            "and only admin can change admin status)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User changed successfully", response = User.class),
            @ApiResponse(code = 400, message = "Invalid path variables", response = RequestError.class),
            @ApiResponse(code = 403, message = "You have hot permission to change this user", response = RequestError.class),
            @ApiResponse(code = 404, message = "User with current id not found", response = RequestError.class)
    })
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id,
                                           @RequestBody UserUpdateDto userDto) {
        userDto.setId(id);
        return ResponseEntity.ok(userService.update(userDto));
    }

    @ApiOperation(value = "Delete user by id (only admin can delete someone else except itself)")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid path variables", response = RequestError.class),
            @ApiResponse(code = 403, message = "You have hot permission to delete this user", response = RequestError.class),
            @ApiResponse(code = 404, message = "User with current id not found", response = RequestError.class)
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

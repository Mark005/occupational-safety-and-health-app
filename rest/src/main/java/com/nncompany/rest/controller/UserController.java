package com.nncompany.rest.controller;

import com.nncompany.api.dto.user.UserUpdateDto;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.wrappers.ResponseList;
import com.nncompany.impl.util.SecurityUtils;
import com.nncompany.rest.annotation.CommonOperation;
import com.nncompany.rest.annotation.OperationDelete;
import com.nncompany.rest.annotation.OperationFindItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.rest.url}")
public class UserController {

    @Autowired
    private UserService userService;

    @CommonOperation(value = "Get current logged user")
    @GetMapping("/user/current")
    public ResponseEntity<User> getLoggedUser() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
    }

    @OperationFindItems(value = "Get users with pagination")
    @GetMapping("/user")
    public ResponseEntity<Object> getUsers(@RequestParam Integer page,
                                           @RequestParam Integer pageSize) {
        Page<User> withPagination = userService.findAllUsers(page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        withPagination.getContent(),
                        withPagination.getTotalElements()));
    }

    @CommonOperation(value = "Get user by id")
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @CommonOperation(value = "Get user by (name/surname)")
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

    @CommonOperation(value = "Update user by id except admin status (only admin can update someone " +
            "else except itself, and only admin can change admin status)")
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id,
                                           @RequestBody UserUpdateDto userDto) {
        userDto.setId(id);
        return ResponseEntity.ok(userService.update(userDto));
    }

    @OperationDelete(value = "Delete user by id (only admin can delete someone else except itself)")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

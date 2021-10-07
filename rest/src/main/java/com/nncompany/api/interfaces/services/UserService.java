package com.nncompany.api.interfaces.services;

import com.nncompany.api.dto.user.UserRegistrationDto;
import com.nncompany.api.dto.user.UserUpdateDto;
import com.nncompany.api.model.entities.User;
import org.springframework.data.domain.Page;

public interface UserService {

    User findById(Integer id);

    Page<User> findAllUsers(Integer page, Integer pageSize);

    Page<User> search(String nameOrAndSurname, Integer page, Integer pageSize);

    User registerUser(UserRegistrationDto userDto);

    User save(User user);

    User update(UserUpdateDto user);

    void delete(Integer id);
}

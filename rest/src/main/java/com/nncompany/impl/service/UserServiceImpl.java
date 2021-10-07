package com.nncompany.impl.service;

import com.nncompany.api.dto.user.UserRegistrationDto;
import com.nncompany.api.dto.user.UserUpdateDto;
import com.nncompany.api.interfaces.services.UserCredentialsService;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.interfaces.store.UserStore;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserCredentials;
import com.nncompany.api.model.enums.Role;
import com.nncompany.impl.exception.EntityNotFoundException;
import com.nncompany.impl.exception.LoginIsAlreadyExistException;
import com.nncompany.impl.exception.PermissionDeniedException;
import com.nncompany.impl.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserStore userStore;

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Override
    public User findById(Integer id) {
        return userStore
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public Page<User> findAllUsers(Integer page, Integer pageSize) {
        return userStore.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<User> search(String searchString, Integer page, Integer pageSize) {
        return userStore.findAllByNameIsLikeOrSurnameIsLike(
                searchString,
                searchString,
                PageRequest.of(page, pageSize));
    }

    @Override
    public User registerUser(UserRegistrationDto userDto) {
        if (userCredentialsService.checkLogin(userDto.getLogin())) {
            throw new LoginIsAlreadyExistException("Current login is already taken");
        }

        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .gender(userDto.getGender())
                .certificateNumber(userDto.getCertificateNumber())
                .profession(userDto.getProfession())
                .dateEmployment(userDto.getDateEmployment())
                .build();

        UserCredentials userCredentials = UserCredentials.builder()
                .login(userDto.getLogin())
                .pass(userDto.getPassword())
                .user(user)
                .build();

        User savedUser = userStore.save(user);
        userCredentialsService.save(userCredentials);
        return savedUser;
    }

    @Override
    public User save(User user) {
        return userStore.save(user);
    }

    @Override
    public User update(UserUpdateDto userDto) {
        User user = userStore
                .findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        User currentUser = SecurityUtils.getCurrentUser();
        if (Role.ADMIN != currentUser.getRole() || Objects.equals(currentUser, user)) {
            throw new PermissionDeniedException("You are not allow to change this user");
        }

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setGender(userDto.getGender());
        user.setCertificateNumber(userDto.getCertificateNumber());
        user.setProfession(userDto.getProfession());

        return userStore.save(user);
    }

    @Override
    public void delete(Integer id) {
        userStore.deleteById(id);
    }
}

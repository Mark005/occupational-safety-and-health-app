package com.nncompany.api.interfaces.services;

import com.nncompany.api.model.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User get(int id);

    Page<User> getWithPagination(Integer page, Integer pageSize);

    List<User> search(String nameOrAndSurname);

    void save(User user);

    void update(User user);

    void delete(User user);
}

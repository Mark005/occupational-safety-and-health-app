package com.nncompany.impl.service;

import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.interfaces.store.UserStore;
import com.nncompany.api.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserStore userStore;

    @Override
    public User get(int id) {
        return userStore.getById(id);
    }

    @Override
    public Page<User> getWithPagination(Integer page, Integer pageSize) {
        return userStore.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public List<User> search(String searchString) {
        return userStore.findAllByNameIsLikeOrSurnameIsLike(searchString, searchString);
    }

    @Override
    public void save(User user) {
        userStore.save(user);
    }

    @Override
    public void update(User user) {
        userStore.save(user);
    }

    @Override
    public void delete(User user) {
        userStore.delete(user);
    }
}

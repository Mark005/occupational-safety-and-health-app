package com.nncompany.impl.service;

import com.nncompany.api.interfaces.services.UserBriefingService;
import com.nncompany.api.interfaces.store.BriefingStore;
import com.nncompany.api.interfaces.store.UserBriefingStore;
import com.nncompany.api.interfaces.store.UserStore;
import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserBriefing;
import com.nncompany.api.model.enums.Direction;
import com.nncompany.api.model.enums.UserBriefingSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserBriefingServiceImpl implements UserBriefingService {

    @Autowired
    UserBriefingStore userBriefingStore;

    @Autowired
    UserStore userStore;

    @Autowired
    BriefingStore briefingStore;


    @Override
    public UserBriefing get(int id) {
        return userBriefingStore.getById(id);
    }

    @Override
    public List<Briefing> getBriefingsByCurrentUser(User user) {
        return briefingStore.findAllPassedByCurrentUser(user);
    }


    @Override
    public List<User> getUsersByCurrentBriefing(Briefing briefing) {
        return userStore.findAllUsersWhoPassCurrentBriefing(briefing);
    }

    @Override
    public List<UserBriefing> getAll(Integer page,
                                     Integer pageSize,
                                     UserBriefingSort sort,
                                     Sort.Direction direction) {
        return userBriefingStore
                .findAll(PageRequest.of(page, pageSize, Sort.by(direction, sort.getTitle())))
                .getContent();
    }

    @Override
    public List<UserBriefing> getWithPagination(Integer page, Integer pageSize) {
        return userBriefingStore
                .findAll(PageRequest.of(page, pageSize))
                .getContent();
    }

    @Override
    public void save(UserBriefing userBriefing) {
        userBriefingStore.save(userBriefing);
    }

    @Override
    public void update(UserBriefing userBriefing) {
        userBriefingStore.save(userBriefing);
    }

    @Override
    public void delete(UserBriefing userBriefing) {
        userBriefingStore.delete(userBriefing);
    }
}

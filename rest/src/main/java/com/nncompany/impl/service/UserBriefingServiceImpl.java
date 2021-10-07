package com.nncompany.impl.service;

import com.nncompany.api.dto.userBriefing.UserBriefingCreateDto;
import com.nncompany.api.dto.userBriefing.UserBriefingUpdateDto;
import com.nncompany.api.interfaces.services.UserBriefingService;
import com.nncompany.api.interfaces.store.BriefingStore;
import com.nncompany.api.interfaces.store.UserBriefingStore;
import com.nncompany.api.interfaces.store.UserStore;
import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserBriefing;
import com.nncompany.api.model.enums.UserBriefingSort;
import com.nncompany.impl.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public UserBriefing findById(Integer id) {
        return userBriefingStore
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passed briefing not found"));
    }

    @Override
    public Page<Briefing> getBriefingsByCurrentUser(Integer userId, Integer page, Integer pageSize) {
        User user = userStore
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return briefingStore.findAllPassedBriefingsByUser(user, PageRequest.of(page, pageSize));
    }


    @Override
    public Page<User> getUsersByCurrentBriefing(Integer briefingId, Integer page, Integer pageSize) {
        final Briefing briefing = briefingStore
                .findById(briefingId)
                .orElseThrow(() -> new EntityNotFoundException("Briefing not fount"));
        return userStore.findAllUsersWhoPassCurrentBriefing(briefing, PageRequest.of(page, pageSize));
    }

    @Override
    public Page<UserBriefing> findAll(Integer page, Integer pageSize, UserBriefingSort sort, Sort.Direction direction) {
        return userBriefingStore.findAll(PageRequest.of(page, pageSize, Sort.by(direction, sort.getTitle())));
    }

    @Override
    public Page<UserBriefing> getUserBriefings(Integer page, Integer pageSize) {
        return userBriefingStore
                .findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public UserBriefing save(UserBriefingCreateDto userBriefingDto) {
        User user = userStore
                .findById(userBriefingDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not fount"));

        Briefing briefing = briefingStore
                .findById(userBriefingDto.getBriefingId())
                .orElseThrow(() -> new EntityNotFoundException("Briefing not fount"));

        UserBriefing userBriefing = UserBriefing.builder()
                .user(user)
                .briefing(briefing)
                .lastDate(userBriefingDto.getLastDate())
                .build();
        return userBriefingStore.save(userBriefing);
    }

    @Override
    public UserBriefing update(UserBriefingUpdateDto userBriefingDto) {
        UserBriefing userBriefing = userBriefingStore
                .findById(userBriefingDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Passed briefing not found"));

        userBriefing.setLastDate(userBriefingDto.getLastDate());

        return userBriefingStore.save(userBriefing);
    }

    @Override
    public void deleteById(Integer userBriefingId) {
        userBriefingStore.deleteById(userBriefingId);
    }
}

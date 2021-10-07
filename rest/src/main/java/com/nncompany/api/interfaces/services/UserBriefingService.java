package com.nncompany.api.interfaces.services;

import com.nncompany.api.dto.userBriefing.UserBriefingCreateDto;
import com.nncompany.api.dto.userBriefing.UserBriefingUpdateDto;
import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserBriefing;
import com.nncompany.api.model.enums.UserBriefingSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserBriefingService {

    UserBriefing findById(Integer id);

    Page<Briefing> getBriefingsByCurrentUser(Integer userId, Integer page, Integer pageSize);

    Page<User> getUsersByCurrentBriefing(Integer briefingId, Integer page, Integer pageSize);

    Page<UserBriefing> findAll(Integer page, Integer pageSize, UserBriefingSort sort, Sort.Direction direction);

    Page<UserBriefing> getUserBriefings(Integer offset, Integer limit);

    UserBriefing save(UserBriefingCreateDto userBriefing);

    UserBriefing update(UserBriefingUpdateDto userBriefingDto);

    void deleteById(Integer userBriefingId);
}

package com.nncompany.api.interfaces.services;

import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserBriefing;
import com.nncompany.api.model.enums.Direction;
import com.nncompany.api.model.enums.UserBriefingSort;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserBriefingService {

    UserBriefing get(int id);

    List<Briefing> getBriefingsByCurrentUser(User user);

    List<User> getUsersByCurrentBriefing(Briefing briefing);

    List<UserBriefing> getAll(Integer page, Integer pageSize, UserBriefingSort sort, Sort.Direction direction);

    List<UserBriefing> getWithPagination(Integer offset, Integer limit);

    void save(UserBriefing userBriefing);

    void update(UserBriefing userBriefing);

    void delete(UserBriefing userBriefing);
}

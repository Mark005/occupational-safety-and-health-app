package com.nncompany.api.interfaces.store;

import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserBriefing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBriefingStore extends JpaRepository<UserBriefing, Integer> {

    @Query("SELECT ub.briefing FROM UserBriefing ub where ub.user =:user")
    List<Briefing> getBriefingsByCurrentUser(@Param("user") User user);

    @Query("SELECT ub.user FROM UserBriefing ub where ub.briefing =:briefing")
    List<User> getUsersByCurrentBriefing(@Param("briefing") Briefing briefing);
}

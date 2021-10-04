package com.nncompany.api.interfaces.store;

import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BriefingStore extends JpaRepository<Briefing, Integer> {

    @Query("SELECT ub.briefing FROM UserBriefing ub where ub.user =:user")
    List<Briefing> findAllPassedByCurrentUser(@Param("user") User user);
}

package com.nncompany.api.interfaces.store;

import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserStore extends JpaRepository<User, Integer> {

    Page<User> findAllByNameIsLikeOrSurnameIsLike(String name, String surname, Pageable pageable);

    List<User> findByNameIgnoreCase(String term);

    @Query("SELECT ub.user FROM UserBriefing ub where ub.briefing =:briefing")
    Page<User> findAllUsersWhoPassCurrentBriefing(@Param("briefing") Briefing briefing, Pageable pageable);
}
